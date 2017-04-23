package com.android.oner0128.doubandemo.api;


import com.android.oner0128.doubandemo.bean.MovieList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rrr on 2017/4/22.
 */

public interface Top250Service {
    @GET("https://api.douban.com/v2/movie/top250?start=0&count={id}")
    Observable<MovieList>getTop250Movies(@Path("id")int count);
}
