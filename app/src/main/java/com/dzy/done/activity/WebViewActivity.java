package com.dzy.done.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.config.AppSetting;
import com.dzy.done.model.ContentModel;
import com.dzy.done.view.StringContentView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 *  问答和文章共用这个activity基类
 */
public  class WebViewActivity extends AppCompatActivity implements StringContentView
{

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.tv_date)
    protected TextView mDate;
    @Bind(R.id.tv_title)
    protected TextView mTitle;

    @Bind(R.id.webview)
    protected WebView mWebView;

    protected String mUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        //设置标题、日期
        String title = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
        mTitle.setText(title);
        mDate.setText(intent.getStringExtra("date"));

        //设置toolbar
        setSupportActionBar(mToolbar);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.webview_activity, menu);

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mWebView.destroy();
        ContentModel.get().cancel();
    }


    @Override
    public void showContent(String content)
    {
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setTextZoom(AppSetting.getSetting().getFontSize());
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.loadData(content, "text/html;charset=UTF-8", null);
    }

    @Override
    public void failure(String error)
    {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loading()
    {

    }
}
