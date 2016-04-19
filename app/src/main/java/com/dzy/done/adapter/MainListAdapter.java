package com.dzy.done.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzy.done.R;
import com.dzy.done.adapter.Holder.BaseHolder;
import com.dzy.done.adapter.Holder.MainListHolder;
import com.dzy.done.bean.ListItem;

import java.util.List;

/**
 * 主页list的adapter，三个页面共用
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainListAdapter extends RecyclerView.Adapter<BaseHolder<ListItem>>
{


    private List<ListItem> mDatas;
    private Context mContext;


    public MainListAdapter(Context context, List<ListItem> list)
    {
        mContext = context;
        mDatas = list;
    }

    @Override
    public  BaseHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            return new MainListHolder(view, mContext);

    }



    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(BaseHolder<ListItem> holder, int position)
    {
        ListItem item = mDatas.get(position);
        holder.setData(item);

    }

}
