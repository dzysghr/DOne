package com.dzy.done.adapter.Holder;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    protected SparseArray<View> mViews;


    public BaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public abstract void setData(T t);


    public void setTextView(@IdRes int id, String text) {
        TextView view = findView(id);
        view.setText(text);
    }


    public void setCheckBox(@IdRes int id, boolean b) {
        CheckBox cb = findView(id);
        cb.setChecked(b);
    }

    public void setImageBitmap(@DrawableRes int id, @IdRes int imageid) {
        ImageView view = findView(imageid);
        if (view != null)
            view.setBackgroundResource(id);
    }

    public <Tv> Tv findView(@IdRes int id) {
        View view = mViews.get(id);
        if (view == null)
            view = itemView.findViewById(id);
        return (Tv) view;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener m) {
        this.mOnItemLongClickListener = m;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            this.mOnItemClickListener.OnItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null)
            return mOnItemLongClickListener.OnItemLongClick(v, getLayoutPosition());
        return false;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean OnItemLongClick(View view, int position);
    }
}
