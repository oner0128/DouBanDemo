package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.MyApplication;
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
    private static final String URL_TOP250 = "https://api.douban.com/v2/movie/";
    private static final Interceptor CACHE_CONTROL_INTERCEPTOP = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response mResponse = chain.proceed(chain.request());
            if (IntenetUtils.isInternetAvailable(MyApplication.getContext())) {
                int maxAge = 60; // 1分钟在线缓存
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
    private static File HTTPCACHEDIRECTORY=new File(MyApplication.getContext().getCacheDir(),"doubanCache");
    private static long CACHE_SIZE=12*1024*1024;//12MB
    private static Cache cache=new Cache(HTTPCACHEDIRECTORY,CACHE_SIZE);
    private static OkHttpClient mOkHttpClient =new OkHttpClient.Builder()
            .addNetworkInterceptor(CACHE_CONTROL_INTERCEPTOP)
            .addInterceptor(CACHE_CONTROL_INTERCEPTOP)
            .cache(cache).build();
    public  Top250Service mTop250Service;
    private static Object syncobj =new Object();
    
    public  Top250Service getTop250Service(){
        if (mTop250Service==null){
            synchronized (syncobj){
                if (mTop250Service==null){
                    mTop250Service=new Retrofit.Builder()
                            .baseUrl(URL_TOP250)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mOkHttpClient).build().create(Top250Service.class);
                }
            }
        }
        return mTop250Service;
    }
}
