package com.dzy.done.presenter;

import com.dzy.done.bean.ArticleItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.model.ContentModel;
import com.dzy.done.view.StringContentView;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public class ContentPresenterImpl implements StringContentPresenter
{
    StringContentView mView;
    ContentModel mModel;
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

    public ContentPresenterImpl()
    {
        mModel = ContentModel.get();
    }

    @Override
    public void onAttach(StringContentView view)
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
        mModel.getArticle(url,mCallback);
    }

    @Override
    public void LoadQAContent(String url)
    {
        mModel.getQA(url,mCallback);
    }

    @Override
    public void saveToFavorite(ListItem item, String content)
    {

    }

    @Override
    public void ExistfromFavorite(ListItem url)
    {
        mView.setFavoriteMenuState(true);
    }

    @Override
    public void deleteFromFavorite(ListItem url)
    {

    }
}
