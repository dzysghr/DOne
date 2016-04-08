package com.dzy.done.view;

import com.dzy.done.bean.PictureItem;

/**
 *
 * Created by dzysg on 2016/4/8 0008.
 */
public interface PictureView
{
    void showPictureInfo(PictureItem item);
    void showMsg(String msg);
    void setFavoriteMenuState(boolean b);
}
