package com.dzy.done.ui.fragment;

import android.content.Context;
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
import com.dzy.done.adapter.BaseAdapter;
import com.dzy.done.adapter.Holder.BaseHolder;
import com.dzy.done.adapter.Holder.MainListHolder;
import com.dzy.done.model.bean.ListItem;
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

    private int mType = 1;
    private boolean mFirshLoad = true;
    private boolean mViewCreated = false;
    private List<ListItem> mDatas = new ArrayList<>();
    private ListPresenter mPresenter;
    private BaseAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    /**
     * @param type 页面类型，图片、文章、东西  {@link com.dzy.done.model.bean.ListItem#ARTICLE}
     * @return 实例
     */
    public static ContentListFragment newInstance(int type)
    {
        return new ContentListFragment(type);
    }

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
        mViewCreated = true;
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        //如果这是第一个fragment，setUserVisibleHint比onCreateView会比先调用，mViewCreated为false，这里判断不会为true
        if (mFirshLoad && isVisibleToUser&&mViewCreated)
        {
            mPresenter.loadListDates();
            mFirshLoad = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) //如果这是第一个fragment,这里结果为true
        {
            mPresenter.loadListDates();
            mFirshLoad = false;
        }
    }

    private void initPresenter()
    {

        if (mType == 2)
            Log.e("tag", "initPresenter ");

        if (mPresenter == null)
        {
            if (mType == ListItem.Common)
                mPresenter = new FavoritePresenter();
            else
                mPresenter = new MainListPresenter(mType);

        }
        mPresenter.attachView(this);

    }


    private void initRecycleView()
    {
        mAdapter = new BaseAdapter<ListItem>(getActivity(), mDatas, R.layout.list_item)
        {
            @Override
            public BaseHolder<ListItem> createHolder(View v, Context context)
            {
                return new MainListHolder(v);
            }
        };

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
    public void onResume()
    {
        super.onResume();
        mPresenter.onResume();
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
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress()
    {
        mSwipeRefreshLayout.setRefreshing(false);

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
