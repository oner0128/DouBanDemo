package com.android.oner0128.doubandemo.presenter;

import android.app.Activity;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.util.PutMoviesToSQLite;
import com.android.oner0128.doubandemo.view.fragment.ComingsoonFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/6/3.
 */

public class ComingsoonPresenterImpl extends BasePresenterImpl implements ComingsoonPresenter {
    ComingsoonFragment fragment;
    Activity activity;
    public ComingsoonPresenterImpl(ComingsoonFragment fragment) {
        this.fragment = fragment;
    }
    public ComingsoonPresenterImpl(Activity activity, ComingsoonFragment fragment) {
        this.fragment = fragment;
        this.activity=activity;
    }
    @Override
    public void getComingsoonMovies() {
        fragment.showProgressDialog();
        Disposable disposable = APIService.getINSTANCE().getDouBanService()
                .getComingsoonMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MoviesBean>() {
                    @Override
                    public void onNext(@NonNull MoviesBean movieBean) {
//                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
//                        MovieList list=MovieList.setMovieList(movieBean);
//                        fragment.updateInTheatersItems(movieBean);
                        PutMoviesToSQLite.getComingsoonMoviesFromJson(activity,movieBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
//                        Log.e("error",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addDisposabe(disposable);
    }
}
