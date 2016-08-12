package com.dzy.done.adapter.Holder;

import android.view.View;
import android.widget.ImageView;

import com.dzy.done.R;
import com.dzy.done.model.bean.BottomSheetItem;

public class BottomSheetHolder extends BaseHolder<BottomSheetItem>
{

    public BottomSheetHolder(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void setData(BottomSheetItem item)
    {
        setTextView(R.id.tv,item.getTitle());
        if (item.getDrawableResource() != 0)
        {
            ImageView view = findView(R.id.iv);
            view.setImageResource(item.getDrawableResource());
        }
    }
}