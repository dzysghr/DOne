package com.dzy.done.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.dzy.done.bean.ThingItem;
import com.dzy.done.model.ContentModel;
import com.dzy.done.util.MLog;
import com.dzy.done.util.colorUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class ThingActivity extends AppCompatActivity implements ContentModel.IGetThingCallback
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    @BindString(R.string.FailLoad)
    String mTips;

    @Bind(R.id.pb)
    ProgressBar mPb;

    ListItem mItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing);
        ButterKnife.bind(this);

        initView();
        ContentModel.get().getThing(mItem.getUrl(),this);
    }

    private void initView()
    {
        //设置progressbar颜色
        mPb.bringToFront();
        mPb .getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPb), PorterDuff.Mode.SRC_IN);


        Intent intent = getIntent();
        mItem = (ListItem) intent.getSerializableExtra("item");
        mToolbar.setTitle(mItem.getTitle());
        ViewCompat.setTransitionName(mIv,mItem.getUrl());

        //设置主题色
        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        if (bitmap!=null) {
            mIv.setImageBitmap(bitmap);

            mIv.setImageBitmap(bitmap);
            Palette.Swatch swatch = colorUtil.getSwatch(bitmap);
            if (swatch!=null)
            {
                mToolbar.setTitleTextColor(swatch.getTitleTextColor());
                mToolbar.setBackgroundColor(swatch.getRgb());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();

                    window.setStatusBarColor(colorUtil.colorBurn(swatch.getRgb()));
                    //window.setNavigationBarColor(colorUtil.colorBurn(swatch.getRgb()));
                }
            }
        }
        setSupportActionBar(mToolbar);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPb.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        //取消请求
        Picasso.with(this).cancelRequest(mIv);
        ContentModel.get().cancel();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.webview_activity, menu);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            supportFinishAfterTransition();
        return true;
    }

    @Override
    public void Finish(ThingItem item)
    {
        if (item==null)
            MLog.getLogger().d("thing item is null");
        else {

            if (TextUtils.isEmpty(item.getName()))
                mTvTitle.setVisibility(View.GONE);
            else
                mTvTitle.setText(item.getName());
            mTvContent.setText(item.getContent());

            MLog.getLogger().d("load Thing image " + item.getImg());
            Picasso.with(this).load(item.getImg()).fit().noPlaceholder().into(mIv, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    mPb.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    mPb.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void Falure(String msg)
    {
        mPb.setVisibility(View.GONE);
        Toast.makeText(this,mTips, Toast.LENGTH_SHORT).show();
    }

}
