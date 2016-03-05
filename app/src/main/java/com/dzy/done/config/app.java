package com.dzy.done.config;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.dzy.done.Api.ApiServer;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class app extends Application
{

    public static RequestQueue mQueue = null;
    public static Context mContext;
    public static ApiServer mApi;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = this;
        mQueue = Volley.newRequestQueue(this);
        VolleyLog.DEBUG = true;

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://dzyone.applinzi.com")
                .baseUrl("http://192.168.199.234")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        mApi = retrofit.create(ApiServer.class);
    }

    public static RequestQueue getRequestQueue()
    {
        return mQueue;
    }

    public static Context getContext()
    {
        return mContext;
    }


    public static ApiServer getApiServer()
    {
        if (mApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl("http://dzyone.applinzi.com")
                    .baseUrl("http://192.168.199.234")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            mApi = retrofit.create(ApiServer.class);
        }
        return mApi;
    }
}
