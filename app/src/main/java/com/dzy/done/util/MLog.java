package com.dzy.done.util;

import android.util.Log;

/**
 * Logger
 * Created by dzysg on 2016/2/12 0012.
 */
public class MLog
{
    static private MLog mMLog = null;


    public void setTag(String tag)
    {
        this.tag = tag;
    }

    private String tag = "tag";


    static public MLog getLogger()
    {
        if (mMLog==null)
            mMLog = new MLog();
        return mMLog;
    }

    public void d(String m)
    {
        Log.d(tag, m);
    }

    public void i(String m)
    {
        Log.i(tag, m);
    }

    public void e(String m)
    {
        Log.e(tag, m);
    }
}
