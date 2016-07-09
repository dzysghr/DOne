package com.dzy.done.bean;

import android.support.annotation.DrawableRes;

/**
 *
 * Created by dzysg on 2016/5/5 0005.
 */
public class BottomSheetItem
{
    private int mDrawableRes;

    private String mTitle;

    public BottomSheetItem(@DrawableRes int drawable, String title) {
        mDrawableRes = drawable;
        mTitle = title;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }
}
