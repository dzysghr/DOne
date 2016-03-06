package com.dzy.done.config;

import android.net.Uri;

import com.squareup.picasso.Downloader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 *
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

//        CacheControl cacheControl = null;
//        if (networkPolicy != 0) {
//            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
//                cacheControl = CacheControl.FORCE_CACHE;
//            } else {
//                CacheControl.Builder builder = new CacheControl.Builder();
//                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
//                    builder.noCache();
//                }
//                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
//                    builder.noStore();
//                }
//                cacheControl = builder.build();
//            }
//        }

        Request request = new Request.Builder()
                .url(uri.toString())
                .header("Cache-Control", "public, max-age=2678400").build();
        okhttp3.Response response = mClient.newCall(request).execute();
        return new Response(response.body().byteStream(),false,response.body().contentLength());
    }

    @Override
    public void shutdown()
    {

    }
}
