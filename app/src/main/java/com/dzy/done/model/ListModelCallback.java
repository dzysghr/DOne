package com.dzy.done.model;

import com.dzy.done.bean.ListItem;

import java.util.List;

/**
 *
 * Created by dzysg on 2015/10/9 0009.
 */
public interface ListModelCallback
{

    void onFinish(List<ListItem> items);

    void OnFalure(String msg);
    
}
