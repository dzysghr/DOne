package com.dzy.done.model;

import android.os.AsyncTask;

import com.dzy.done.bean.ListItem;
import com.dzy.done.config.OneApi;
import com.dzy.done.config.app;
import com.dzy.done.util.HtmlLoader;
import com.dzy.done.util.ListPareser;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * Created by dzysg on 2015/10/9 0009.
 */
public class ListModelimpl implements IListModel
{

    int mType = 1;
    ModelCallback mCallback;
    private ListPareser mPareser = new ListPareser();


    Executor mExecutor = Executors.newFixedThreadPool(3);
    public ListModelimpl(int type, ModelCallback callback) {
        mType = type;
        mCallback = callback;
    }

    @Override
    public void LoadDatasFromCache(int page)
    {
        String constr = OneApi.getConnectUrl(mType, page);

        if (app.getRequestQueue().getCache().get(constr)!=null) {
            try {
                String response = new String(app.getRequestQueue().getCache().get(constr).data, "GBK");
                praseHTMLByAsyncTask(response);
            }
            catch (Exception e)
            {
                mCallback.OnFalure(e.getMessage());
            }
        }
        else
            mCallback.OnFalure("no cache");
    }


    public void praseHTMLByAsyncTask(String html) {

        new praseHtmlDatesTask().executeOnExecutor(mExecutor,html);
    }

    @Override
    public void LoadDatasFromNetWork(int page)
    {
        String constr = OneApi.getConnectUrl(mType, page);
        new GetHtmlAsynTask().executeOnExecutor(mExecutor,constr);
    }

    private class GetHtmlAsynTask extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String re = null;
            try {
                re = HtmlLoader.getStringByUrl(url,"GBK");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return re;
        }

        @Override
        protected void onPostExecute(String html) {
            if (html!=null)
            praseHTMLByAsyncTask(html);
            else
                mCallback.OnFalure("加载失败");
        }
    }

    //解析html数据
    private class praseHtmlDatesTask extends AsyncTask<String, Void, List<ListItem>> {

        @Override
        protected List<ListItem> doInBackground(String... params) {
            String html = params[0];
            if (mType == 1)
                return mPareser.praseArticles(html,1);
            if (mType == 2)
                return mPareser.prasePictures(html, 2);
            if (mType == 3)
                return mPareser.praseThings(html, 3);
            return null;
        }

        @Override
        protected void onPostExecute(List<ListItem> list) {
            super.onPostExecute(list);
            if (list == null)
                mCallback.OnFalure("prase html error");
            else
                mCallback.onFinish(list);
        }
    }
}
