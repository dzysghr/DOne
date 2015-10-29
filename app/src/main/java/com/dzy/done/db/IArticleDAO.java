package com.dzy.done.db;

/**
 * Created by dzysg on 2015/10/11 0011.
 */
public interface IArticleDAO {

    String select(String title);
    boolean insert(String title, String content);
    boolean delete(String title);
    boolean update(String title);
}
