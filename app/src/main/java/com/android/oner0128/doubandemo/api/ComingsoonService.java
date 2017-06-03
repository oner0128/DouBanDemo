package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.bean.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by rrr on 2017/6/3.
 */

public interface ComingsoonService {
    @GET("coming_soon")
    Observable<MovieBean> getComingsoonMovies();
}
