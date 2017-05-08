package com.android.oner0128.doubandemo.fragment;

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

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.InTheatersListCursorAdapter;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.data.MoviesContract;
import com.android.oner0128.doubandemo.presenter.InTheatersPresentImplTest;
import com.android.oner0128.doubandemo.util.CustomGridLayoutManager;
import com.android.oner0128.doubandemo.util.PutMoviesToSQLite;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/5/7.
 */

public class InTheatersFragmentTest extends Fragment implements InTheatersView,LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.fragment_in_theaters_test)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_in_theaters)
    RecyclerView recycler_in_theaters;
    InTheatersListCursorAdapter mInTheatersListCursorAdapter;
    GridLayoutManager gridLayoutManager;
    InTheatersPresentImplTest inTheatersPresentImpl;
    public static InTheatersFragmentTest INSTANCE;
    public final String[] MOVIEDS_COLUNMS = {
            MoviesContract.TopRatedMoviesEntry._ID,
            MoviesContract.TopRatedMoviesEntry.COLUMN_TITLE,
            MoviesContract.TopRatedMoviesEntry.COLUMN_IMAGE_POSTER,
    };
    private static final int MOVIES_LIST_LOADER_ID = 1;
    private static final String LOG_TAG = InTheatersFragmentTest.class.getSimpleName();
    public static InTheatersFragmentTest newINSTANCE() {
        if (INSTANCE == null) {
            synchronized (InTheatersFragmentTest.class) {
                if (INSTANCE == null) INSTANCE = new InTheatersFragmentTest();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_theaters_test, container, false);
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
        mInTheatersListCursorAdapter = new InTheatersListCursorAdapter(getContext(),null);
        inTheatersPresentImpl = new InTheatersPresentImplTest(this);
    }

    private void initView() {
        getActivity().getSupportLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, this);
        gridLayoutManager = new CustomGridLayoutManager(getContext(), 2);
        recycler_in_theaters.setLayoutManager(gridLayoutManager);
        recycler_in_theaters.setAdapter(mInTheatersListCursorAdapter);
        loadMovies();
    }

    private void loadMovies() {
        inTheatersPresentImpl.getInTheatersMovies(0,20);
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
        PutMoviesToSQLite.getInTheatersMoviesFromJson(getContext(),movieBean);
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
