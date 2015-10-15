package com.dzy.done.db;

/**
 * Created by dzysg on 2015/10/11 0011.
 */
public interface IArticleDAO {

    public String select(String title);
    public boolean insert(String title,String content);
    public boolean delete(String title);
    public boolean update(String title);
}
