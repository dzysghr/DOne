package com.dzy.done.presenter;

import com.dzy.done.bean.ArticleItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.db.DBManager;
import com.dzy.done.model.ContentModel;
import com.dzy.done.view.ArticleContentView;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public class ArticlePresenterImpl implements ArticleContentPresenter
{
    ArticleContentView mView;
    ContentModel mModel;
    DBManager mDB;

    ContentModel.IGetStringCallback mCallback = new ContentModel.IGetStringCallback() {
        @Override
        public void Finish(ArticleItem content)
        {
            if (mView!=null)
                mView.showContent(content);
        }

        @Override
        public void Falure(String msg)
        {
            if (mView!=null)
                mView.showToast(msg);
        }
    };

    public ArticlePresenterImpl()
    {
        mModel = ContentModel.get();
        mDB = DBManager.getInstance();
    }

    @Override
    public void onAttach(ArticleContentView view)
    {
        mView = view;
    }

    @Override
    public void onDetach()
    {
        mView = null;
        mModel.cancel();
    }

    @Override
    public void LoadArticleContent(String url)
    {
        mModel.getArticle(url, mCallback);
    }

    @Override
    public void LoadQAContent(String url)
    {
        mModel.getQA(url,mCallback);
    }

    @Override
    public void saveToFavorite(ListItem item, ArticleItem content)
    {
        mDB.insertArticle(item,content);
    }

    @Override
    public void ExistfromFavorite(ListItem item)
    {
        mView.setFavoriteMenuState(mDB.exist(item));
    }

    @Override
    public void deleteFromFavorite(ListItem url)
    {
        mDB.deleteArticle(url);
    }
}
