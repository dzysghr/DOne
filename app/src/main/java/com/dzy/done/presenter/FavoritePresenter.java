package com.dzy.done.presenter;

import com.dzy.done.bean.ListItem;
import com.dzy.done.model.DBModel;
import com.dzy.done.view.ContentListView;

import java.util.List;

/**
 *  收藏界面的p
 * Created by dzysg on 2016/4/9 0009.
 */
public class FavoritePresenter implements ListPresenter
{

    DBModel mDBModel;
    ContentListView mView;
    boolean loadedAll = false;
    public FavoritePresenter()
    {
        mDBModel = DBModel.getInstance();
    }

    @Override
    public void loadListDates(int page)
    {
        if (loadedAll&&page>1)
            return;
        List<ListItem> list = mDBModel.loadList(page);
        if (list.isEmpty())
        {
            loadedAll=true;
        }
        else
        {
            loadedAll =false;
            mView.showDatas(list);
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
