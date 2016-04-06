package com.dzy.done.network;

import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * retrofit实现，网络数据接口，json,列表一个小时过期，详情一个月过期
 * Created by dzysg on 2016/3/4 0004.
 */
public interface ApiServer
{

    @Headers("Cache-Control: public, max-age=3600")
    @GET("/articlelist.php")
    Call<List<ListItem>> getArticleList(@Query("page") int page);

    @Headers("Cache-Control: public, max-age=2678400")
    @GET("/article.php")
    Call<String> getArticle(@Query("url") String url);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("/thinglist.php")
    Call<List<ListItem>> getThingList(@Query("page") int page);


    @Headers("Cache-Control: public, max-age=2678400")
    @GET("/thing.php")
    Call<ThingItem> getThing(@Query("url") String url);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("/picturelist.php")
    Call<List<ListItem>> getPictureList(@Query("page") int page);

    @Headers("Cache-Control: public, max-age=2678400")
    @GET("/picture.php")
    Call<PictureItem> getPicture(@Query("url") String url);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("/QAlist.php")
    Call<List<ListItem>> getQAList(@Query("page") int page);

    @Headers("Cache-Control: public, max-age=2678400")
    @GET("/QA.php")
    Call<String> getQA(@Query("url") String url);
}
