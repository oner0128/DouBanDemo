package com.android.oner0128.doubandemo.fragment;

import com.android.oner0128.doubandemo.bean.MovieBean;

/**
 * Created by rrr on 2017/4/22.
 */
public interface Top250View extends BaseView {
    void updateListItem(MovieBean movieBean);
    void loadingMoreItem(MovieBean movieBean);
}
