package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.App;
import com.android.oner0128.doubandemo.util.IntenetUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rrr on 2017/4/22.
 */

public class APIService {
    private static final String BASE_MOVIE_URL = "https://api.douban.com/v2/movie/";
    private static final Interceptor CACHE_CONTROL_INTERCEPTOP = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response mResponse = chain.proceed(chain.request());
            if (IntenetUtils.isInternetAvailable(App.getContext())) {
                int maxAge = 5 * 60; // 5分钟在线缓存
                return mResponse.newBuilder().removeHeader("Prama")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 4周离线缓存
                return mResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };


    public static APIService apiService;

    public static APIService getINSTANCE() {
        if (apiService == null) {
            synchronized (APIService.class) {
                if (apiService == null) apiService = new APIService();
            }
        }
        return apiService;
    }

    private static File HTTPCACHEDIRECTORY = new File(App.getContext().getCacheDir(), "doubanCache");
    private static long CACHE_SIZE = 20 * 1024 * 1024;//20MB
    private static Cache cache = new Cache(HTTPCACHEDIRECTORY, CACHE_SIZE);
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
//            .addNetworkInterceptor(CACHE_CONTROL_INTERCEPTOP)
//            .addInterceptor(CACHE_CONTROL_INTERCEPTOP).writeTimeout(5, TimeUnit.SECONDS)
            .cache(cache).build();

    public Top250Service mTop250Service;
    public InTheatersService inTheatersService;
    public ComingsoonService mComingsoonService;
    public MovieDetailSevice mMovieDetailSevice;
    private static Object syncobj = new Object();
    public ComingsoonService getComingsoonService() {
        if (mComingsoonService == null) {
            synchronized (syncobj) {
                if (mComingsoonService == null) {
                    mComingsoonService = new Retrofit.Builder()
                            .baseUrl(BASE_MOVIE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mOkHttpClient).build().create(ComingsoonService.class);
                }
            }
        }
        return mComingsoonService;
    }
    public Top250Service getTop250Service() {
        if (mTop250Service == null) {
            synchronized (syncobj) {
                if (mTop250Service == null) {
                    mTop250Service = new Retrofit.Builder()
                            .baseUrl(BASE_MOVIE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mOkHttpClient).build().create(Top250Service.class);
                }
            }
        }
        return mTop250Service;
    }

    public MovieDetailSevice getMovieDetailSevice() {
        if (mMovieDetailSevice == null) {
            synchronized (syncobj) {
                if (mMovieDetailSevice == null) {
                    mMovieDetailSevice = new Retrofit.Builder()
                            .baseUrl(BASE_MOVIE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mOkHttpClient).build().create(MovieDetailSevice.class);
                }
            }
        }
        return mMovieDetailSevice;
    }

    public InTheatersService getInTheatersService() {
        if (inTheatersService == null) {
            synchronized (syncobj) {
                if (inTheatersService == null) {
                    inTheatersService = new Retrofit.Builder()
                            .baseUrl(BASE_MOVIE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mOkHttpClient).build().create(InTheatersService.class);
                }
            }
        }
        return inTheatersService;
    }
}
