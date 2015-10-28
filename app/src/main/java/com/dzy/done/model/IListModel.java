package com.dzy.done.model;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public interface IListModel
{

    public void LoadDatasFromNetWork(int page);
    public void LoadDatasFromCache(int page);


}
