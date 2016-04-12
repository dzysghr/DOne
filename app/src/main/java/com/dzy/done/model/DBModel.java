package com.dzy.done.model;

import com.dzy.done.bean.ListItem;
import com.dzy.easydao.dborm.orm.EasyDAO;

import java.util.List;

/**
 * 数据库访问层，主要用于收藏夹数据的读写，由于数据库读写数据量少速度快，故没做异步
 * 所有的sql逻辑由一个自己写的orm库EasyDAO完成
 * 相关链接：https://github.com/dzysghr/EasyDao
 * Created by dzysg on 2016/4/7 0007.
 */
public class DBModel
{

    EasyDAO<ListItem> mListItemDAO;
    int pageCount = 10;


    static public class singleHolder
    {
        static public DBModel single = new DBModel();
    }

    static public DBModel getInstance()
    {
        return singleHolder.single;
    }

    public void Close()
    {
        mListItemDAO.closeDB();
    }

    public DBModel()
    {
        mListItemDAO = EasyDAO.getInstance(ListItem.class);
    }

    public List<ListItem> loadList(int page)
    {
        String limit = String.valueOf((page-1)*pageCount)+","+String.valueOf(page*pageCount);
        return mListItemDAO.queryWhere(null,null,null,limit);
    }

    public void insert(ListItem listItem)
    {
        mListItemDAO.save(listItem);
    }

    public void delete(ListItem item)
    {
        mListItemDAO.deleteWhere("url=?",item.getUrl());
    }

    public boolean exist(ListItem item)
    {
        return mListItemDAO.queryWhere("url=?",item.getUrl()).size()>0;
    }
}
