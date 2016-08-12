package com.dzy.done.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.dzy.done.model.bean.ListItem;
import com.dzy.done.config.PageConfig;
import com.dzy.done.ui.fragment.ContentListFragment;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/** 主界面PageView 的adapter
 * Created by dzysg on 2015/10/9 0009.
 */
public class MainPageAdapter extends FragmentPagerAdapter {

    static SoftReference<List<Fragment>> mSoftFragments;
    List<Fragment> mFragments;

    public MainPageAdapter(FragmentManager fm) {
        super(fm);

        if (mSoftFragments==null)
        {
            createFragment();
        }
        else
        {
            mFragments = mSoftFragments.get();
            if (mFragments==null)
                createFragment();
        }
    }


    private void createFragment()
    {
        mFragments = new ArrayList<>(4);
        mFragments.add(ContentListFragment.newInstance(ListItem.ARTICLE));
        mFragments.add(ContentListFragment.newInstance(ListItem.PICTURE));
        mFragments.add(ContentListFragment.newInstance(ListItem.THING));
        mFragments.add(ContentListFragment.newInstance(ListItem.QA));

        //把四个fragment缓存起来，主题切换时可以不用再重建
        mSoftFragments = new SoftReference<>(mFragments);
        Log.e("tag","createFragment");
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
