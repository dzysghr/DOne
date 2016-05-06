package com.dzy.done.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.adapter.Holder.BottomSheetAdapter;
import com.dzy.done.asynctask.SavePhotoTask;
import com.dzy.done.bean.BottomSheetItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.config.AppSetting;
import com.dzy.done.presenter.PicturePresenter;
import com.dzy.done.util.MLog;
import com.dzy.done.util.colorUtil;
import com.dzy.done.view.PictureView;
import com.dzy.done.widget.BottomSheet;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureActivity extends AppCompatActivity implements PictureView, View.OnLongClickListener, BottomSheetAdapter.ItemClickListener
{


    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.iv) ImageView mIv;
    @Bind(R.id.num) TextView mTvNum;
    @Bind(R.id.author) TextView mTvAuthor;
    @Bind(R.id.tv_content) TextView mTvContent;
    @Bind(R.id.tv_day) TextView mTvDay;
    @Bind(R.id.tv_mm_yy) TextView mTvMonth;
    @Bind(R.id.pb) ProgressBar mPb;


    //已经完成
    boolean mIsFinish = false;
    //已经收藏
    boolean haveSave = true;

    BottomSheetItem mSaveItem;
    ListItem mItem;
    PicturePresenter mPresenter;
    List<BottomSheetItem> mBSItems;
    BottomSheet mBottomSheet;

    //大图
    Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mItem = (ListItem) intent.getSerializableExtra("item");
        ViewCompat.setTransitionName(mIv, mItem.getUrl());
        initView();
        initData();
        BottomSheetDialog dialog = new BottomSheetDialog(this);

    }

    public void initView()
    {

        //解析图片主题色
        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        if (bitmap != null)
        {
            mIv.setImageBitmap(bitmap);

            if (!AppSetting.getSetting().isNightMode())
            {
                Palette.Swatch swatch = colorUtil.getSwatch(bitmap);
                if (swatch != null)
                {
                    mToolbar.setTitleTextColor(swatch.getTitleTextColor());
                    mToolbar.setBackgroundColor(swatch.getRgb());
                    if (android.os.Build.VERSION.SDK_INT >= 21)
                    {
                        Window window = getWindow();
                        window.setStatusBarColor(colorUtil.colorBurn(swatch.getRgb()));
                        window.setNavigationBarColor(colorUtil.colorBurn(swatch.getRgb()));
                    }
                }
            }

        }

        //设置actionbar
        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //设置progressbar颜色
        mPb.bringToFront();
        mPb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPb), PorterDuff.Mode.SRC_IN);
        mPb.setVisibility(View.VISIBLE);

        mIv.setOnLongClickListener(this);

    }


    public void initData()
    {

        //初始化BottomSheet选项
        mBSItems = new ArrayList<>();
        mBSItems.add(new BottomSheetItem(R.drawable.photo, "保存到相册"));
        mSaveItem = new BottomSheetItem(R.drawable.star, "收藏");
        mBSItems.add(mSaveItem);
        mBSItems.add(new BottomSheetItem(R.drawable.share, "分享"));


        //设置作者等信息，这两项数据可以在请求成功回调finish方法获取和设置，这里提前设置让体验好一丢丢
        mTvNum.setText(mItem.getTitle().split(" ", 2)[0]);
        mTvAuthor.setText(mItem.getTitle().split(" ", 2)[1]);

        mPresenter = new PicturePresenter();
        mPresenter.onAttach(this);
        mPresenter.loadPicture(mItem.getUrl());
        mPresenter.checkFromFavorite(mItem);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Picasso.with(this).cancelTag(mItem.getImg());
    }

    @OnClick(R.id.iv)
    public void ImageOnClick()
    {
        if (!mIsFinish)
            return;

        if (mIv.getDrawable() != null)
        {
            Intent intent = new Intent(this, PhotoViewerActivity.class);
            intent.putExtra("url", mItem.getImg());
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            supportFinishAfterTransition();
        }
        return true;
    }

    @Override
    public void showPictureInfo(PictureItem item)
    {
        //数据加载完成
        MLog.getLogger().d("load image " + item.getImg());

        Picasso.with(this).load(item.getImg()).tag(mItem.getImg()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
            {

                mIv.setImageBitmap(bitmap);
                mIsFinish = true;
                mBitmap = bitmap;
                mPb.setVisibility(View.GONE);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable)
            {
                mIsFinish = true;
                mPb.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable)
            {

            }
        });



        mTvContent.setText(item.getContent());
        mTvDay.setText(item.getDay());
        mTvMonth.setText(item.getYear());
        mTvAuthor.setText(item.getAuthor());
        mTvNum.setText(item.getNum());
    }

    @Override
    public void showMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setHaveSave(boolean b)
    {
        haveSave = b;
        if (mSaveItem!=null)
        {
            if (haveSave)
                mSaveItem.setTitle("取消收藏");
            else
                mSaveItem.setTitle("收藏");
        }
    }

    //长按弹出菜单
    @Override
    public boolean onLongClick(View v)
    {
        if (!mIsFinish)
            return false;
        mBottomSheet = new BottomSheet(this, R.style.AppBottomSheetDialogTheme, mBSItems, this);
        mBottomSheet.show();
        return true;
    }

    @Override
    public void onItemClick(int position)
    {

        //收藏或者取消收藏
        if (position == 1)
        {

            if (haveSave)
            {
                mPresenter.deleteFromFavorite(mItem);
                haveSave = false;
                mSaveItem.setTitle("收藏");

            } else
            {
                mPresenter.saveToFavorite(mItem);
                haveSave = true;
                mSaveItem.setTitle("取消收藏");
            }
        }
        // 保存到相册
        else if (position == 0)
        {
            SavePhotoTask task = new SavePhotoTask();
            task.execute(mBitmap,mItem.getTitle());
        }
        mBottomSheet.dismiss();
    }


}
