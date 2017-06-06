package com.android.oner0128.doubandemo.view.activity;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250Adapter;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.presenter.SearchPresenterImpl;
import com.android.oner0128.doubandemo.view.fragment.SearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchMovieActivityFragment extends Fragment implements SearchView {
    @BindView(R.id.recycler_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    SearchPresenterImpl mSearchPresenterImpl;
    private ArrayList<MovieBean.Subjects> movies;
    private Top250Adapter mAdapter;
    private String searchString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchString = getActivity().getIntent().getStringExtra("SearchString");
        initPresenterAndAdapter();
        initView();
    }

    private void initPresenterAndAdapter() {
        mSearchPresenterImpl = new SearchPresenterImpl(this);
        movies = new ArrayList<>();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Top250Adapter(mRecyclerView, getContext(), movies);
        mRecyclerView.setAdapter(mAdapter);
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
                        mAdapter.clearData();
                        loadMovies();
                    }
                }, 500);
            }
        });
    }

    private void loadMovies() {
        mSearchPresenterImpl.getSearchList(searchString);
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
        if (mRecyclerView != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            Snackbar.make(mRecyclerView,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.clearData();
                    mSearchPresenterImpl.getSearchList(searchString);
                }
            }).show();
        }
    }

    @Override
    public void updateListItem(MovieBean movieBean) {
        movies.addAll(movieBean.getSubjects());
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();
    }
}
