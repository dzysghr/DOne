package com.dzy.done.activity;

import com.dzy.done.model.ContentModel;

/**
 *  问答activity
 * Created by dzysg on 2016/3/20 0020.
 */
public class QAActivity extends WebViewActiviry
{
    @Override
    public void attachToModel()
    {
        ContentModel.get().getQA(mUrl,this);
    }
}
