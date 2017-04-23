package com.android.oner0128.doubandemo.presenter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

/**
 * Created by rrr on 2017/4/22.
 */

public class BasePresenterImpl implements BasePresenter {
    CompositeDisposable mCompositeDisposable;
    protected void addDisposabe(Disposable  disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }
    @Override
    public void undisposable() {
        if (mCompositeDisposable!=null)mCompositeDisposable.dispose();
    }
}
