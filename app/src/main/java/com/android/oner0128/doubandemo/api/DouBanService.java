package com.android.oner0128.doubandemo.api;


import com.android.oner0128.doubandemo.bean.MoviesBean;
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
    Observable<MoviesBean>getTop250Movies(@Query("start") int start, @Query("count") int count);
    @GET("in_theaters")
    Observable<MoviesBean> getInTheatersMovies();
    @GET("coming_soon")
    Observable<MoviesBean> getComingsoonMovies();
    @GET("subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);
    @GET("search")
    Observable<MoviesBean> getSearchMovies(@Query("q") String movieNameOrPeople);
    @GET("search")
    Observable<MoviesBean> getSearchMovies(@Query("q") String movieNameOrPeople,@Query("start") int start, @Query("count") int count);
}
