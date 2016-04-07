package com.dzy.done.adapter.Holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public class FavoriteListHolder extends BaseHolder<ListItem>
{
    @Bind(R.id.tv_title) TextView mTitle;
    @Bind(R.id.tv_date) TextView mdate;
    @Bind(R.id.tv_content) TextView mContent;
    @Bind(R.id.img) public ImageView img;

    ListItem mItem;

    public FavoriteListHolder(View itemView, Context context)
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

    }
}
