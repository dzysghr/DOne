package com.dzy.done.config;

import com.dzy.done.util.MLog;
import com.dzy.done.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by dzysg on 2016/3/5 0005.
 */
public class MInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        if(!NetworkUtils.isNetworkConnected()){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            MLog.getLogger().d("no network");
        }
        Response originalResponse = chain.proceed(request);
        if(NetworkUtils.isNetworkConnected()){
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }else{
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
