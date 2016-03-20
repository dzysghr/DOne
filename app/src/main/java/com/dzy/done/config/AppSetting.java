package com.dzy.done.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  存放着一些设置参数
 * Created by dzysg on 2016/3/14 0014.
 */
public class AppSetting
{

    public static AppSetting single = null;

    public static AppSetting  getSetting()
    {
        return single;
    }

    public static void initSetting(Context context)
    {
        SharedPreferences p = context.getSharedPreferences("setting", 0);
        single = new AppSetting();
        single.setFontSize(Integer.parseInt(p.getString("FontSize","120")));
    }
    private int FontSize = 120;


    public int getFontSize()
    {
        return FontSize;
    }

    public void setFontSize(int fontSize)
    {
        FontSize = fontSize;
    }




}
