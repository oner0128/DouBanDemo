package com.android.oner0128.doubandemo.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250AdapterLinear;
import com.android.oner0128.doubandemo.adapter.OnLoadMoreListener;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.presenter.Top250PresenterImplLinear;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Top250FragmentLinear extends Fragment implements Top250View {
    @BindView(R.id.fragment_top250_linear)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_top250)
    RecyclerView recycler_top250_linear;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    Top250PresenterImplLinear mTop250PresentImpl;
    private ArrayList<MovieBean.Subjects> movies;
    private Top250AdapterLinear mTop250Adapter;

    public int start, count, total;
    static Top250FragmentLinear INSTANCE;


    public static Top250FragmentLinear getInstance() {
        if (INSTANCE == null) {
            synchronized (Top250FragmentLinear.class) {
                if (INSTANCE == null) INSTANCE = new Top250FragmentLinear();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top250_linear, container, false);
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
        mTop250PresentImpl = new Top250PresenterImplLinear(this);
        movies = new ArrayList<>();
    }

    private void initView() {
        recycler_top250_linear.setLayoutManager(new LinearLayoutManager(getContext()));
        mTop250Adapter = new Top250AdapterLinear(recycler_top250_linear, getContext(), movies);
        recycler_top250_linear.setAdapter(mTop250Adapter);
        loadMovies();
        //swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.color_fab_scrolling);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //refresh data
                        mTop250Adapter.clearData();
                        loadMovies();
                    }
                }, 500);
            }
        });
        //loading more
        mTop250Adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (movies.size() <= total) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Generating more data
                            start += count;
                            count = 10;
                            mTop250PresentImpl.loadingMoreMovie(start, count);
                        }
                    }, 500);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadMovies() {
        start = 0;
        count = 10;
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
        if (recycler_top250_linear != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            Snackbar.make(recycler_top250_linear,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (start==0)
                    mTop250PresentImpl.getMovieList(start, count);
                    else mTop250PresentImpl.loadingMoreMovie(start, count);
                }
            }).show();
        }
    }

    @Override
    public void updateListItem(MovieBean movieBean) {
        total = movieBean.getTotal();
        movies.addAll(movieBean.getSubjects());
        mTop250Adapter.notifyDataSetChanged();
        mTop250Adapter.setLoaded();
    }

    @Override
    public void loadingMoreItem(MovieBean movieBean) {
        mTop250Adapter.notifyItemRemoved(mTop250Adapter.getItemCount());
        total = movieBean.getTotal();
        movies.addAll(movieBean.getSubjects());
        mTop250Adapter.notifyDataSetChanged();
        mTop250Adapter.setLoaded();
    }
}
