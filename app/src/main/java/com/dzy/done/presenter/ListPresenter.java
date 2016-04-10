package com.dzy.done.presenter;

import com.dzy.done.view.ContentListView;

/**
 *
 * Created by dzysg on 2016/3/10 0010.
 */
public interface ListPresenter
{
    /** 加载第Page页
     */
    void loadListDates();
    void loadMore();
    void attachView(ContentListView view);
    void detach();


}
