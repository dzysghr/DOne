package com.dzy.done.view;

import com.dzy.done.bean.ListItem;

import java.util.List;

public interface ContentListView
{

    void showDatas(List<ListItem> dailies);

    void showProgress();

    void hideProgress();

    void showMsg(String msg);

}