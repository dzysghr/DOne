package com.dzy.done.config;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;

import java.io.IOException;
import java.util.Map;

/**
 *
 * Created by dzysg on 2016/2/12 0012.
 */
public class OkHttpStack implements HttpStack
{


    @Override
    public org.apache.http.HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError
    {

        return null;
    }
}
