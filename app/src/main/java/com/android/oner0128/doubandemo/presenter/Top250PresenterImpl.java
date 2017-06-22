package com.android.oner0128.doubandemo.presenter;

import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.view.fragment.Top250Fragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrr on 2017/4/24.
 */

public class Top250PresenterImpl extends BasePresenterImpl implements Top250FragmentPresenter {
    static Top250Fragment fragment;

    public Top250PresenterImpl(Top250Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void getMovieList(int start, int count) {
        fragment.showProgressDialog();
        Disposable disposable = APIService.getINSTANCE().getDouBanService()
                .getTop250Movies(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MoviesBean>() {
                    @Override
                    public void onNext(@NonNull MoviesBean movieBean) {
//                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
                        fragment.updateListItem(movieBean);

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

    @Override
    public void loadingMoreMovie(int start, int count) {
        Disposable disposable = APIService.getINSTANCE().getDouBanService()
                .getTop250Movies(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MoviesBean>() {
                    @Override
                    public void onNext(@NonNull MoviesBean movieBean) {
//                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
                        fragment.hideProgressDialog();
                        fragment.loadingMoreItem(movieBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fragment.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addDisposabe(disposable);

//        APIService.getINSTANCE().getDouBanService()
//                .getTop250Movies(start, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MoviesBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MoviesBean movieBean) {
//////                        Log.d("Test",movieBean.getCount()+movieBean.getTitle());
//                        fragment.hideProgressDialog();
//                        fragment.loadingMoreItem(movieBean);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        fragment.showError(e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
