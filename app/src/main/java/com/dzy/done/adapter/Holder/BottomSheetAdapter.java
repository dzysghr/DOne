package com.dzy.done.adapter.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.BottomSheetItem;

import java.util.List;

/**
 *
 * Created by dzysg on 2016/5/5 0005.
 */
public class BottomSheetAdapter extends RecyclerView.Adapter<BaseHolder<BottomSheetItem>>
{


    List<BottomSheetItem> mDatas;
    ItemClickListener mListener;

    public BottomSheetAdapter(List<BottomSheetItem> list,ItemClickListener listener)
    {
        mDatas = list;
        mListener = listener;

    }

    @Override
    public BaseHolder<BottomSheetItem> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_item,parent,false);
        BottomSheetHolder holder = new BottomSheetHolder(view,parent.getContext(),mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder<BottomSheetItem> holder, int position)
    {
        BottomSheetItem item = mDatas.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }


    public interface  ItemClickListener{

         void onItemClick(int position);

    }

   public static class  BottomSheetHolder extends BaseHolder<BottomSheetItem> implements View.OnClickListener
   {

        ImageView mImageView;
        TextView mTextView;
        ItemClickListener mListener;


       public BottomSheetHolder(View itemView, Context c,ItemClickListener listener)
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
           if (item.getDrawableResource()!=0)
           {
               mImageView.setImageResource(item.getDrawableResource());
           }
       }
       @Override
       public void onClick(View v)
       {
            if (mListener!=null)
            {
                mListener.onItemClick(getAdapterPosition());
            }
       }
   }
}
