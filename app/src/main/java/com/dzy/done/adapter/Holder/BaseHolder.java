package com.dzy.done.adapter.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public abstract class  BaseHolder<T> extends RecyclerView.ViewHolder
{
    protected Context mContext;

    public BaseHolder(View itemView,Context c)
    {
        super(itemView);
        mContext = c;
    }

    public abstract void setData(T item);

}
