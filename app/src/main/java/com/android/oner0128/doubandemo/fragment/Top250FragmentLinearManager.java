package com.android.oner0128.doubandemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.Top250AdapterLinear;
import com.android.oner0128.doubandemo.adapter.OnLoadMoreListener;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.presenter.Top250PresentImplLinear;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Top250FragmentLinearManager extends Fragment implements InTheatersView {
    @BindView(R.id.fragment_top250_linear)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_top250_linear)
    RecyclerView recycler_in_theaters;
    Top250PresentImplLinear mTop250PresentImpl;
    private ArrayList<MovieBean.Subjects> movies;
    private Top250AdapterLinear mTop250Adapter;

    public int start, count, total;
    static Top250FragmentLinearManager INSTANCE;

    public static Top250FragmentLinearManager newINSTANCE() {
        if (INSTANCE == null) {
            synchronized (Top250FragmentLinearManager.class) {
                if (INSTANCE == null) INSTANCE = new Top250FragmentLinearManager();
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
        mTop250PresentImpl = new Top250PresentImplLinear(this);
        movies = new ArrayList<>();
    }

    private void initView() {
        recycler_in_theaters.setLayoutManager(new LinearLayoutManager(getContext()));
        mTop250Adapter = new Top250AdapterLinear(recycler_in_theaters, getContext(),movies);
        recycler_in_theaters.setAdapter(mTop250Adapter);
        loadMovies();
        mTop250Adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (movies.size() <= total) {
                    movies.add(null);
                    mTop250Adapter.notifyItemInserted(movies.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            movies.remove(movies.size() - 1);
                            mTop250Adapter.notifyItemRemoved(movies.size());
                            //Generating more data
                            start+=count;
                            count=20;
                            mTop250PresentImpl.getMoreMovies(start,count);
                        }
                    }, 3000);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadMovies() {
        start = 0;
        count = 20;
        mTop250PresentImpl.getInTheatersMovies(start, count);
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
    public void updateInTheatersItems(MovieBean movieBean) {
        total = movieBean.getTotal();
        movies.addAll(movieBean.getSubjects());
        mTop250Adapter.notifyDataSetChanged();
        mTop250Adapter.setLoaded();
    }

    @Override
    public void updateMoreItems(MovieBean movieBean) {
        total = movieBean.getTotal();
        movies.addAll(movieBean.getSubjects());
        mTop250Adapter.notifyDataSetChanged();
        mTop250Adapter.setLoaded();
    }
}
