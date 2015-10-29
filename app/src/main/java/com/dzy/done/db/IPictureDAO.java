package com.dzy.done.db;

import com.dzy.done.bean.PictureItem;

/**
 * Created by dzysg on 2015/10/11 0011.
 */
public interface IPictureDAO {

    PictureItem select(String num);
    boolean insert(PictureItem item);
    boolean delete(PictureItem item);
    boolean update(PictureItem item);


}
