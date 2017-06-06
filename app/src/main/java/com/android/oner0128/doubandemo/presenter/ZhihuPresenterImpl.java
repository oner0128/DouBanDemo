package com.android.oner0128.doubandemo.presenter;

import android.util.Log;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.view.fragment.ZhihuFragment;

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
        Disposable disposable = APIService.getINSTANCE().getZhihuService()
                .getZhihuLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ZhihuLatestNewsBean>() {
                    @Override
                    public void onNext(@NonNull ZhihuLatestNewsBean zhihuLatestBean) {
                        Log.d("Test", zhihuLatestBean.getDate());
                        fragment.hideProgressDialog();
                        fragment.updateNewsLatestItem(zhihuLatestBean);
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
        addDisposabe(disposable);
    }

    @Override
    public void getNewsBefore(String date) {

    }
}
