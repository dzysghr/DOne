package com.dzy.done.util;

import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络数据api，暂时无用
 * Created by dzysg on 2016/2/16 0016.
 */
public class apiService
{
    private ContentParser mParser = new ContentParser();




    public String getArticle(String url)
    {

        int maxStale = 60 * 60 * 24 ; //1周
        Request request = new Request.Builder()
                .url("http://dzyone.applinzi.com/article.php")
                .header("url",url)
                .header("Cache-Control", "max-stale=" + maxStale)
                .build();
        try {
            Response response = HtmlLoader.getHttpClient().newCall(request).execute();
            if(response.isSuccessful()) {
                String result = new String(response.body().bytes(), "GBK");
                return result;
            }
        }
        catch (Exception e)
        {
            MLog.getLogger().d("getArticle network exception");
        }
        return "";
    }

    public ThingItem getThing(String url)
    {
        Request request = new Request.Builder().url(url).header("Cache-Control", "max-stale=3600").build();
        try {
            Response response = HtmlLoader.getHttpClient().newCall(request).execute();
            if(response.isSuccessful()) {
                String result = new String(response.body().bytes(), "GBK");
                return mParser.paresThing(result);
            }
        }
        catch (Exception e)
        {
            MLog.getLogger().d("getThing network exception");
        }

        return null;
    }

    public PictureItem getPicture(String url)
    {
        Request request = new Request.Builder().url(url).header("Cache-Control", "max-stale=3600").build();
        try {
            Response response = HtmlLoader.getHttpClient().newCall(request).execute();
            if(response.isSuccessful()) {
                String result = new String(response.body().bytes(), "GBK");
                return mParser.paresPicture(result);
            }
        }
        catch (Exception e)
        {
            MLog.getLogger().d("getPicture network exception");
        }
        return null;
    }
}
