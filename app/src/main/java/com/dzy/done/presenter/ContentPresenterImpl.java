package com.dzy.done.presenter;

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
        public void Finish(String content)
        {
            if (mView!=null)
                mView.showContent(content);
        }

        @Override
        public void Falure(String msg)
        {
            if (mView!=null)
                mView.failure(msg);
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
}
