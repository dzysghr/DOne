package com.dzy.done.model;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by dzysg on 2015/10/14 0014.
 */
public class StringRequest extends com.android.volley.toolbox.StringRequest
{
    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(url, listener, errorListener);
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        String parsed;
        try {
            parsed = new String(response.data, "GBK");
        }
        catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

}
