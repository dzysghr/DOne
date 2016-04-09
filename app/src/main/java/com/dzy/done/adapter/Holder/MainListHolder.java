package com.dzy.done.adapter.Holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.ui.activity.PictureActivity;
import com.dzy.done.ui.activity.ThingActivity;
import com.dzy.done.ui.activity.WebViewActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public class MainListHolder extends BaseHolder<ListItem>
{
    @Bind(R.id.tv_title) TextView mTitle;
    @Bind(R.id.tv_date) TextView mdate;
    @Bind(R.id.tv_content) TextView mContent;
    @Bind(R.id.img) public ImageView img;

    ListItem mItem;

    public MainListHolder(View itemView, Context context)
    {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(ListItem item)
    {
        mTitle.setText(item.getTitle());
        mdate.setText(item.getDate());
        mContent.setText(item.getContent());
        mItem = item;
        if (item.getType()==ListItem.PICTURE||item.getType()==ListItem.THING)
        {
            Picasso.with(mContext).load(item.getImg()).fit().into(img);
        }
    }


    @OnClick(R.id.item_parent)
    public void onClick(View v)
    {

        //如果是文章或问答
        if (mItem.getType() == ListItem.ARTICLE||mItem.getType()== ListItem.QA) {
            Intent intent = new Intent(mContext,WebViewActivity.class);
            intent.putExtra("item",mItem);
            mContext.startActivity(intent);
        }
        //如果是图片或者东西
        else
        {
            Intent intent = null;
            if(mItem.getType() == ListItem.PICTURE)
             intent  = new Intent(mContext, PictureActivity.class);
            else
                intent = new Intent(mContext,ThingActivity.class);

            intent.putExtra("item",mItem);
            if (img.getDrawable() instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                intent.putExtra("bitmap", bitmap);
            }

            //设置共享元素效果
            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.setTransitionName(img, mItem.getUrl());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)(mContext), img, mItem.getUrl());
                mContext.startActivity(intent, options.toBundle());
            } else {
                mContext.startActivity(intent);
            }
        }

    }
}
