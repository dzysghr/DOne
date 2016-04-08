package com.dzy.done.presenter;

import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.db.DBManager;
import com.dzy.done.model.ContentModel;
import com.dzy.done.view.PictureView;

/**
 *  p
 * Created by dzysg on 2016/4/8 0008.
 */
public class PicturePresenter
{

    PictureView mPictureView;
    ContentModel mModel;
    DBManager mDB;
    ContentModel.IGetPictureCallback mCallback = new ContentModel.IGetPictureCallback() {
        @Override
        public void Finish(PictureItem item)
        {
            if (mPictureView!=null)
            {
                mPictureView.showPictureInfo(item);
            }
        }

        @Override
        public void Falure(String msg)
        {
            if (mPictureView!=null)
            {
                mPictureView.showMsg(msg);
            }
        }
    };


    public PicturePresenter()
    {
        mModel = ContentModel.get();
        mDB = DBManager.getInstance();
    }


    public void loadPicture(String url)
    {
        mModel.getPicture(url,mCallback);
    }

    public   void onAttach(PictureView view)
    {
        mPictureView = view;
    }

    public  void onDetach()
    {
        mPictureView = null;
        mModel.cancel();
    }

    public  void saveToFavorite(ListItem item, PictureItem pitem)
    {

    }

    public   void checkfromFavorite(ListItem url)
    {

    }

    public  void deleteFromFavorite(ListItem url)
    {

    }


}
