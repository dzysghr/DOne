package com.dzy.done.view.fregment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.ListItem;
import com.dzy.done.presenter.IViewPager;
import com.dzy.done.presenter.ListPresenterimpl;
import com.dzy.done.view.adapter.MainListAdapter;
import com.dzy.done.widget.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页Viewpager的三个Fragment
 * Created by dzysg on 2015/10/9 0009.
 */
public class ContentListFragment extends Fragment implements IViewPager, SwipeRefreshLayout.OnRefreshListener
{

    @Bind(R.id.recyclerview) RecyclerView mRecyclerView;

    @Bind(R.id.swrfresh) SwipeRefreshLayout mSwipeRefreshLayout;


    int mType = 1;
    List<ListItem> mDatas = new ArrayList<>();
    ListPresenterimpl mPresenter;
    private MainListAdapter mAdapter;
    private int mPageCount = 1;
    private LinearLayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fregment_article, container, false);
        ButterKnife.bind(this, view);


        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new MainListAdapter(getActivity(), mDatas);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVerticalScrollBarEnabled(true);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 || mRecyclerView.getChildCount() == 0)
                        mSwipeRefreshLayout.setEnabled(true);
                    else
                        mSwipeRefreshLayout.setEnabled(false);


                    //如果recycleview滑到底,加载很多数据
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mDatas.size() - 1) {
                        if (mRecyclerView.getChildCount() > 0) {
                            mPresenter.LoadListDates(++mPageCount);
                            Log.i("tag", "load more");
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mPresenter = new ListPresenterimpl(this, mType);
        //加载第一页
        mPresenter.LoadListDates(1);
        Log.i("tag", "mPresenter loaddatas");
        return view;
    }

    public ContentListFragment(int type)
    {
        mType = type;
    }

    public static ContentListFragment newInstance(int type)
    {
        return new ContentListFragment(type);
    }

    @Override
    public void showDatas(List<ListItem> datas)
    {
        if (mPageCount == 1)
            mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress()
    {
        Log.i("tag", "show progress");
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress()
    {
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void failload()
    {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRefresh()
    {
        Log.i("tag", "on Refresh");
        mPresenter.LoadListDates(1);
        mPageCount = 1;
    }

    public void scrollToTop()
    {
        mRecyclerView.scrollToPosition(0);
    }
}
