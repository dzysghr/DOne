package com.dzy.done.model;

import android.os.AsyncTask;

import com.dzy.done.bean.ListItem;
import com.dzy.done.config.OneApi;
import com.dzy.done.config.app;
import com.dzy.done.util.HtmlLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class ListModelimpl implements IListModel
{

    int mType = 1;
    ModelCallback mCallback;
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

    public List<ListItem> praseArticles(String html) {
        Document doc;
        List<ListItem> list = null;
        try {
            doc = Jsoup.parse(html);
            list = new ArrayList<ListItem>();

            Elements lis = doc.getElementsByClass("photo-big");
            int size = lis.size();
            for (int i = 0; i < size; i++) {
                Element li = lis.get(i);
                ListItem item = new ListItem();
                String href = li.getElementsByTag("a").get(0).attr("href");
                item.setUrl(href.replace("/a/ONE_wenzhang/", OneApi.OneArticleHead));
                item.setTitle(li.getElementsByTag("b").html());
                item.setDate(li.getElementsByClass("meta-data").get(0).html());
                item.setContent(li.getElementsByTag("p").get(0).html());
                item.setTYPE(mType);
                list.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ListItem> prasePicture(String html) {
        Document doc;
        List<ListItem> list = new ArrayList<ListItem>();
        try {
            doc = Jsoup.parse(html);
            Elements lis = doc.getElementsByClass("photo-big");
            int size = lis.size();
            for (int i = 0; i < size; i++) {
                Element li = lis.get(i);
                ListItem item = new ListItem();

                String src = li.getElementsByTag("img").get(0).attr("src");
                item.setImg(OneApi.One + src);

                String href = li.getElementsByTag("a").get(0).attr("href");
                item.setUrl(href.replace("/a/ONE_tupian/", OneApi.OnePictureHead));

                item.setTitle(li.getElementsByTag("b").html());
                item.setDate(li.getElementsByClass("meta-data").get(0).html());
                item.setContent(li.getElementsByTag("p").get(0).html());
                item.setTYPE(mType);
                list.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ListItem> praseThing(String html) {
        Document doc;
        List<ListItem> list = new ArrayList<ListItem>();
        try {
            doc = Jsoup.parse(html);
            Elements lis = doc.getElementsByClass("photo-big");
            int size = lis.size();
            for (int i = 0; i < size; i++) {
                Element li = lis.get(i);
                ListItem item = new ListItem();

                String src = li.getElementsByTag("img").get(0).attr("src");
                item.setImg(OneApi.One + src);

                String href = li.getElementsByTag("a").get(0).attr("href");
                item.setUrl(href.replace("/a/ONE_dongxi/", OneApi.OneThingHead));

                item.setTitle(li.getElementsByTag("b").html());
                item.setDate(li.getElementsByClass("meta-data").get(0).html());
                item.setContent(li.getElementsByTag("p").get(0).html());
                item.setTYPE(mType);
                list.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
                return praseArticles(html);
            if (mType == 2)
                return prasePicture(html);
            if (mType == 3)
                return praseThing(html);
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
