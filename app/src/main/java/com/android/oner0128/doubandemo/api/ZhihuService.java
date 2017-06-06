package com.android.oner0128.doubandemo.api;

import com.android.oner0128.doubandemo.bean.ZhihuBeforeNewsBean;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.bean.ZhihuStoryContentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rrr on 2017/6/5.
 */

public interface ZhihuService {
    @GET("news/latest")
    Observable<ZhihuLatestNewsBean> getZhihuLatestNews();
    @GET("news/before/{date}")
    Observable<ZhihuBeforeNewsBean> getZhihuBeforeNews();
    @GET("news/{storyId}")
    Observable<ZhihuStoryContentBean> getStoryContent(@Path("storyId") int storyId);
}
