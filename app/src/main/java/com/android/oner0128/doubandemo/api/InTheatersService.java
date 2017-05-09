package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.bean.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rrr on 2017/4/24.
 */

public interface InTheatersService {
    @GET("in_theaters")
    Observable<MovieBean> getInTheatersMovies();
}
