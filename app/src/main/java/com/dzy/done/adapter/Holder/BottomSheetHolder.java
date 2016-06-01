package com.dzy.done.adapter.Holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.BottomSheetItem;

public class BottomSheetHolder extends BaseHolder<BottomSheetItem> implements View.OnClickListener
{
    ImageView mImageView;
    TextView mTextView;
    ItemClickListener mListener;

    public BottomSheetHolder(View itemView, Context c, ItemClickListener listener)
    {
        super(itemView, c);
        itemView.setOnClickListener(this);
        mImageView = (ImageView) itemView.findViewById(R.id.iv);
        mTextView = (TextView) itemView.findViewById(R.id.tv);
        mListener = listener;
    }

    @Override
    public void setData(BottomSheetItem item)
    {
        mTextView.setText(item.getTitle());
        if (item.getDrawableResource() != 0)
        {
            mImageView.setImageResource(item.getDrawableResource());
        }
    }

    @Override
    public void onClick(View v)
    {
        if (mListener != null)
        {
            mListener.onItemClick(getAdapterPosition());
        }
    }


    public interface ItemClickListener
    {

        void onItemClick(int position);

    }
}