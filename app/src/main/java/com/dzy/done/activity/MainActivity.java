package com.dzy.done.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.adapter.MainPageAdapter;
import com.dzy.done.config.PageConfig;
import com.dzy.done.fregment.ContentListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Bind(R.id.dl_left)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nv_main_navigation)
    NavigationView mMenuView;

    ActionBarDrawerToggle mDrawerToggle;


    MainPageAdapter mAdapter;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        setupView();
    }

    public void setupView() {
        setSupportActionBar(mToolbar);

        mAdapter = new MainPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(PageConfig.titles.length);
        mTabs.setupWithViewPager(mViewPager);
        mToast = Toast.makeText(this,getResources().getString(R.string.ExitTips), Toast.LENGTH_SHORT);

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mMenuView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                if (item.getItemId()==R.id.menu_setting)
                {
                    startActivity(new Intent(MainActivity.this,SettingActivity.class));
                }
                return false;
            }
        });

        assert  getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @OnClick(R.id.fab)
    public void onFabClick(View v)
    {
        ContentListFragment fregment = (ContentListFragment) mAdapter.getItem(mTabs.getSelectedTabPosition());
        fregment.scrollToTop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
         if (mToast.getView().getParent() == null)
            mToast.show();
        else
            super.onBackPressed();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}