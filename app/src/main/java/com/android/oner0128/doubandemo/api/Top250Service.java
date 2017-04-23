package com.android.oner0128.doubandemo.api;


import com.android.oner0128.doubandemo.bean.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rrr on 2017/4/22.
 */

public interface Top250Service {
    @GET("top250")
    Observable<MovieBean>getTop250Movies(@Query("start") int start, @Query("count") int count);
}
