package com.android.oner0128.doubandemo.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.fragment.Top250FragmentLinearManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/4/24.
 */

public class Top250PresentImplLinear extends BasePresenterImpl implements InTheatersPresenter {
    static Top250FragmentLinearManager fragment;

    public Top250PresentImplLinear(Top250FragmentLinearManager fragment) {
        this.fragment = fragment;

    }
    @Override
    public void getInTheatersMovies(int start, int count) {
        fragment.showProgressDialog();
       APIService.getINSTANCE().getTop250Service().getTop250Movies(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Toast.makeText(fragment.getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

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
//        addDisposabe(disposable);
    }

    @Override
    public void getMoreMovies(int start, int count) {
        fragment.showProgressDialog();
        APIService.getINSTANCE().getTop250Service().getTop250Movies(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Toast.makeText(fragment.getContext(), "Get more Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(@NonNull MovieBean movieBean) {
                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
//                        MovieList list=MovieList.setMovieList(movieBean);
                        fragment.updateMoreItems(movieBean);
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
    }
}
