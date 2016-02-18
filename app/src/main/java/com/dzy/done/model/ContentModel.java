package com.dzy.done.model;

import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.config.app;
import com.dzy.done.util.ContentParser;
import com.dzy.done.util.MLog;
import com.dzy.done.util.NetworkUtils;
import com.dzy.done.util.apiService;

/**
 * 数据获取和逻辑控制层，单例
 * Created by dzysg on 2015/10/13 0013.
 */
public class ContentModel
{

    private static ContentModel Model = null;
    private apiService mServer = new apiService();

    private ContentParser mParser = new ContentParser();
    private LruCache<String, String> mArticleCache = new LruCache<String, String>(100);
    private LruCache<String, PictureItem> mPictureCache = new LruCache<String, PictureItem>(100);
    private LruCache<String, ThingItem> mThingCache = new LruCache<String, ThingItem>(100);

    private static Handler mHandler;


    public static ContentModel get()
    {
        if (Model == null)
            Model = new ContentModel();
        return Model;
    }

    public void getArticle(final String url, final IGetArticleCallback callback)
    {
        Log.i("tag", "article url  : " + url);

        String html = null;
        //检查内存缓存
        if ((html = mArticleCache.get(url)) != null) {
            callback.Finish(html);
            MLog.getLogger().d("article MemoryCache hit");
        }
        else if (app.getRequestQueue().getCache().get(url) != null) {
            try {
                MLog.getLogger().d("article FileCache hit");

                html = new String(app.getRequestQueue().getCache().get(url).data, "GBK");

                callback.Finish(html);
                mArticleCache.put(url, html);
            }
            catch (Exception e) {
                callback.Falure(e.getMessage());
            }
        }
        //检查网络
        else if (NetworkUtils.isNetworkConnected()) {
            MLog.getLogger().d("start Article Task by network");
            String requesturl = "http://dzyone.applinzi.com/article.php?url="+url;

            StringRequest request = new StringRequest(requesturl, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    MLog.getLogger().d(response);

                    mArticleCache.put(url,response);
                    callback.Finish(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    callback.Falure("request fail");
                }
            });
            try {

                app.getRequestQueue().add(request);

            }
            catch (Exception e)
            {
                callback.Falure("add request exeception");
            }
//            new AsyncTask<String, Integer, String>()
//            {
//                @Override
//                protected String doInBackground(String... params)
//                {
//                    return mServer.getArticle(params[0]);
//                }
//                @Override
//                protected void onPostExecute(String s)
//                {
//                    if (TextUtils.isEmpty(s)) {
//                        callback.Falure("获取文章失败");
//                    } else {
//                        callback.Finish(s);
//                        //mArticleCache.put(url, s);
//                    }
//                }
//            }.execute(url);
        } else
            callback.Falure("网络连接错误");
    }
    public void getPicture(final String url, final IGetPictureCallback callback)
    {
        String html = null;
        PictureItem item = null;
        if ((item = mPictureCache.get(url)) != null)
            callback.Finish(item);
        else if (app.getRequestQueue().getCache().get(url) != null) {
            try {
                html = new String(app.getRequestQueue().getCache().get(url).data, "GBK");

                item = mParser.paresPicture(html);
                callback.Finish(item);
                mPictureCache.put(url, item);
            }
            catch (Exception e) {
                callback.Falure(e.getMessage());
            }
        } else if (NetworkUtils.isNetworkConnected()) {
            StringRequest request = new StringRequest(url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    PictureItem item = mParser.paresPicture(response);
                    if (item != null) {
                        callback.Finish(item);
                        mPictureCache.put(url, item);
                    } else
                        callback.Falure("pares html error");
                }

            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    callback.Falure(error.getMessage());
                }
            });
            app.getRequestQueue().add(request);
        } else
            callback.Falure("网络连接错误");

    }

    public void getThing(final String url, final IGetThingCallback callback)
    {

        String html = null;
        ThingItem item = null;
        if ((item = mThingCache.get(url)) != null)
            callback.Finish(item);
        else if (app.getRequestQueue().getCache().get(url) != null) {
            try {
                html = new String(app.getRequestQueue().getCache().get(url).data, "GBK");
                item = mParser.paresThing(html);
                callback.Finish(item);
                mThingCache.put(url, item);
            }
            catch (Exception e) {
                callback.Falure(e.getMessage());
            }
        } else if (NetworkUtils.isNetworkConnected()) {
            StringRequest request = new StringRequest(url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    ThingItem item = mParser.paresThing(response);
                    if (item != null) {
                        callback.Finish(item);
                        mThingCache.put(url, item);
                    } else
                        callback.Falure("pares html error");
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    callback.Falure(error.getMessage());
                }
            });
            app.getRequestQueue().add(request);
        } else
            callback.Falure("网络连接错误");
    }


    public interface IGetArticleCallback
    {
        void Finish(String content);

        void Falure(String msg);
    }

    public interface IGetPictureCallback
    {
        void Finish(PictureItem item);

        void Falure(String msg);
    }

    public interface IGetThingCallback
    {
        void Finish(ThingItem item);

        void Falure(String msg);
    }
}
