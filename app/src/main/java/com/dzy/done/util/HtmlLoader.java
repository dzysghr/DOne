package com.dzy.done.util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class HtmlLoader {





    public void RequestHtml(String url,Response.Listener<String> listener,Response.ErrorListener errorListener)
    {
        StringRequest stringRequest = new StringRequest(url,listener,errorListener);

    }



}
