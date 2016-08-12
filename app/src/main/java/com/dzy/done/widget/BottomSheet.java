package com.dzy.done.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dzy.done.R;
import com.dzy.done.adapter.BaseAdapter;
import com.dzy.done.adapter.Holder.BaseHolder;
import com.dzy.done.adapter.Holder.BottomSheetHolder;
import com.dzy.done.model.bean.BottomSheetItem;

import java.util.List;

/**
 * 详情页面的 bottomsheet
 * Created by dzysg on 2016/5/5 0005.
 */
public class BottomSheet extends BottomSheetDialog
{

    List<BottomSheetItem> mList;

    BaseHolder.OnItemClickListener mListener;

    public BottomSheet(@NonNull Context context, @StyleRes int theme,@NonNull List<BottomSheetItem> list,BaseHolder.OnItemClickListener listener)
    {
        super(context, theme);
        mList = list;
        mListener = listener;
        init();
    }


    public void setList(List<BottomSheetItem> list)
    {
        mList = list;
    }

    private void init()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout, null);
        RecyclerView review = (RecyclerView) view.findViewById(R.id.review);
        review.setHasFixedSize(true);
        review.setLayoutManager(new LinearLayoutManager(getContext()));
        review.setAdapter(new BaseAdapter<BottomSheetItem>(this.getContext(),mList,R.layout.bottom_sheet_item){
                              @Override
                              public BaseHolder<BottomSheetItem> createHolder(View v, Context context)
                              {
                                  BaseHolder<BottomSheetItem> holder = new BottomSheetHolder(v);
                                    holder.setOnItemClickListener(mListener);


                                  return holder;
                              }
                          });
        setContentView(view);
    }
}
