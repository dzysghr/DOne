package com.dzy.done.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dzy.done.config.PageConfig;
import com.dzy.done.view.fregment.ContentListFragment;

import java.util.ArrayList;
import java.util.List;

/** 主界面PageView 的adapter
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainPageAdapter extends FragmentPagerAdapter {


    List<Fragment> mFragments;


    public MainPageAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();

        mFragments.add(ContentListFragment.newInstance(1));
        mFragments.add(ContentListFragment.newInstance(2));
        mFragments.add(ContentListFragment.newInstance(3));

    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return PageConfig.titles[position];
    }
}
