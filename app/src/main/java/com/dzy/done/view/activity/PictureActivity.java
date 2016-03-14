package com.dzy.done.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.model.ContentModel;
import com.dzy.done.util.MLog;
import com.dzy.done.util.colorUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureActivity extends AppCompatActivity implements ContentModel.IGetPictureCallback
{


    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.iv) ImageView mIv;
    @Bind(R.id.num) TextView mTvNum;
    @Bind(R.id.author) TextView mTvAuthor;
    @Bind(R.id.tv_content) TextView mTvContent;
    @Bind(R.id.tv_day) TextView mTvDay;
    @Bind(R.id.tv_mm_yy) TextView mTvMonth;
    @Bind(R.id.pb) ProgressBar mPb;

    String mImgurl;
    boolean mIsFinish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d("tag",url);
        ViewCompat.setTransitionName(mIv, url);

        //解析图片主题色
        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        if (bitmap != null)
        {
            mIv.setImageBitmap(bitmap);
            Palette p = Palette.from(bitmap).generate();
            Palette.Swatch swatch = p.getVibrantSwatch();
            if (swatch == null)
            {
                swatch = p.getMutedSwatch();
            }
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

        //设置作者等信息
        mTvNum.setText(intent.getStringExtra("num"));
        mTvAuthor.setText(intent.getStringExtra("author"));
        

        //设置actionbar
        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //设置progressbar颜色
        mPb.bringToFront();
        mPb .getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPb), PorterDuff.Mode.SRC_IN);
        mPb.setVisibility(View.VISIBLE);


        //加载大图和图片信息
        ContentModel.get().getPicture(url, this);
    }





    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Picasso.with(this).cancelRequest(mIv);
        ContentModel.get().cancel();
    }

    @OnClick(R.id.iv)
    public void ImageOnClick()
    {
        if (!mIsFinish)
            return;

        if (mIv.getDrawable() != null)
        {
            Intent intent = new Intent(this, PhotoViewerActivity.class);
            intent.putExtra("url", mImgurl);
            startActivity(intent);
        }
    }




    @Override
    public void Finish(PictureItem item)
    {

        MLog.getLogger().d("load image " + item.getImg());
        Picasso.with(this).load(item.getImg()).noPlaceholder().into(mIv, new Callback()
        {
            @Override
            public void onSuccess()
            {
                mIsFinish = true;
                mPb.setVisibility(View.GONE);
            }

            @Override
            public void onError()
            {
                mPb.setVisibility(View.GONE);
            }
        });

        mTvContent.setText(item.getContent());
        mTvDay.setText(item.getDay());
        mTvMonth.setText(item.getYear());
        //mTvAuthor.setText(item.getAuthor());
        //mTvNum.setText(item.getNum());
        mImgurl = item.getImg();
    }

    @Override
    public void Falure(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
