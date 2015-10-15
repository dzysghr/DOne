package com.dzy.done.db;

import com.dzy.done.bean.ThingItem;

/**
 * Created by dzysg on 2015/10/11 0011.
 */
public interface IThingDAO {
    public ThingItem select(String url);
    public boolean insert(ThingItem item);
    public boolean delete(ThingItem item);
    public boolean update(ThingItem item);
}
