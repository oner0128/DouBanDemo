package com.android.oner0128.doubandemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250Adapter;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.presenter.Top250PresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/4/22.
 */

public class Top250Fragment extends Fragment implements Top250View {
    boolean loading;
    boolean connected = true;
    Top250Adapter mTop250Adapter;

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView.OnScrollListener loadingMoreListener;
    int currentIndex;

    Top250PresenterImpl mTop250PresenterImpl;
    @BindView(R.id.recycler_top250)
    RecyclerView recycler_top250;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_top250)
    FrameLayout frameLayout;

    static Top250Fragment INSTANCE;
    public static Top250Fragment newINSTANCE(){
        if (INSTANCE==null)
        {
            synchronized (Top250Fragment.class){
                if (INSTANCE==null)INSTANCE=new Top250Fragment();
            }
        }
        return INSTANCE;
    }

    @Override
    public void showProgressDialog() {
        if (currentIndex ==0){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        if (recycler_top250 != null) {
            Snackbar.make(recycler_top250, getString(R.string.please_check_your_network), Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTop250PresenterImpl.getMovieList(currentIndex);
                }
            }).show();
        }
    }

    @Override
    public void upListItem(MovieBean movieBean) {
        loading=false;
        progressBar.setVisibility(View.INVISIBLE);
        mTop250Adapter.addItems(movieBean);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_top250,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenterAndAdapter();
        initView();
    }

    private void initPresenterAndAdapter() {
        mTop250PresenterImpl=new Top250PresenterImpl(this);
        mTop250Adapter=new Top250Adapter(getContext(),this);
    }

    private void initView() {
//        gridLayoutManager=new GridLayoutManager(getContext(),3);
//        initialListener();
//        mLinearLayoutManager = new LinearLayoutManager(getContext());
//        recycler_top250.setLayoutManager(mLinearLayoutManager);
//        recycler_top250.setLayoutManager(gridLayoutManager);
//        recycler_top250.setHasFixedSize(true);
        recycler_top250.setAdapter(mTop250Adapter);
//        recycler_top250.addOnScrollListener(loadingMoreListener);
        if (connected) {
            loadMovie();
        }
    }

    private void loadMovie() {
        Log.v("loadMovie",mTop250Adapter.getItemCount()+"");
        if (mTop250Adapter.getItemCount() > 0) {
            mTop250Adapter.clearData();
        }
        currentIndex = 20;
        mTop250PresenterImpl.getMovieList(currentIndex);

    }

    private void initialListener() {
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();
                    Log.v("OnScrollListener",visibleItemCount+"-"+pastVisiblesItems+"-"+totalItemCount);
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMoreMovies();
                    }
                }
            }
        };

    }

    private void loadMoreMovies() {
        mTop250Adapter.loadingStart();
        currentIndex +=20;
        mTop250PresenterImpl.getMovieList(currentIndex);

    }
}
