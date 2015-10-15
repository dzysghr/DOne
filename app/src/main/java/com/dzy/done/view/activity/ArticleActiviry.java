package com.dzy.done.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.model.ContentCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActiviry extends AppCompatActivity implements ContentCache.IGetArticleCallback{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_content)
    TextView mContent;

    @Bind(R.id.tv_date)
    TextView mDate;
    @Bind(R.id.tv_title)
    TextView mTitle;

    String mUrl = "default url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");

        mTitle.setText(intent.getStringExtra("title"));
        mDate.setText(intent.getStringExtra("date"));


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ContentCache.get().getArticle(mUrl, this);


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
        mContent.setText(content);
    }


    public void Falure(String msg)
    {
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }
}
