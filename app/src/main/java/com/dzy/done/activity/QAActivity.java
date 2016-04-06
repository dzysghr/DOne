package com.dzy.done.activity;

import android.os.Bundle;

import com.dzy.done.presenter.ContentPresenterImpl;
import com.dzy.done.presenter.StringContentPresenter;

/**
 *  问答activity
 * Created by dzysg on 2016/3/20 0020.
 */
public class QAActivity extends WebViewActivity
{
    StringContentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPresenter = new ContentPresenterImpl();
        mPresenter.onAttach(this);
        mPresenter.LoadQAContent(mUrl);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mPresenter.onDetach();
    }

}
