package com.dzy.done.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.presenter.PicturePresenter;
import com.dzy.done.util.MLog;
import com.dzy.done.util.colorUtil;
import com.dzy.done.view.PictureView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureActivity extends AppCompatActivity implements PictureView
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
    boolean haveSave = true;
    ListItem mItem;
    PicturePresenter mPresenter;



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

    }

    public void initView()
    {
        //解析图片主题色
        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        if (bitmap != null)
        {
            mIv.setImageBitmap(bitmap);
            Palette.Swatch swatch = colorUtil.getSwatch(bitmap);
            if (swatch != null)
            {
                mToolbar.setTitleTextColor(swatch.getTitleTextColor());
                mToolbar.setBackgroundColor(swatch.getRgb());
                if (android.os.Build.VERSION.SDK_INT >= 21)
                {
                    Window window = getWindow();
                    window.setStatusBarColor(colorUtil.colorBurn(swatch.getRgb()));
                    //window.setNavigationBarColor(colorUtil.colorBurn(swatch.getRgb()));
                }
            }
        }

        //设置actionbar
        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //设置progressbar颜色
        mPb.bringToFront();
        mPb .getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPb), PorterDuff.Mode.SRC_IN);
        mPb.setVisibility(View.VISIBLE);

        //加载大图和文字
        //ContentModel.get().getPicture(mItem.getUrl(), this);
    }


    public void initData()
    {
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
        Picasso.with(this).cancelRequest(mIv);

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
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.webview_activity, menu);
        if (haveSave)
            menu.findItem(R.id.action_favorite).setTitle(R.string.havesave);
        else
            menu.findItem(R.id.action_favorite).setTitle(R.string.save);
        return mIsFinish;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==android.R.id.home)
        {
            supportFinishAfterTransition();
        }
        else if (item.getItemId()==R.id.action_favorite)
        {
            if (haveSave)
                mPresenter.deleteFromFavorite(mItem);
            else
                mPresenter.saveToFavorite(mItem);
        }
        return true;
    }

    @Override
    public void showPictureInfo(PictureItem item)
    {
        MLog.getLogger().d("load image " + item.getImg());
        Picasso.with(this).load(item.getImg()).noPlaceholder().into(mIv, new Callback()
        {
            @Override
            public void onSuccess()
            {
                mIsFinish = true;
                invalidateOptionsMenu();
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
        mTvAuthor.setText(item.getAuthor());
        mTvNum.setText(item.getNum());
        mImgurl = item.getImg();
    }

    @Override
    public void showMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFavoriteMenuState(boolean b)
    {
        haveSave = b;
        invalidateOptionsMenu();
    }
}
