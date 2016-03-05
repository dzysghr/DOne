package com.dzy.done.Api;

import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 后台接口，json
 * Created by dzysg on 2016/3/4 0004.
 */
public interface ApiServer
{

    @GET("/articlelist.php")
    Call<List<ListItem>> getArticles(@Query("page") int page);


    @GET("/article.php")
    Call<String> getArticle(@Query("url") String url);


    @GET("/thinglist.php")
    Call<List<ListItem>> getThings(@Query("page") int page);


    // TODO: 2016/3/4 0004 后台没完成
    @GET("/thing.php")
    Call<ThingItem> getThing(@Query("url") String url);


    @GET("/picturelist.php")
    Call<List<ListItem>> getPictures(@Query("page") int page);


    @GET("/picture.php")
    Call<PictureItem> getPicture(@Query("url") String url);

}
