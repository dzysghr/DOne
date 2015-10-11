package com.dzy.done.presenter;

import android.util.Log;

import com.dzy.done.bean.ListItem;
import com.dzy.done.model.ContentModel;
import com.dzy.done.model.IContentModel;
import com.dzy.done.model.ModelCallback;
import com.dzy.done.util.NetworkUtils;

import java.util.List;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class ContentPresenter {

    IViewPager mPager;
    IContentModel mModel;



    int mType;
    ModelCallback mCallback = new ModelCallback() {
        @Override
        public void onFinish(List<ListItem> items) {

            mPager.loadData(items);
            mPager.hideProgress();
            Log.i("tag",items.size()+"");

        }

        @Override
        public void OnFalure(String msg) {
            Log.i("tag",msg);
        }
    };


    public ContentPresenter(IViewPager pager,int type)
    {
        mPager =pager;
        mType = type;
        mModel = new ContentModel(mType,mCallback);
    }


    public void LoadDatas(int page)
    {
        Log.i("tag","check network");

         if (NetworkUtils.isNetworkConnected()) {
             mPager.showProgress();
             mModel.LoadDatas(page);
             Log.i("tag","Model load datas");
         }
        else
             mPager.networkunavaiable();
    }



}
