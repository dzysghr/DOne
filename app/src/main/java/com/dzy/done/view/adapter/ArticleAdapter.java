package com.dzy.done.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.view.activity.ArticleActiviry;
import com.dzy.done.view.activity.PictureActivity;
import com.dzy.done.view.activity.ThingActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dzysg on 2015/10/9 0009.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyHolder>
{

    private List<ListItem> mDatas;
    private Context mContext;

    public ArticleAdapter(Context context,List<ListItem> list)
    {
        mContext= context;
        mDatas = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        MyHolder holder = new MyHolder(view,mContext);

        return holder;

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ListItem item = mDatas.get(position);
        holder.setContent(item);

        if (item.getTYPE()==2||item.getTYPE()==3)
        Picasso.with(mContext).load(item.getImg()).fit().into(holder.img);
    }


    public static class MyHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_date)
        TextView mdate;
        @Bind(R.id.tv_content)
        TextView mContent;
        @Bind(R.id.img)
        ImageView img;


        Context mContext;
        ListItem mItem;

        public MyHolder(View itemView,Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;

        }
        public void setContent(ListItem item)
        {

            mTitle.setText(item.getTitle());
            mdate.setText(item.getDate());
            mContent.setText(item.getContent());
            mItem = item;

        }
        @OnClick(R.id.item_parent)
        public void onClick(View v) {
            if (mItem.getTYPE()==1) {
                Intent intent = new Intent(mContext, ArticleActiviry.class);
                intent.putExtra("url", mItem.getUrl());
                intent.putExtra("title", mItem.getTitle());
                intent.putExtra("date", mItem.getDate());
                mContext.startActivity(intent);
            }
            else if (mItem.getTYPE()==2) {
                Intent intent = new Intent(mContext, PictureActivity.class);
                intent.putExtra("url", mItem.getUrl());
                intent.putExtra("num",mItem.getTitle().split(" ",2)[0]);

                intent.putExtra("author", mItem.getTitle().split(" ",2)[1]);
                mContext.startActivity(intent);
            } else if (mItem.getTYPE()==3) {
                Intent intent = new Intent(mContext, ThingActivity.class);
                intent.putExtra("title", mItem.getTitle());
                intent.putExtra("url", mItem.getUrl());
                mContext.startActivity(intent);

            }
        }
    }

}
