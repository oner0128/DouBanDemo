package com.android.oner0128.doubandemo.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250Adapter;
import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.presenter.SearchPresenterImpl;
import com.android.oner0128.doubandemo.util.ToastUtils;
import com.android.oner0128.doubandemo.view.fragment.SearchView;
import com.android.oner0128.doubandemo.view.item.MoiveItemDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchMovieActivity extends AppCompatActivity implements SearchView {
    @BindView(R.id.recycler_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_search)
    FrameLayout mFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    SearchPresenterImpl mPresenter;
    private ArrayList<MoviesBean.Subjects> movies;
    private MultiItemTypeAdapter<MoviesBean.Subjects> mAdapter;
    LoadMoreWrapper mLoadMoreWrapper;
    private String searchString;
    private int start = 0, count , total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchString = getIntent().getStringExtra("SearchString");
        toolbar.setTitle("\""+searchString+"\""+"搜索结果：");
        initPresenterAndAdapter();
        initView();
    }

    private void initPresenterAndAdapter() {
        movies = new ArrayList<>();
        mPresenter = new SearchPresenterImpl(this);
        mAdapter = new MultiItemTypeAdapter<>(this, movies);
        mAdapter.addItemViewDelegate(new MoiveItemDelegate());
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.item_loading_more);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (total!= 0 && start + count < total) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.loadingMoreSearchList(searchString,start,count);
                        }
                    }, 500);
                }else if (total!= 0 &&start >= total){
//                    mLoadMoreWrapper.notifyDataSetChanged();
                    ToastUtils.showToast(getApplicationContext(),"No more movies");
                }
            }
        });

    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        loadMovies();
    }

    private void loadMovies() {
        mPresenter.getSearchList(searchString);
    }


    @Override
    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        showProgressDialog();
        if (mRecyclerView != null) {
            Snackbar.make(mRecyclerView,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mAdapter.clearData();
                    mPresenter.getSearchList(searchString);
                }
            }).show();
        }
    }

    @Override
    public void updateListItem(MoviesBean moviesBean) {
        total = moviesBean.getTotal();
        count=moviesBean.getCount();
        movies.addAll(moviesBean.getSubjects());
        if (start+count>=total)count=total-start;
        start+=count;
        mAdapter.notifyDataSetChanged();
        mLoadMoreWrapper.notifyDataSetChanged();
//        mAdapter.setLoaded();
    }
}
