package com.dzy.done.presenter;

import android.util.Log;

import com.dzy.done.bean.ListItem;
import com.dzy.done.model.IListModel;
import com.dzy.done.model.ListModelimpl;
import com.dzy.done.model.ModelCallback;

import java.util.List;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class ListPresenter
{

    IViewPager mPager;
    IListModel mModel;
    int mType;

    ModelCallback mCallback = new ModelCallback()
    {
        @Override
        public void onFinish(List<ListItem> items)
        {

            mPager.showDatas(items);
            mPager.hideProgress();
            Log.i("tag", "presenter onFinish    items:" + items.size() + "");
        }

        @Override
        public void OnFalure(String msg)
        {
            Log.e("tag", "onFalure    " + msg);
            mPager.failload();
        }
    };

    public ListPresenter(IViewPager pager, int type)
    {
        mPager = pager;
        mType = type;
        mModel = new ListModelimpl(mType, mCallback);
    }


    /**
     * 加载数据
     *
     * @param page 页数
     */
    public void LoadDatas(int page)
    {

        mPager.showProgress();
        mModel.LoadDatas(page);
    }
}
