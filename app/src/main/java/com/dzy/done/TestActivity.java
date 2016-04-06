package com.dzy.done;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 *  测试用
 */
public class TestActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.i("tag", "onCreate");
    }




    public void onclick(View v)
    {


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
}

