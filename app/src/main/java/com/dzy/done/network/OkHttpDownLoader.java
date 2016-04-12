package com.dzy.done.network;

import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 用于picasso的DownLoader，基于OKHTTP3.0，picasso源码中的网络层只能配合OKHTTP2.x的版本
 * Created by dzysg on 2016/3/6 0006.
 */
public class OkHttpDownLoader implements Downloader
{
    OkHttpClient mClient = null;
    public OkHttpDownLoader(OkHttpClient client)
    {
        mClient = client;
    }
    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException
    {
         CacheControl.Builder builder = new CacheControl.Builder();
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                builder.onlyIfCached();
            } else {
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
            }
        }
        Request request = new Request.Builder()
                .cacheControl(builder.build())
                .url(uri.toString())
                .build();
        okhttp3.Response response = mClient.newCall(request).execute();
        return new Response(response.body().byteStream(),response.cacheResponse()!=null,response.body().contentLength());
    }

    @Override
    public void shutdown()
    {
        Log.e("tag", "picasso downloader shutdown");
    }
}
