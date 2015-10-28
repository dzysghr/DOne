package com.dzy.done.presenter;

import com.dzy.done.bean.ListItem;

import java.util.List;

public interface IViewPager {

    void loadData(List<ListItem> dailies);

    void showProgress();

    void hideProgress();

    void failload();

}