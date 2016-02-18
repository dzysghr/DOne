package com.dzy.done.util;

import com.dzy.done.bean.ListItem;
import com.dzy.done.config.OneApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dzysg on 2016/2/17 0017.
 */
public class ListPareser
{

    public  List<ListItem> praseArticles(String html,int mType) {
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

    public List<ListItem> prasePictures(String html,int mType) {
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
                if (!src.startsWith("http"))
                  src =  OneApi.One + src;
                item.setImg(src);

                String href = li.getElementsByTag("a").get(0).attr("href");
                item.setUrl(href.replace("/a/ONE_tupian/", OneApi.OnePictureHead));

                item.setTitle(li.getElementsByTag("b").html());
                item.setDate(li.getElementsByClass("meta-data").get(0).ownText());
                item.setContent(li.getElementsByTag("p").get(0).ownText());
                item.setTYPE(mType);
                list.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ListItem> praseThings(String html, int mType) {
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
}
