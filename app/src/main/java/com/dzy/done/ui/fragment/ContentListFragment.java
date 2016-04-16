package com.dzy.done.ui.fragment;

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
import com.dzy.done.adapter.MainListAdapter;
import com.dzy.done.bean.ListItem;
import com.dzy.done.presenter.FavoritePresenter;
import com.dzy.done.presenter.ListPresenter;
import com.dzy.done.presenter.MainListPresenter;
import com.dzy.done.view.ContentListView;
import com.dzy.done.widget.RecyclerViewItemDecoration;
import com.dzy.done.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页Viewpager的三个Fragment
 * Created by dzysg on 2015/10/9 0009.
 */
public class ContentListFragment extends Fragment implements ContentListView, SwipeRefreshLayout.OnRefreshListener
{

    @Bind(R.id.recyclerview) RecyclerView mRecyclerView;

    @Bind(R.id.swrfresh) ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;


    int mType = 1;
    List<ListItem> mDatas = new ArrayList<>();
    ListPresenter mPresenter;
    private MainListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fregment_article, container, false);
        ButterKnife.bind(this, view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setScrollUpChild(mRecyclerView);
        initRecycleView();
        initPresenter();
        return view;
    }


    /**
     *
     */
    private void initPresenter()
    {
        if (mType == ListItem.Common)
            mPresenter = new FavoritePresenter();
        else
            mPresenter = new MainListPresenter(mType);

        mPresenter.attachView(this);
        //加载第一页
        mPresenter.loadListDates();


        Log.i("tag", "mPresenter loaddatas");
    }


    private void initRecycleView()
    {
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    //如果recycleview滑到底,加载数据
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mDatas.size() - 1 && mLayoutManager.findFirstCompletelyVisibleItemPosition() != 0)
                    {
                        if (mRecyclerView.getChildCount() > 0)
                        {
                            mPresenter.loadMore();
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
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mPresenter.detach();

    }

    private ContentListFragment(int type)
    {
        mType = type;
    }

    /**
     * @param type 页面类型，图片、文章、东西  {@link com.dzy.done.bean.ListItem#ARTICLE}
     * @return 实例
     */
    public static ContentListFragment newInstance(int type)
    {
        return new ContentListFragment(type);
    }

    @Override
    public void showDatas(List<ListItem> datas)
    {
        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress()
    {
        Log.i("tag", "show progress");
       // mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress()
    {
        mSwipeRefreshLayout.setRefreshing(false);
        //mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void showMsg(String msg)
    {
        Toast.makeText(getContext(), R.string.load_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh()
    {
        Log.i("tag", "on Refresh");
        mPresenter.loadListDates();

    }

    public void scrollToTop()
    {
        mRecyclerView.scrollToPosition(0);
    }
}
