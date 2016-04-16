package com.dzy.done.presenter;

import com.dzy.done.bean.ArticleItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.model.DBModel;
import com.dzy.done.model.ContentModel;
import com.dzy.done.view.ArticleContentView;

/**
 * 文章和问答只有一个方法不同，所以共用这一个presenter
 * Created by dzysg on 2016/4/6 0006.
 */
public class ArticlePresenterImpl implements ArticlePresenter
{
    ArticleContentView mView;
    ContentModel mModel;
    DBModel mDB;

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
        mDB = DBModel.getInstance();
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

    /** 加载文章
     * @param url 地址
     */
    @Override
    public void LoadArticleContent(String url)
    {
        mModel.getArticle(url, mCallback);
    }

    /** 加载问答
     * @param url 地址
     */
    @Override
    public void LoadQAContent(String url)
    {
        mModel.getQA(url,mCallback);
    }

    /** 将当前内容保存在数据库中，供收藏夹用
     * @param item 列表的item
     */
    @Override
    public void saveToFavorite(ListItem item)
    {
        mDB.insert(item);
        mView.setFavoriteMenuState(true);
    }

    /** 检查当前的item是否在数据库中
     * @param item 检查的对象
     */
    @Override
    public void ExistfromFavorite(ListItem item)
    {
        mView.setFavoriteMenuState(mDB.exist(item));
    }

    /** 从数据库中删除当前item
     * @param item 要删除的对象
     */
    @Override
    public void deleteFromFavorite(ListItem item)
    {
        mDB.delete(item);
        mView.setFavoriteMenuState(false);
    }
}
