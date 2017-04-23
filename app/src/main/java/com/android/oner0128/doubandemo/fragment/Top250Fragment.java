package com.android.oner0128.doubandemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250Adapter;
import com.android.oner0128.doubandemo.bean.MovieList;
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

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void upListItem(MovieList movieList) {

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
        initPresenter();
    }

    private void initPresenter() {
        mTop250PresenterImpl=new Top250PresenterImpl(this);
        mTop250Adapter=new Top250Adapter(getContext());
    }
}
