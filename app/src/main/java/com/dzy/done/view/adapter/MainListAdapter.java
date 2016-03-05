package com.dzy.done.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.view.activity.ArticleActiviry;
import com.dzy.done.view.activity.MainActivity;
import com.dzy.done.view.activity.PictureActivity;
import com.dzy.done.view.activity.ThingActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页list的adapter，三个页面共用
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyHolder>
{

    private List<ListItem> mDatas;
    private Context mContext;

    public MainListAdapter(Context context, List<ListItem> list)
    {
        mContext = context;
        mDatas = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        //点击自定义的红色点击波浪反馈
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            view.setBackground(mContext.getResources().getDrawable(R.drawable.ripple, mContext.getTheme()));
        }

        return new MyHolder(view, mContext);

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position)
    {

        ListItem item = mDatas.get(position);
        holder.setContent(item);

        if (item.getType() == 2 || item.getType() == 3)
            Picasso.with(mContext).load(item.getImg()).fit().into(holder.img);
    }


    public static class MyHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.tv_title) TextView mTitle;
        @Bind(R.id.tv_date) TextView mdate;
        @Bind(R.id.tv_content) TextView mContent;
        @Bind(R.id.img) public ImageView img;


        Context mContext;
        ListItem mItem;

        public MyHolder(View itemView, Context context)
        {
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
        public void onClick(View v)
        {

            //如果是文章
            if (mItem.getType() == ListItem.ARTICLE) {
                Intent intent = new Intent(mContext, ArticleActiviry.class);
                intent.putExtra("url", mItem.getUrl());
                intent.putExtra("title", mItem.getTitle());
                intent.putExtra("date", mItem.getDate());

                mContext.startActivity(intent);

            }
            //如果是图片
            else if (mItem.getType() == ListItem.PICTURE) {
                Intent intent = new Intent(mContext, PictureActivity.class);
                intent.putExtra("url", mItem.getUrl());
                intent.putExtra("num", mItem.getTitle().split(" ", 2)[0]);
                if (img.getDrawable() instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    intent.putExtra("bitmap", bitmap);
                }
                intent.putExtra("author", mItem.getTitle().split(" ", 2)[1]);

                if (Build.VERSION.SDK_INT >= 21) {
                    ViewCompat.setTransitionName(img,mItem.getUrl());

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) (mContext), img, mItem.getUrl());
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
            //如果是 东西
            else if (mItem.getType() == ListItem.THING) {
                Intent intent = new Intent(mContext, ThingActivity.class);
                intent.putExtra("title", mItem.getTitle());
                intent.putExtra("url", mItem.getUrl());
                if (img.getDrawable() instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    intent.putExtra("bitmap", bitmap);
                }

                if (Build.VERSION.SDK_INT >= 16) {
                    ViewCompat.setTransitionName(img,mItem.getUrl());

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) (mContext), img, mItem.getUrl());
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        }
    }

}
