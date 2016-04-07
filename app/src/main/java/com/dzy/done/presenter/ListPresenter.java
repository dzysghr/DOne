package com.dzy.done.presenter;

import com.dzy.done.view.ContentListView;

/**
 *
 * Created by dzysg on 2016/3/10 0010.
 */
public interface ListPresenter
{
    void loadListDates(int page);
    void attachView(ContentListView view);
    void detach();

}
