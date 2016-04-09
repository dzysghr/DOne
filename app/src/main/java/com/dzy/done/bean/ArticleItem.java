package com.dzy.done.bean;

/**
 * 为了方便，文章和问答共用一个实体类
 * Created by dzysg on 2016/3/21 0021.
 */

public class ArticleItem
{

    private long id;

    private String url;

    private String Content;

    public ArticleItem(String url, String content)
    {
        this.url = url;
        Content = content;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getContent()
    {
        return Content;
    }

    public void setContent(String content)
    {
        Content = content;
    }
}
