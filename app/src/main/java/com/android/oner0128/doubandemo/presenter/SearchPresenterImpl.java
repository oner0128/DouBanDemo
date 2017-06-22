package com.android.oner0128.doubandemo.presenter;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.view.activity.SearchMovieActivity;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/6/4.
 */

public class SearchPresenterImpl extends BasePresenterImpl implements SearchPresenter {
    private SearchMovieActivity activity;

    public SearchPresenterImpl(SearchMovieActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getSearchList(String searchString) {
        activity.showProgressDialog();
        APIService.getINSTANCE().getDouBanService()
                .getSearchMovies(searchString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<MoviesBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MoviesBean moviesBean) {
                        Logger.d(moviesBean.getCount() + moviesBean.getTitle());
                        activity.hideProgressDialog();
                        activity.updateListItem(moviesBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        activity.hideProgressDialog();
                        activity.showError(e.toString());
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadingMoreSearchList(String searchString, int start, int count) {
        APIService.getINSTANCE().getDouBanService()
                .getSearchMovies(searchString, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<MoviesBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MoviesBean moviesBean) {
//                        Logger.d(movieBean.getCount() + movieBean.getTitle());
                        activity.updateListItem(moviesBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        activity.showError(e.toString());
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
