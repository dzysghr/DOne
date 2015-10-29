package com.dzy.done.model;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public interface IListModel
{

    void LoadDatasFromNetWork(int page);
    void LoadDatasFromCache(int page);


}
