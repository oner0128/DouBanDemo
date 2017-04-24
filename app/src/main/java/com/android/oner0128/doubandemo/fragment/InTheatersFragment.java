package com.android.oner0128.doubandemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.InTheatersAdapter;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.presenter.InTheatersPresentImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InTheatersFragment extends Fragment implements InTheatersView {
    @BindView(R.id.fragment_in_theaters)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_in_theaters)
    RecyclerView recycler_in_theaters;
    InTheatersAdapter inTheatersAdapter;
    InTheatersPresentImpl inTheatersPresentImpl;
    private int start = 0, count = 10;
    static InTheatersFragment INSTANCE;

    public static InTheatersFragment newINSTANCE() {
        if (INSTANCE == null) {
            synchronized (InTheatersFragment.class) {
                if (INSTANCE == null) INSTANCE = new InTheatersFragment();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_theaters, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenterAndAdapter();
        initView();
    }

    private void initView() {
        recycler_in_theaters.setAdapter(inTheatersAdapter);
        inTheatersPresentImpl.getInTheatersMovies(start, count);
    }

    private void initPresenterAndAdapter() {
        inTheatersAdapter = new InTheatersAdapter(
                R.layout.menuitem_in_theaters_recyclerview,
                new ArrayList<MovieBean.Subjects>());
        inTheatersPresentImpl = new InTheatersPresentImpl(this);
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
        inTheatersAdapter.addData(movieBean.getSubjects());
    }
}
