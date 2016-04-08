package com.dzy.done.db;

import com.dzy.done.bean.ArticleItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;
import com.dzy.easydao.dborm.orm.EasyDAO;

/**
 * 数据库访问层，主要用于收藏夹数据的读写，由于数据库读写数据量少，故没做异步
 * 所有的读写逻辑由一个自己写的orm库EasyDAO完成
 * 相关链接：https://github.com/dzysghr/EasyDao
 * Created by dzysg on 2016/4/7 0007.
 */
public class DBManager
{

    EasyDAO<ListItem> mListItemDAO;
    EasyDAO<ArticleItem> mArticleItemDAO;
    EasyDAO<PictureItem> mPictureItemDAO;
    EasyDAO<ThingItem> mThingItemDAO;

    static public class singleHolder
    {
        static public DBManager single = new DBManager();
    }

    static public DBManager getInstance()
    {
        return singleHolder.single;
    }

    public DBManager()
    {
        mListItemDAO = EasyDAO.getInstance(ListItem.class);
        mArticleItemDAO = EasyDAO.getInstance(ArticleItem.class);
        mPictureItemDAO = EasyDAO.getInstance(PictureItem.class);
        mThingItemDAO = EasyDAO.getInstance(ThingItem.class);

    }


    public ArticleItem getArticle(String url)
    {
        return  mArticleItemDAO.queryFirst("url=?", url);
    }

    public void insertArticle(ListItem listItem,ArticleItem item)
    {
        mArticleItemDAO.save(item);
        mListItemDAO.save(listItem);
    }

    public void deleteArticle(ListItem item)
    {
        mListItemDAO.deleteWhere("url=?",item.getUrl());
        mArticleItemDAO.deleteWhere("url=?",item.getUrl());
    }

    public boolean exist(ListItem item)
    {
        return mListItemDAO.queryWhere("url=?",item.getUrl()).size()>0;
    }

}
