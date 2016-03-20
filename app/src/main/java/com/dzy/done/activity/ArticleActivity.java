package com.dzy.done.activity;

import android.os.Bundle;

import com.dzy.done.model.ContentModel;

/**
 *  文章activity
 * Created by dzysg on 2016/3/20 0020.
 */
public class ArticleActivity extends WebViewActiviry
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void attachToModel()
    {
        ContentModel.get().getArticle(mUrl,this);
    }
}
