package com.android.oner0128.doubandemo.view.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.InTheatersListCursorAdapter;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.data.MoviesContract;
import com.android.oner0128.doubandemo.presenter.InTheatersPresentImpl;
import com.android.oner0128.doubandemo.util.CustomGridLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/5/7.
 */

public class InTheatersFragment extends Fragment implements InTheatersView,LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.fragment_in_theaters)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_in_theaters)
    RecyclerView recycler_in_theaters;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    InTheatersListCursorAdapter mInTheatersListCursorAdapter;
    GridLayoutManager gridLayoutManager;
    InTheatersPresentImpl inTheatersPresentImpl;
    public static InTheatersFragment INSTANCE;
    public final String[] MOVIEDS_COLUNMS = {
            MoviesContract.InTheatersMoviesEntry._ID,
            MoviesContract.InTheatersMoviesEntry.COLUMN_TITLE,
            MoviesContract.InTheatersMoviesEntry.COLUMN_DOUBAN_ID,
            MoviesContract.InTheatersMoviesEntry.COLUMN_IMAGE_POSTER,
    };
    public static final int COLUMN_MOVIE_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_DOUBAN_ID = 2;
    public static final int COLUMN_IMAGE_POSTER = 3;
    private static final int MOVIES_LIST_LOADER_ID = 1;
    private static final String LOG_TAG = InTheatersFragment.class.getSimpleName();
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

    private void initPresenterAndAdapter() {
        mInTheatersListCursorAdapter = new InTheatersListCursorAdapter(getContext(),this,null);
        inTheatersPresentImpl = new InTheatersPresentImpl(getActivity(),this);
    }

    private void initView() {
        getActivity().getSupportLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, this);
        gridLayoutManager = new CustomGridLayoutManager(getContext(), 2);
        recycler_in_theaters.setLayoutManager(gridLayoutManager);
        recycler_in_theaters.setAdapter(mInTheatersListCursorAdapter);
        loadMovies();
    }

    private void loadMovies() {
        inTheatersPresentImpl.getInTheatersMovies();
    }


    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void updateInTheatersItems(MovieBean movieBean) {

    }

    @Override
    public void updateMoreItems(MovieBean movieBean) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MoviesContract.InTheatersMoviesEntry.CONTENT_URI,
                MOVIEDS_COLUNMS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mInTheatersListCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mInTheatersListCursorAdapter.swapCursor(null);
    }
}