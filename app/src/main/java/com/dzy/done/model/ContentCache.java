package com.dzy.done.model;

import android.support.v4.util.LruCache;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.config.OneApi;
import com.dzy.done.config.app;
import com.dzy.done.util.NetworkUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 没用到
 * Created by dzysg on 2015/10/13 0013.
 */
public class ContentCache
{

    private static ContentCache mCache = null;

    private LruCache<String, String> mArticleCache = new LruCache<String, String>(100);
    private LruCache<String, PictureItem> mPictureCache = new LruCache<String, PictureItem>(100);
    private LruCache<String, ThingItem> mThingCache = new LruCache<String, ThingItem>(100);

    static {

        mCache = new ContentCache();
    }

    public static ContentCache get()
    {
        return mCache;
    }

    public void getArticle(final String url, final IGetArticleCallback callback)
    {
        String html = null;
        if ((html = mArticleCache.get(url)) != null)
            callback.Finish(html);
        else if (app.getRequestQueue().getCache().get(url) != null) {
            try {
                html = new String(app.getRequestQueue().getCache().get(url).data, "GBK");
                String content = paresArticle(html);

                callback.Finish(content);
                mArticleCache.put(url, content);
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
                    String content = paresArticle(response);
                    callback.Finish(content);
                    mArticleCache.put(url, content);
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


    public String paresArticle(String Html)
    {
        Document doc;
        StringBuilder sb = new StringBuilder();
        try {
            doc = Jsoup.parse(Html);
            Elements div = doc.getElementsByClass("articulo-contenido");
            Elements ps = div.get(0).getElementsByTag("p");
            int size = ps.size();
            for (int i = 0; i < size; i++) {
                sb.append(ps.get(i).ownText());
                sb.append("\r\n\r\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public PictureItem paresPicture(String html)
    {
        Document doc;
        PictureItem item = null;
        try {
            doc = Jsoup.parse(html);
            item = new PictureItem();

            Element root = doc.getElementsByClass("d").get(0);

            String src = root.getElementsByClass("one-imagen").get(0).getElementsByTag("img").get(0).attr("src");
            item.setImg(OneApi.One + src);

            String num = root.getElementsByClass("one-titulo").get(0).ownText();
            item.setNum(num);

            String author = root.getElementsByClass("one-imagen-leyenda").get(0).ownText();
            item.setAuthor(author);

            String content = root.getElementsByClass("one-cita").get(0).ownText();
            item.setContent("     " + content);

            String day = root.getElementsByClass("dom").get(0).ownText();
            item.setDay(day);

            String year = root.getElementsByClass("may").get(0).ownText();
            item.setYear(year);
            return item;
        }
        catch (Exception e) {
            e.printStackTrace();
            item = null;
        }
        return null;
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

                item = paresPicture(html);
                callback.Finish(item);
                mPictureCache.put(url, item);
            }
            catch (Exception e) {
                callback.Falure(e.getMessage());
            }
        } else if (NetworkUtils.isNetworkConnected())
        {
            StringRequest request = new StringRequest(url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    PictureItem item = paresPicture(response);
                    if (item!=null) {
                        callback.Finish(item);
                        mPictureCache.put(url, item);
                    }
                    else
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
        }else
            callback.Falure("网络连接错误");

    }


    public ThingItem paresThing(String html)
    {
        Document doc;
        ThingItem item = null;
        try
        {
            doc = Jsoup.parse(html);
            item = new ThingItem();
            Element root = doc.getElementsByClass("d").get(0);
            String src = root.getElementsByClass("cosas-imagen").get(0).getElementsByTag("img").get(0).attr("src");
            item.setSrc(OneApi.One + src);

            String name = root.getElementsByClass("cosas-titulo").get(0).ownText();
            item.setName(name);

            String content = root.getElementsByClass("cosas-contenido").get(0).ownText();
            item.setContent(content);
            return item;
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
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
                item = paresThing(html);
                callback.Finish(item);
                mThingCache.put(url,item);
            }
            catch (Exception e) {
                callback.Falure(e.getMessage());
            }
        } else if (NetworkUtils.isNetworkConnected())
        {
            StringRequest request = new StringRequest(url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    ThingItem item = paresThing(response);
                    if (item!=null)
                    {
                    callback.Finish(item);
                    mThingCache.put(url,item);}
                    else
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
        }else
            callback.Falure("网络连接错误");
    }


    public  interface IGetArticleCallback
    {
        public void Finish(String content);

        public void Falure(String msg);
    }

    public  interface IGetPictureCallback
    {
        public void Finish(PictureItem item);

        public void Falure(String msg);
    }

    public  interface IGetThingCallback
    {
        public void Finish(ThingItem item);

        public void Falure(String msg);
    }
}
