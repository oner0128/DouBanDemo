package com.android.oner0128.doubandemo.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.OnLoadMoreListener;
import com.android.oner0128.doubandemo.adapter.ZhihuAdapter;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.presenter.ZhihuPresenterImpl;
import com.android.oner0128.doubandemo.view.item.ZhihuBannerItem;
import com.android.oner0128.doubandemo.view.item.ZhihuHeaderTitleItem;
import com.android.oner0128.doubandemo.view.item.ZhihuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/6/5.
 */

public class ZhihuFragment extends Fragment implements ZhihuView {
    @BindView(R.id.fragment)
    CoordinatorLayout fragment;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    ZhihuPresenterImpl mPresenter;
    private ArrayList<ZhihuItem> mDatas;
    private ZhihuAdapter mAdapter;
    private String mdate;
    private static ZhihuFragment INSTANCE;

    public static ZhihuFragment getInstance() {
        if (INSTANCE == null) {
            synchronized (ZhihuFragment.class) {
                if (INSTANCE == null) INSTANCE = new ZhihuFragment();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenterAndAdapter();
        initView();
        initListener();
    }

    private void initPresenterAndAdapter() {
        mPresenter = new ZhihuPresenterImpl(this);
        mDatas = new ArrayList<>();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ZhihuAdapter(getContext(), mDatas);
        recyclerView.setAdapter(mAdapter);
        getLatestNews();

    }

    private void getLatestNews() {
        mPresenter.getNewsLatest();
    }

    @Override
    public void showProgressDialog() {
        mSwipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void hideProgressDialog() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        if (recyclerView != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            Snackbar.make(recyclerView,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLatestNews();
                }
            }).show();
        }
    }

    void initListener() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.color_fab_scrolling);
        //swipe refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh data
                getLatestNews();

            }
        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                View lastchildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
//                int lastChildBottomY = lastchildView.getBottom();
//                int recyclerBottomY = recyclerView.getBottom() - recyclerView.getPaddingBottom();
//                int lastPosition = recyclerView.getLayoutManager().getPosition(lastchildView);
//
//                if (lastChildBottomY == recyclerBottomY && newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
//                    mPresenter.getNewsBefore(mdate);
//                }
//            }
//        });
    }

    @Override
    public void updateNewsLatestItem(ZhihuLatestNewsBean zhihuLatestNewsBean) {
        mDatas.clear();
        mdate = zhihuLatestNewsBean.getDate();
        //banner top stories
        mDatas.add(new ZhihuBannerItem(zhihuLatestNewsBean.getTop_stories()));
        //date title
        mDatas.add(new ZhihuHeaderTitleItem(mdate));
        //stories
        mDatas.addAll(zhihuLatestNewsBean.getStories());
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateNewsBeforeItem() {

    }
}
