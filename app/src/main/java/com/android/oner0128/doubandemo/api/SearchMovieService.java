package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.bean.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rrr on 2017/6/4.
 */

public interface SearchMovieService {
    @GET("search")
    Observable<MovieBean> getSearchMovies(@Query("q") String movieNameOrPeople);
}
