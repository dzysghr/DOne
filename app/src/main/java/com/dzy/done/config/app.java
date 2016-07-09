package com.dzy.done.config;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dzy.done.network.ApiServer;
import com.dzy.done.network.MInterceptor;
import com.dzy.done.network.OkHttpDownLoader;
import com.dzy.easydao.dborm.orm.EasyDAO;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * Created by dzysg on 2015/10/9 0009.
 */
public class app extends Application
{

    public static Context mContext;
    public static ApiServer mApi;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = this;
        EasyDAO.init(this,"DONE",1);
        AppSetting.initSetting(this);

        File file = new File(this.getCacheDir(),"okhttp");

        OkHttpClient client =new OkHttpClient
                .Builder()
                .addInterceptor(new MInterceptor())
                .cache(new Cache(file, 1024 * 1024 * 100)).build();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttpDownLoader(client))
                .build();
        Picasso.setSingletonInstance(picasso);


        String url =  mContext.getSharedPreferences("setting",0).getString("baseUrl","http://dzyone.applinzi.com/");

        Log.e("tag","base url "+ url);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                //.baseUrl("http://gw35ib.gcpro-sz-01.ghostcloud.cn/DonePHP/")
                //.baseUrl("http://dzyone.applinzi.com/")
                //.baseUrl("http://192.168.199.234")
                //.baseUrl("http://doneapp-1-dzyone.hz.tenxapp.com/DonePHP/")
                .baseUrl(url)

                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        mApi = retrofit.create(ApiServer.class);
    }


    public static Context getContext()
    {
        return mContext;
    }


    public static ApiServer getApiServer()
    {
        return mApi;
    }
}
