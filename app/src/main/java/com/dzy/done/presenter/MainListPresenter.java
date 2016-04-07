package com.dzy.done.presenter;

import android.util.Log;

import com.dzy.done.bean.ListItem;
import com.dzy.done.model.ListModel;
import com.dzy.done.model.ListModelCallback;
import com.dzy.done.view.ContentListView;

import java.util.List;

/**
 *  主界面list的presenter
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainListPresenter implements ListPresenter
{

    ContentListView mView;
    ListModel mModel;
    int mType;
    boolean isLoading = false;
    private ListModelCallback mCallback = new ListModelCallback()
    {
        @Override
        public void onFinish(List<ListItem> items)
        {
            if (mView==null)
                return;
            mView.showDatas(items);
            mView.hideProgress();
            isLoading = false;
            Log.i("tag", "presenter onFinish    items:" + items.size() + "");
        }

        @Override
        public void OnFalure(String msg)
        {
            if (mView==null)
                return;

            isLoading = false;
            Log.e("tag", "onFalure    " + msg);
            mView.failload();
        }
    };

    public MainListPresenter(int type)
    {
        mType = type;
        mModel = ListModel.getInstant();
    }

    /**
     * 加载数据
     * @param page 页数
     */
    @Override
    public void loadListDates(int page)
    {
        if (isLoading)
            return;

        isLoading = true;
        mView.showProgress();
        mModel.LoadDatas(page,mType,mCallback);
    }

    @Override
    public void attachView(ContentListView view)
    {
        mView = view;
    }

    @Override
    public void detach()
    {
        mView = null;
    }


}
