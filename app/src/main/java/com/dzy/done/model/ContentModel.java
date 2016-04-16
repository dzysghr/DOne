package com.dzy.done.model;

import android.support.v4.util.LruCache;

import com.dzy.done.bean.ArticleItem;
import com.dzy.done.network.ApiServer;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.config.app;
import com.dzy.done.util.MLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 数据获取Model，单例
 * Created by dzysg on 2015/10/13 0013.
 */
public class ContentModel
{

    private static ContentModel Model = null;
    private ApiServer mApiServer;
    private LruCache<String, ArticleItem> mStringLruCache = new LruCache<>(16);
    private LruCache<String, PictureItem> mPictureCache = new LruCache<>(16);
    private LruCache<String, ThingItem> mThingCache = new LruCache<>(16);
    private Call mCall = null;

    public ContentModel()
    {
        mApiServer = app.getApiServer();
    }

    public static ContentModel get()
    {
        if (Model == null)
            Model = new ContentModel();
        return Model;
    }

    public void cancel()
    {
        if (mCall!=null)
        {
            mCall.cancel();
            mCall = null;
        }

    }

    public void getArticle(final String url, final IGetStringCallback callback)
    {
        MLog.getLogger().d("start getArticle " + url);
        if (url==null)
            return;

        ArticleItem cache = mStringLruCache.get(url);
        if (cache!=null)
        {
            callback.Finish(cache);
            return;
        }



        Call<String> call = mApiServer.getArticle(url);
        mCall = call;
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (!call.isCanceled())
                {
                    ArticleItem item = new ArticleItem(url,response.body());
                    mStringLruCache.put(url,item);
                    callback.Finish(item);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                if (!call.isCanceled())
                {
                    callback.Falure(t.getMessage());
                }
                MLog.getLogger().e(t.getMessage());
            }
        });
    }

    public void getPicture(final String url, final IGetPictureCallback callback)
    {
        MLog.getLogger().d("start picture "+url);

        PictureItem cache = mPictureCache.get(url);
        if (cache!=null)
        {
            callback.Finish(cache);
            return;
        }

        Call<PictureItem> call = mApiServer.getPicture(url);
        mCall = call;
        call.enqueue(new Callback<PictureItem>()
        {
            @Override
            public void onResponse(Call<PictureItem> call, Response<PictureItem> response)
            {
                if (call.isCanceled())
                    return;
                mPictureCache.put(url, response.body());
                callback.Finish(response.body());
            }

            @Override
            public void onFailure(Call<PictureItem> call, Throwable t)
            {
                if (!call.isCanceled())
                {
                    callback.Falure(t.getMessage());
                }
                MLog.getLogger().e(t.getMessage());
            }
        });
    }

    public void getThing(final String url, final IGetThingCallback callback)
    {
        MLog.getLogger().d("start getThing "+url);
        ThingItem cache = mThingCache.get(url);
        if (cache!=null)
        {
            callback.Finish(cache);
            return;
        }

        Call<ThingItem> call = mApiServer.getThing(url);
        mCall = call;
        call.enqueue(new Callback<ThingItem>()
        {
            @Override
            public void onResponse(Call<ThingItem> call, Response<ThingItem> response)
            {
                if (call.isCanceled())
                    return;
                mThingCache.put(url, response.body());
                callback.Finish(response.body());
            }

            @Override
            public void onFailure(Call<ThingItem> call, Throwable t)
            {
                if (!call.isCanceled())
                {
                    callback.Falure(t.getMessage());
                }
                MLog.getLogger().e(t.getMessage());

            }
        });
    }


    public void getQA(final String url, final IGetStringCallback callback)
    {
        MLog.getLogger().d("start QA " + url);
        ArticleItem cache = mStringLruCache.get(url);
        if (cache!=null)
        {
            callback.Finish(cache);
            return;
        }

        Call<String> call = mApiServer.getQA(url);
        mCall = call;
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (call.isCanceled())
                return;
                ArticleItem item = new ArticleItem(url,response.body());
                mStringLruCache.put(url,item);
                callback.Finish(new ArticleItem(url,response.body()));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                if (!call.isCanceled())
                {
                    callback.Falure(t.getMessage());
                }
                MLog.getLogger().e(t.getMessage());
            }
        });
    }

    public interface IGetStringCallback
    {
        void Finish(ArticleItem content);

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
