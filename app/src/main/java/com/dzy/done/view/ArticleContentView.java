package com.dzy.done.view;

import com.dzy.done.bean.ArticleItem;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public interface ArticleContentView
{
     void showContent(ArticleItem c);
     void showToast(String error);
     void loading();
     void setFavoriteMenuState(boolean b);
}