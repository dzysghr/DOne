package com.dzy.done.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dzy.done.R;
import com.dzy.done.fregment.ContentListFragment;

/**
 *  没写完，ContentListFragment结构要改
 */
public class FavoriteActivity extends AppCompatActivity
{


    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ContentListFragment.newInstance(0))
                .commit();

    }
}
