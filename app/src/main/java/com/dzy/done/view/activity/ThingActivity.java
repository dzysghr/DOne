package com.dzy.done.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.model.ContentModel;
import com.dzy.done.util.MLog;
import com.dzy.done.util.colorUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
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

    @Bind(R.id.pb)
    ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing);
        ButterKnife.bind(this);

        //设置progressbar颜色
        mPb.bringToFront();
        mPb .getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPb), PorterDuff.Mode.SRC_IN);


        WindowManager wm = this.getWindowManager();
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        mIv.getLayoutParams().height = point.y/2;

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        mToolbar.setTitle(title);

        ViewCompat.setTransitionName(mIv,url);
        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        if (bitmap!=null) {
            mIv.setImageBitmap(bitmap);

            Palette p = Palette.from(bitmap).generate();
            Palette.Swatch swatch = p.getVibrantSwatch();
            if (swatch==null)
            {
                swatch = p.getMutedSwatch();
            }
            if (swatch!=null)
            {
                mToolbar.setTitleTextColor(swatch.getTitleTextColor());
                mToolbar.setBackgroundColor(swatch.getRgb());

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();

                    window.setStatusBarColor(colorUtil.colorBurn(swatch.getRgb()));
                    window.setNavigationBarColor(colorUtil.colorBurn(swatch.getRgb()));
                }
            }
        }


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ContentModel.get().getThing(url,this);
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        Picasso.with(this).cancelRequest(mIv);
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
        mPb.setVisibility(View.VISIBLE);
        if (item==null)
            MLog.getLogger().d("thing item is null");
        else {
            MLog.getLogger().d("load Thing image " + item.getSrc());
            Picasso.with(this).load(item.getSrc()).fit().noPlaceholder().into(mIv, new Callback() {
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
            if (TextUtils.isEmpty(item.getName()))
                mTvTitle.setVisibility(View.GONE);
            else
                mTvTitle.setText(item.getName());
            mTvContent.setText(item.getContent());
        }
    }

    @Override
    public void Falure(String msg)
    {
        mPb.setVisibility(View.GONE);
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }
}
