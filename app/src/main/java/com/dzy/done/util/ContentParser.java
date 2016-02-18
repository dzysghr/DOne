package com.dzy.done.util;

import com.dzy.done.bean.PictureItem;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.config.OneApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 从html提取内容转成实体类
 * Created by dzysg on 2016/2/16 0016.
 */
public class ContentParser
{
    public String paresArticle(String Html)
    {
        MLog.getLogger().d(Html);

        Document doc;
        StringBuilder sb = new StringBuilder();
        try {
            doc = Jsoup.parse(Html);
            Elements div = doc.getElementsByClass("articulo-contenido");
            Elements ps = div.get(0).getElementsByTag("p");
            int size = ps.size();
            for (int i = 0; i < size; i++) {
                sb.append(ps.get(i).ownText());
                sb.append("\r\n\r\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public PictureItem paresPicture(String html)
    {
        Document doc;
        PictureItem item = null;
        try {
            doc = Jsoup.parse(html);
            item = new PictureItem();

            Element root = doc.getElementsByClass("d").get(0);

            String src = root.getElementsByClass("one-imagen").get(0).getElementsByTag("img").get(0).attr("src");
            if (!src.startsWith("http"))
                src = OneApi.One + src;
            item.setImg(src);

            String num = root.getElementsByClass("one-titulo").get(0).ownText();
            item.setNum(num);

            String author = root.getElementsByClass("one-imagen-leyenda").get(0).ownText();
            item.setAuthor(author);

            String content = root.getElementsByClass("one-cita").get(0).ownText();
            item.setContent("     " + content);

            String day = root.getElementsByClass("dom").get(0).ownText();
            item.setDay(day);

            String year = root.getElementsByClass("may").get(0).ownText();
            item.setYear(year);
            return item;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ThingItem paresThing(String html)
    {
        Document doc;
        ThingItem item = null;
        try {
            doc = Jsoup.parse(html);
            item = new ThingItem();
            Element root = doc.getElementsByClass("d").get(0);
            String src = root.getElementsByClass("cosas-imagen").get(0).getElementsByTag("img").get(0).attr("src");
            if (!src.startsWith("http"))
                src = OneApi.One +src;
            item.setSrc(src);
            String name = root.getElementsByClass("cosas-titulo").get(0).ownText();
            item.setName(name);

            String content = root.getElementsByClass("cosas-contenido").get(0).ownText();
            item.setContent(content);
            return item;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
