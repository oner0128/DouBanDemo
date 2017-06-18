package com.android.oner0128.doubandemo.presenter;

import android.util.Log;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.ZhihuBeforeNewsBean;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.view.fragment.ZhihuFragment;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/4/24.
 */

public class ZhihuPresenterImpl extends BasePresenterImpl implements ZhihuPresenter {
    static ZhihuFragment fragment;

    public ZhihuPresenterImpl(ZhihuFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void getNewsLatest() {
        fragment.showProgressDialog();
        APIService.getINSTANCE().getZhihuService()
                .getZhihuLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuLatestNewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZhihuLatestNewsBean zhihuLatestNewsBean) {
                        Log.d("Test", zhihuLatestNewsBean.getDate());
                        fragment.hideProgressDialog();
                        fragment.updateNewsLatestItem(zhihuLatestNewsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getNewsBefore(String date) {
        fragment.showProgressDialog();
        APIService.getINSTANCE().getZhihuService()
                .getZhihuBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuBeforeNewsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ZhihuBeforeNewsBean zhihuBeforeNewsBean) {
                        Logger.d(zhihuBeforeNewsBean.getDate());
                        fragment.hideProgressDialog();
                        fragment.updateNewsBeforeItem(zhihuBeforeNewsBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fragment.hideProgressDialog();
                        fragment.showError(e.toString());
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
