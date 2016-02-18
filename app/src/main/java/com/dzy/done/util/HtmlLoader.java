package com.dzy.done.util;


/**
 *
 * Created by dzysg on 2015/10/9 0009.
 */


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HtmlLoader
{


    private static  OkHttpClient mOkHttpClient;

    public static OkHttpClient getHttpClient()
    {
        if (mOkHttpClient==null)
            mOkHttpClient = new OkHttpClient();

        return mOkHttpClient;
    }


    public static String getStringByUrl(String url,String charset) throws IOException
    {
        Request request = new Request.Builder().url(url).build();
        Response response = getHttpClient().newCall(request).execute();

        if (response.isSuccessful()) {
            String responseUrl = new String(response.body().bytes(),charset==null?"utf-8":charset);
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }



}