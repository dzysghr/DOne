package com.dzy.done.db;

import com.dzy.done.bean.PictureItem;

/**
 * Created by dzysg on 2015/10/11 0011.
 */
public interface IPictureDAO {

    public PictureItem select(String num);
    public boolean insert(PictureItem item);
    public boolean delete(PictureItem item);
    public boolean update(PictureItem item);


}
