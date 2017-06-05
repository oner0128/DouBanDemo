package com.android.oner0128.doubandemo.view.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.GridLayoutCursorAdapter;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.data.MoviesContract;
import com.android.oner0128.doubandemo.presenter.ComingsoonPresenterImpl;
import com.android.oner0128.doubandemo.util.RecyclerViewScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/5/7.
 */

public class ComingsoonFragment extends Fragment implements ComingsoonView,LoaderManager.LoaderCallbacks<Cursor>,View.OnScrollChangeListener {
    @BindView(R.id.fragment_comingsoon)
    CoordinatorLayout fragment_comingsoon;
    @BindView(R.id.recycler_comingsoon)
    RecyclerView recycler_comingsoon;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    GridLayoutManager gridLayoutManager;
    GridLayoutCursorAdapter mGridLayoutCursorAdapter;
    ComingsoonPresenterImpl mComingsoonPresenterImpl;
    public static ComingsoonFragment INSTANCE;
    public final String[] MOVIEDS_COLUNMS = {
            MoviesContract.ComingsoonMoviesEntry._ID,
            MoviesContract.ComingsoonMoviesEntry.COLUMN_TITLE,
            MoviesContract.ComingsoonMoviesEntry.COLUMN_DOUBAN_ID,
            MoviesContract.ComingsoonMoviesEntry.COLUMN_IMAGE_POSTER,
    };
    public static final int COLUMN_MOVIE_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_DOUBAN_ID = 2;
    public static final int COLUMN_IMAGE_POSTER = 3;
    private static final int MOVIES_LIST_LOADER_ID = 2;
    private static final String LOG_TAG = ComingsoonFragment.class.getSimpleName();
    public static ComingsoonFragment getInstance() {
        if (INSTANCE == null) {
            synchronized (ComingsoonFragment.class) {
                if (INSTANCE == null) INSTANCE = new ComingsoonFragment();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comingsoon, container, false);
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
        mGridLayoutCursorAdapter = new GridLayoutCursorAdapter(getContext(),this,null);
        mComingsoonPresenterImpl = new ComingsoonPresenterImpl(getActivity(),this);
    }

    private void initView() {
        getActivity().getSupportLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, this);
//        gridLayoutManager = new CustomGridLayoutManager(getContext(), 2);
        recycler_comingsoon.setLayoutManager(new GridLayoutManager(getContext(),2));
        recycler_comingsoon.setAdapter(mGridLayoutCursorAdapter);
        //swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.color_fab_scrolling);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //refresh data
                        mComingsoonPresenterImpl.getComingsoonMovies();
                    }
                }, 100);
            }
        });
        loadMovies();

        recycler_comingsoon.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void hide() {
//                ViewPropertyAnimator animator = appBarLayout.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        appBarLayout.setVisibility(View.GONE);
//                    }
//                },animator.getDuration());
            }

            @Override
            public void show() {
//                appBarLayout.setVisibility(View.VISIBLE);
//                appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
            }
        });
    }

    private void loadMovies() {
        mComingsoonPresenterImpl.getComingsoonMovies();
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

    }

    @Override
    public void updateComingsoonItems(MovieBean movieBean) {

    }

    @Override
    public void updateMoreItems(MovieBean movieBean) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MoviesContract.ComingsoonMoviesEntry.CONTENT_URI,
                MOVIEDS_COLUNMS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mGridLayoutCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mGridLayoutCursorAdapter.swapCursor(null);
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }
}
