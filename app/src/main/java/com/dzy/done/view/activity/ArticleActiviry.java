package com.dzy.done.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.model.ContentModel;
import com.dzy.done.util.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActiviry extends AppCompatActivity implements ContentModel.IGetArticleCallback{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
   // @Bind(R.id.tv_content)
    //TextView mContent;

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




        String title = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
        mTitle.setText(title);
        mDate.setText(intent.getStringExtra("date"));




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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }


    public void Finish(String content)
    {
        //mContent.setText(content);
        MLog.getLogger().d(content);
        mWebView.getSettings().setDefaultTextEncodingName("GBK");
        mWebView.loadData(content, "text/html;charset=UTF-8",null);

    }


    public void Falure(String msg)
    {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}
