package com.dzy.done.presenter;

import com.dzy.done.bean.ListItem;
import com.dzy.done.model.DBModel;
import com.dzy.done.view.ContentListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏界面的presenter
 * Created by dzysg on 2016/4/9 0009.
 */
public class FavoritePresenter implements ListPresenter
{

    DBModel mDBModel;
    ContentListView mView;
    boolean loadedAll = false;
    int page = 1;
    List<ListItem> mdatas = new ArrayList<>();

    public FavoritePresenter()
    {
        mDBModel = DBModel.getInstance();
    }

    @Override
    public void loadListDates()
    {
        mView.showProgress();
        page = 1;
        List<ListItem> list = mDBModel.loadList(page);
        if (list.isEmpty())
        {
            loadedAll = true;
        } else
        {
            loadedAll = false;
            mdatas.clear();
            mdatas.addAll(list);
            mView.showDatas(mdatas);
            page++;
        }
        mView.hideProgress();
    }

    @Override
    public void loadMore()
    {
        mView.showProgress();
        if (!loadedAll)
        {
            List<ListItem> list = mDBModel.loadList(page);
            if (list.isEmpty())
            {
                loadedAll = true;
            } else
            {
                loadedAll = false;
                mdatas.addAll(list);
                mView.showDatas(mdatas);
                page++;
            }
        }
        mView.hideProgress();
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
