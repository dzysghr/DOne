package com.dzy.done.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.ui.fragment.ContentListFragment;

import butterknife.Bind;

/**
 *  没写完，ContentListFragment结构要改
 */
public class FavoriteActivity extends AppCompatActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("收藏");
        setSupportActionBar(mToolbar);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,ContentListFragment.newInstance(ListItem.Common))
                .commit();
    }
}
