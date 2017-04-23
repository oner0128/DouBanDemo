package com.android.oner0128.doubandemo.presenter;

import android.util.Log;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MovieList;
import com.android.oner0128.doubandemo.fragment.Top250Fragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/4/22.
 */

public class Top250PresenterImpl extends BasePresenterImpl implements Top250FragmentPresenter {
    Top250Fragment fragment;

    public Top250PresenterImpl(Top250Fragment fragment) {
        this.fragment = fragment;

    }

    @Override
    public void getMovieList(int nums) {
        Disposable disposable = APIService.getINSTANCE().getTop250Service()
                .getTop250Movies(nums)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieList>() {
                    @Override
                    public void onNext(@NonNull MovieList list) {
                        fragment.hideProgressDialog();
                        fragment.upListItem(list);
                        Log.d("Test",list.getMovieList().get(0).getTitle());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addDisposabe(disposable);
    }
}
