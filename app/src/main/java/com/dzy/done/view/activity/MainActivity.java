package com.dzy.done.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.config.PageConfig;
import com.dzy.done.view.adapter.MainPageAdapter;
import com.dzy.done.view.fregment.ContentListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewpager_activity_main)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabs;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    MainPageAdapter mAdapter;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setupView();

    }

    public void setupView() {
        setSupportActionBar(mToolbar);

        mAdapter = new MainPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(PageConfig.titles.length);

        mTabs.setupWithViewPager(mViewPager);
        mTabs.setTabsFromPagerAdapter(mAdapter);

        
        mToast = Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             ContentListFragment fregment = (ContentListFragment) mAdapter.getItem(mTabs.getSelectedTabPosition());
                fregment.scrollToTop();
            }
        });
    }

    @Override
    public void onBackPressed() {
         if (mToast.getView().getParent() == null)
            mToast.show();
        else
            super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}