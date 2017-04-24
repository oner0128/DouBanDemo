package com.android.oner0128.doubandemo.presenter;

import android.util.Log;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.fragment.InTheatersFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/4/24.
 */

public class InTheatersPresentImpl extends BasePresenterImpl implements InTheatersPresenter {
    InTheatersFragment fragment;

    public InTheatersPresentImpl(InTheatersFragment fragment) {
        this.fragment = fragment;

    }
    @Override
    public void getInTheatersMovies(int start, int count) {
        fragment.showProgressDialog();
        Disposable disposable = APIService.getINSTANCE().getInTheatersService()
                .getInTheatersMovies(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieBean>() {
                    @Override
                    public void onNext(@NonNull MovieBean movieBean) {
                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
//                        MovieList list=MovieList.setMovieList(movieBean);
                        fragment.updateInTheatersItems(movieBean);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
                        Log.e("error",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addDisposabe(disposable);
    }
}