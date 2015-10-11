package com.dzy.done.model;

import android.os.AsyncTask;
import android.util.Log;

import com.dzy.done.bean.ListItem;
import com.dzy.done.config.OneApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class ContentModel implements IContentModel {

    int mType = 1;
    ModelCallback mCallback;

    static Executor mThreadprool;

    int TimeOut = 2000;


    public ContentModel(int type, ModelCallback callback) {
        mType = type;
        mCallback = callback;

    }

    public Executor getExecutor()
    {
        if (mThreadprool==null)
            mThreadprool = Executors.newFixedThreadPool(4);
        return mThreadprool;
    }


    @Override
    public void LoadDatas(int page) {
        LoadHtml(page);
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void praseHTMLByAsyncTask(String html)
    {
        new praseHtmlDatesTask().execute(html);
        Log.i("tag","prase html");
    }

    public void LoadHtml(int page) {
        new GetHTMLThead(page).start();
        Log.i("tag", "loadHTML thread start");
    }


    private  class GetHTMLThead extends Thread {
        int mPage;

        public GetHTMLThead(int page) {
            mPage = page;
        }
        @Override
        public void run() {
            HttpURLConnection conn;
            String constr = "";
            InputStream is;
            InputStreamReader isr;
            BufferedReader br;

            if (mType == 1)
                constr = OneApi.OneArticle.replace("%d", mPage + "");
            if (mType == 2)
                constr = OneApi.OnePicture.replace("%d", mPage + "");
            if (mType==3)
                constr = OneApi.OneThing.replace("%d",mPage+"");

            try {
                URL url = new URL(constr);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TimeOut);
                conn.setUseCaches(false);
                is = conn.getInputStream();
                isr = new InputStreamReader(is,"GBK");
                br = new BufferedReader(isr);

                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();
                isr.close();
                is.close();
                conn.disconnect();

                praseHTMLByAsyncTask(sb.toString());

            }
            catch (Exception e) {
                e.printStackTrace();
                mCallback.OnFalure(e.getMessage());
            }


        }
    }


    private class praseHtmlDatesTask extends AsyncTask<String, Void, List<ListItem>> {

        @Override
        protected List<ListItem> doInBackground(String... params) {
            String page = params[0];
            if (mType == 1)
                return praseArticles(page);
            if (mType == 2)
                return prasePicture(page);
            if (mType == 3)
                return praseThing(page);

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
