package com.dzy.done.presenter;

import android.util.Log;

import com.dzy.done.bean.ListItem;
import com.dzy.done.config.app;
import com.dzy.done.model.IListModel;
import com.dzy.done.model.ListModelimpl;
import com.dzy.done.model.ListModelCallback;
import com.dzy.done.view.ContentListView;

import java.util.List;

/**
 *  主界面list的presenter
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainListPresenter implements ListPresenter
{

    ContentListView mPager;
    IListModel mModel;
    int mType;
    boolean isLoading = false;
    private ListModelCallback mCallback = new ListModelCallback()
    {
        @Override
        public void onFinish(List<ListItem> items)
        {

            mPager.showDatas(items);
            mPager.hideProgress();
            isLoading = false;
            Log.i("tag", "presenter onFinish    items:" + items.size() + "");
        }

        @Override
        public void OnFalure(String msg)
        {
            isLoading = false;
            Log.e("tag", "onFalure    " + msg);
            mPager.failload();
        }
    };

    public MainListPresenter(ContentListView pager, int type)
    {
        mPager = pager;
        mType = type;
        mModel = new ListModelimpl(mType, mCallback, app.getApiServer());
    }


    /**
     * 加载数据
     *
     * @param page 页数
     */
    public void LoadListDates(int page)
    {
        if (isLoading)
            return;

        isLoading = true;
        mPager.showProgress();
        mModel.LoadDatas(page);
    }
}
