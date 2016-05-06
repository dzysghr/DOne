package com.dzy.done.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.ArticleItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.config.AppSetting;
import com.dzy.done.presenter.ArticlePresenter;
import com.dzy.done.presenter.ArticlePresenterImpl;
import com.dzy.done.util.MLog;
import com.dzy.done.view.ArticleContentView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 问答和文章页面共用这个activity
 */
public class WebViewActivity extends AppCompatActivity implements ArticleContentView
{

    @Bind(R.id.toolbar) protected Toolbar mToolbar;

    @Bind(R.id.tv_date) protected TextView mDate;
    @Bind(R.id.tv_title) protected TextView mTitle;
    @Bind(R.id.webview) protected WebView mWebView;

    boolean haveSaved = false;
    boolean isFinish = false;

    protected ListItem mItem;
    ArticlePresenter mPresenter;
    ArticleItem mArticleItem;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mItem = (ListItem) intent.getSerializableExtra("item");

        initPresenter();
        initData();

        //设置toolbar
        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPresenter()
    {
        mPresenter = new ArticlePresenterImpl();
        mPresenter.onAttach(this);
    }

    public void initData()
    {
        //设置标题、日期
        mTitle.setText(mItem.getTitle());
        mDate.setText(mItem.getDate());

        //根据不同的情况加载不同的数据
        if (mItem.getType() == ListItem.ARTICLE)
            mPresenter.LoadArticleContent(mItem.getUrl());
        else
            mPresenter.LoadQAContent(mItem.getUrl());

        mPresenter.ExistfromFavorite(mItem);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MLog.getLogger().d("onPrepare");

        getMenuInflater().inflate(R.menu.webview_activity, menu);

        if(haveSaved)
            menu.findItem(R.id.action_favorite).setTitle("已收藏");
        else
            menu.findItem(R.id.action_favorite).setTitle("收藏");

        //内容加载完才显示菜单
        return isFinish;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }else if (item.getItemId() == R.id.action_favorite)
        {
            if (!haveSaved)
                mPresenter.saveToFavorite(mItem);
            else
                mPresenter.deleteFromFavorite(mItem);
        }
        return false;
    }

    @Override
    protected void onDestroy()
    {
        mWebView.destroy();
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showContent(ArticleItem item)
    {
        isFinish = true;
        invalidateOptionsMenu();
        mArticleItem = item;

        //html 中的字体颜色要通过html的方式改
        String TextColorString = "#"+Integer.toHexString(getResources().getColor(R.color.webViewTextColor)).substring(2);
        Log.d("tag","text color  "+TextColorString);


        String content = new StringBuilder(item.getContent())
                .insert(0,"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>\n" +
                "<font color=\""+TextColorString+"\"><body>").append("</font><div></div><div></div></html>").toString();

        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setTextZoom(AppSetting.getSetting().getFontSize());
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.loadData(content, "text/html;charset=UTF-8", null);
    }

    @Override
    public void showToast(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loading()
    {

    }

    @Override
    public void setFavoriteMenuState(boolean b)
    {
        //b 为true表示收藏已经存在，不可收藏
        haveSaved = b;
        invalidateOptionsMenu();
    }
}
