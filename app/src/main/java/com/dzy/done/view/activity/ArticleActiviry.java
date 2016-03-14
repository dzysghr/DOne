package com.dzy.done.view.activity;

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
import com.dzy.done.util.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActiviry extends AppCompatActivity implements ContentModel.IGetArticleCallback{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.tv_date)
    TextView mDate;
    @Bind(R.id.tv_title)
    TextView mTitle;

    @Bind(R.id.webview)
    WebView mWebView;
    String mUrl = "default url";

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

        ContentModel.get().getArticle(mUrl, this);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article, menu);
        return true;
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
    }

    public void Finish(String content)
    {

        //mContent.setText(content);
        //WebSettings.LayoutAlgorithm layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;
        //mWebView.getSettings().setLayoutAlgorithm(layoutAlgorithm);
        MLog.getLogger().d(content);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setTextZoom(AppSetting.getSetting().getFontSize());
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.loadData(content, "text/html;charset=UTF-8", null);

    }


    public void Falure(String msg)
    {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}
