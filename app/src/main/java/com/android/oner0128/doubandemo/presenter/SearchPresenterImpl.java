package com.android.oner0128.doubandemo.presenter;

import android.util.Log;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.view.activity.SearchMovieActivityFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/6/4.
 */

public class SearchPresenterImpl extends BasePresenterImpl implements SearchPresenter {
    private SearchMovieActivityFragment fragment;

    public SearchPresenterImpl(SearchMovieActivityFragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public void getSearchList(String searchString) {
        fragment.showProgressDialog();
        Disposable disposable = APIService.getINSTANCE().getSearchMovieService()
                .getSearchMovies(searchString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieBean>() {
                    @Override
                    public void onNext(@NonNull MovieBean movieBean) {
                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
                        fragment.updateListItem(movieBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
                        Log.e("error",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","onComplete");
                    }
                });
        addDisposabe(disposable);
    }
}
