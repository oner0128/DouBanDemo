package com.android.oner0128.doubandemo.presenter;

import android.util.Log;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.view.fragment.Top250FragmentLinear;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/4/24.
 */

public class Top250PresenterImplLinear extends BasePresenterImpl implements Top250FragmentPresenter {
    static Top250FragmentLinear fragment;

    public Top250PresenterImplLinear(Top250FragmentLinear fragment) {
        this.fragment = fragment;
    }
    @Override
    public void getMovieList(int start, int count) {
        fragment.showProgressDialog();
//        APIService.getINSTANCE().getTop250Service().getTop250Movies(start, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MovieBean>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
////                        Toast.makeText(fragment.getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull MovieBean movieBean) {
//                        Log.d("Test", movieBean.getCount() + movieBean.getTitle());
//                        fragment.hideProgressDialog();
////                        MovieList list=MovieList.setMovieList(movieBean);
//                        fragment.updateListItem(movieBean);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        fragment.hideProgressDialog();
//                        fragment.showError(e.toString());
//                        Log.e("error", e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        Disposable disposable = APIService.getINSTANCE().getTop250Service()
                .getTop250Movies(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieBean>() {
                    @Override
                    public void onNext(@NonNull MovieBean movieBean) {
//                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
//                        MovieList list=MovieList.setMovieList(movieBean);
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

                    }
                });
        addDisposabe(disposable);
    }

    @Override
    public void loadingMoreMovie(int start, int count) {
//        fragment.showProgressDialog();
//        APIService.getINSTANCE().getTop250Service().getTop250Movies(start, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MovieBean>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
////                        Toast.makeText(fragment.getContext(), "Get more Movie Completed", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull MovieBean movieBean) {
////                        Log.d("Test", movieBean.getCount() + movieBean.getTitle());
//                        fragment.hideProgressDialog();
////                        MovieList list=MovieList.setMovieList(movieBean);
//                        fragment.loadingMoreItem(movieBean);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        fragment.hideProgressDialog();
//                        fragment.showError(e.toString());
//                        Log.e("error", e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        Disposable disposable = APIService.getINSTANCE().getTop250Service()
                .getTop250Movies(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieBean>() {
                    @Override
                    public void onNext(@NonNull MovieBean movieBean) {
//                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
//                        fragment.hideProgressDialog();
//                        MovieList list=MovieList.setMovieList(movieBean);
                        fragment.loadingMoreItem(movieBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addDisposabe(disposable);
    }
}
