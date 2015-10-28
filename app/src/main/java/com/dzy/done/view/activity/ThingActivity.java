package com.dzy.done.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.model.ContentCache;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThingActivity extends AppCompatActivity implements ContentCache.IGetThingCallback
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_content)
    TextView mTvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing);
        ButterKnife.bind(this);

        WindowManager wm = this.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        mIv.getLayoutParams().height = height/2;


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        mToolbar.setTitle(title);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ContentCache.get().getThing(url,this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    public void Finish(ThingItem item)
    {
        if (item==null)
            Log.i("tag", "item is null");
        else {
            Picasso.with(this).load(item.getSrc()).fit().into(mIv);
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
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }
}
