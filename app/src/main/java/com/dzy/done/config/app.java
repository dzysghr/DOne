package com.dzy.done.config;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class app extends Application {

    public static RequestQueue mQueue=null;
    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext =this;
        mQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getRequestQueue()
    {
        return mQueue;
    }

    public static Context getContext()
    {
        return mContext;
    }
}
