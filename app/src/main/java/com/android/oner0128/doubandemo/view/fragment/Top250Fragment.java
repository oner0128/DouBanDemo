package com.android.oner0128.doubandemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250Adapter;
import com.android.oner0128.doubandemo.adapter.OnLoadMoreListener;
import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.presenter.Top250PresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Top250Fragment extends Fragment implements Top250View {
    @BindView(R.id.fragment_top250_linear)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_top250)
    RecyclerView recycler_top250_linear;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    Top250PresenterImpl mTop250PresentImpl;
    private ArrayList<MoviesBean.Subjects> movies;
    private Top250Adapter mTop250Adapter;

    public int start, count, total;
    private static Top250Fragment INSTANCE;


    public static Top250Fragment getInstance() {
        if (INSTANCE == null) {
            synchronized (Top250Fragment.class) {
                if (INSTANCE == null) INSTANCE = new Top250Fragment();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top250, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenterAndAdapter();
        initView();
    }

    private void initPresenterAndAdapter() {
        mTop250PresentImpl = new Top250PresenterImpl(this);
        movies = new ArrayList<>();
    }

    private void initView() {
        recycler_top250_linear.setLayoutManager(new LinearLayoutManager(getContext()));
        mTop250Adapter = new Top250Adapter(recycler_top250_linear, getContext(), movies);
        recycler_top250_linear.setAdapter(mTop250Adapter);
        loadMovies();
        //swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.color_fab_scrolling);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh data
                mTop250Adapter.clearData();
                loadMovies();
            }
        });
        //loading more
        mTop250Adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (start < total) {
                    //Generating more data
                    start += count;
                    count = 15;
                    if (start==240)count=10;
                    mTop250PresentImpl.loadingMoreMovie(start, count);
                } else {
//                    Toast.makeText(getActivity(), "no more data ", Toast.LENGTH_SHORT).show();
                    Snackbar.make(recycler_top250_linear,
                            "No more data",
                            Snackbar.LENGTH_INDEFINITE).setAction("- -!", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTop250Adapter.notifyItemRemoved(mTop250Adapter.getItemCount()+1);
                            mTop250Adapter.notifyDataSetChanged();
                        }
                    }).show();
                }
            }
        });
    }

    private void loadMovies() {
        start = 0;
        count = 15;
        mTop250PresentImpl.getMovieList(start, count);
    }


    @Override
    public void showProgressDialog() {
        if (start == 0) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressDialog() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        Log.e("error",error);
        if (recycler_top250_linear != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            Snackbar.make(recycler_top250_linear,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (start == 0)
                        mTop250PresentImpl.getMovieList(start, count);
                    else mTop250PresentImpl.loadingMoreMovie(start, count);
                }
            }).show();
        }
    }

    @Override
    public void updateListItem(MoviesBean moviesBean) {
        total = moviesBean.getTotal();
        movies.addAll(moviesBean.getSubjects());
        mTop250Adapter.notifyDataSetChanged();
        mTop250Adapter.setLoaded();
    }

    @Override
    public void loadingMoreItem(MoviesBean moviesBean) {
//        if (start==240)
//        Log.e("getItemCount()",""+mTop250Adapter.getItemCount());
        mTop250Adapter.notifyItemRemoved(mTop250Adapter.getItemCount());
        total = moviesBean.getTotal();
        movies.addAll(moviesBean.getSubjects());
        mTop250Adapter.notifyDataSetChanged();
        mTop250Adapter.setLoaded();
    }
}