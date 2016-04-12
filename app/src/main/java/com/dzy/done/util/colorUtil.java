package com.dzy.done.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;

/**
 *  颜色工具类
 * Created by dzysg on 2016/2/18 0018.
 */
public class colorUtil
{
    /** 使颜色加深，代码来自网络
     * @param RGBValues  颜色rgb值
     * @return 加深后的rgb
     */
    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }


    public static Palette.Swatch getSwatch(Bitmap bitmap)
    {
        Palette p = Palette.from(bitmap).generate();
        Palette.Swatch swatch = p.getVibrantSwatch();
        if (swatch == null)
        {
            swatch = p.getMutedSwatch();
        }
        return swatch;
    }
}
