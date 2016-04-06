package com.dzy.done.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.view.Holder.BaseHolder;
import com.dzy.done.view.Holder.FavoriteListHolder;
import com.dzy.done.view.Holder.MainListHolder;

import java.util.List;

/**
 * 主页list的adapter，三个页面共用
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainListAdapter extends RecyclerView.Adapter<BaseHolder<ListItem>>
{
    /**
     * 表明这是用于主页的list
     */
    public static int MainViewType = 1;

    /**
     * 表明这是用于收藏夹的list
     */
    public static int FavoriteViewType = 2;

    private List<ListItem> mDatas;
    private Context mContext;
    private int mType = 1;



    public MainListAdapter(Context context, List<ListItem> list,int type)
    {
        mContext = context;
        mDatas = list;
        mType = type;
    }

    @Override
    public BaseHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        if (mType==MainViewType)
            return new MainListHolder(view, mContext);
        else if (mType==FavoriteViewType)
            return new  FavoriteListHolder(view,mContext);
        else
            throw new IllegalArgumentException("illegal MainListAdapter type "+mType);
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
