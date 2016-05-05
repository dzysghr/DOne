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
import com.dzy.done.adapter.Holder.BottomSheetAdapter;
import com.dzy.done.bean.BottomSheetItem;

import java.util.List;

/**
 * 详情页面的 bottomsheet
 * Created by dzysg on 2016/5/5 0005.
 */
public class BottomSheet extends BottomSheetDialog
{

    List<BottomSheetItem> mList;
    BottomSheetAdapter.ItemClickListener mListener;

    public BottomSheet(@NonNull Context context, @StyleRes int theme,@NonNull List<BottomSheetItem> list,BottomSheetAdapter.ItemClickListener listener)
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
        review.setAdapter(new BottomSheetAdapter(mList, mListener));
        setContentView(view);
    }
}
