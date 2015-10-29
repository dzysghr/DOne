package com.dzy.done.db;

import com.dzy.done.bean.ThingItem;

/**
 * Created by dzysg on 2015/10/11 0011.
 */
public interface IThingDAO {
    ThingItem select(String url);
    boolean insert(ThingItem item);
    boolean delete(ThingItem item);
    boolean update(ThingItem item);
}
