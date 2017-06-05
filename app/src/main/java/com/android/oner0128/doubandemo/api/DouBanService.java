package com.android.oner0128.doubandemo.api;


import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.bean.MovieDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rrr on 2017/4/22.
 */

public interface DouBanService {
    @GET("top250")
    Observable<MovieBean>getTop250Movies(@Query("start") int start, @Query("count") int count);
    @GET("in_theaters")
    Observable<MovieBean> getInTheatersMovies();
    @GET("coming_soon")
    Observable<MovieBean> getComingsoonMovies();
    @GET("subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);
    @GET("search")
    Observable<MovieBean> getSearchMovies(@Query("q") String movieNameOrPeople);
}
