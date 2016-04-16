package com.dzy.done;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.dzy.done.util.MLog;

/**
 *  测试用
 */
public class TestActivity extends AppCompatActivity
{

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.i("tag", "onCreate");

    }




    public void onclick(View v)
    {


        if (mMenu==null)
            MLog.getLogger().d("xxxxxxxxxxxxxxxxxxx");
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i("tag", "onStart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i("tag","onDestroy");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i("tag", "onStop");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        Log.i("tag", "onConfigurationChanged");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        Log.i("tag", "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.webview_activity, menu);
        mMenu = menu;
        if (mMenu==null)
            MLog.getLogger().d("ddddddddddd");
        return true;
    }
}

