package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.bean.MoiveDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rrr on 2017/5/8.
 */

public interface MovieDetailSevice {
    @GET("subject/{id}")
    Observable<MoiveDetailBean> getMovieDetail(@Path("id") String id);
}
