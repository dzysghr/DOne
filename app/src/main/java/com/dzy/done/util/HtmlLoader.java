package com.dzy.done.util;


/**
 * Created by dzysg on 2015/10/9 0009.
 */

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HtmlLoader
{
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static {
        mOkHttpClient.setConnectTimeout(3, TimeUnit.SECONDS);
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException
    {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback)
    {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }



    public static String getStringByUrl(String url,String charset) throws IOException
    {
        Request request = new Request.Builder().url(url).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = new String(response.body().bytes(),charset==null?"utf-8":charset);
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static byte[] getBytes(String url) throws IOException
    {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().bytes();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


}