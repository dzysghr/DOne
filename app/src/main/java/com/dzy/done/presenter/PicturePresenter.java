package com.dzy.done.presenter;

import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.model.DBModel;
import com.dzy.done.model.ContentModel;
import com.dzy.done.view.PictureView;

/**
 *  图片页面Presenter
 * Created by dzysg on 2016/4/8 0008.
 */
public class PicturePresenter
{

    PictureView mView;
    ContentModel mModel;
    DBModel mDB;
    ContentModel.IGetPictureCallback mCallback = new ContentModel.IGetPictureCallback() {
        @Override
        public void Finish(PictureItem item)
        {
            if (mView !=null)
            {
                mView.showPictureInfo(item);
            }
        }

        @Override
        public void Falure(String msg)
        {
            if (mView !=null)
            {
                mView.showMsg(msg);
            }
        }
    };

    public PicturePresenter()
    {
        mModel = ContentModel.get();
        mDB = DBModel.getInstance();
    }


    public void loadPicture(String url)
    {
        mModel.getPicture(url,mCallback);
    }

    public   void onAttach(PictureView view)
    {
        mView = view;
    }

    public  void onDetach()
    {
        mView = null;
        mModel.cancel();
    }

    /** 将当前内容保存在数据库中，供收藏夹用
     * @param item 列表的item
     */
    public void saveToFavorite(ListItem item)
    {
        mDB.insert(item);
        mView.setFavoriteMenuState(true);
    }

    /** 检查当前的item是否在数据库中
     * @param item 检查的对象
     */
    public void checkFromFavorite(ListItem item)
    {
        mView.setFavoriteMenuState(mDB.exist(item));
    }

    /** 从数据库中删除当前item
     * @param item 要删除的对象
     */
    public void deleteFromFavorite(ListItem item)
    {
        mDB.delete(item);
        mView.setFavoriteMenuState(false);
    }

}
