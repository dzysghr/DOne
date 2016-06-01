package com.dzy.done.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzy.done.adapter.Holder.BaseHolder;

import java.util.List;

/**
 * 主页list的adapter，三个页面共用
 * Created by dzysg on 2015/10/9 0009.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>>
{

    private List<T> mDatas;
    private Context mContext;
    int itemLayoutId;

    public BaseAdapter(Context context, List<T> list, @LayoutRes int layoutid)
    {
        mContext = context;
        mDatas = list;
        itemLayoutId = layoutid;
    }

    @Override
    public  BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
            View view = LayoutInflater.from(mContext).inflate(itemLayoutId, parent, false);
            return createHolder(view,mContext);
    }

    public abstract BaseHolder<T> createHolder(View v,Context context);


    public void setDatas(List<T> datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position)
    {
        T item = mDatas.get(position);
        holder.setData(item);
    }

}
