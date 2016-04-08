package com.dzy.done.bean;

import com.dzy.easydao.dborm.annotation.Column;
import com.dzy.easydao.dborm.annotation.ID;
import com.dzy.easydao.dborm.annotation.Table;

/**
 * 为了方便，文章和问答共用一个实体类
 * Created by dzysg on 2016/3/21 0021.
 */
@Table
public class ArticleItem
{
    @ID
    private long id;

    @Column
    private String url;
    @Column
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
