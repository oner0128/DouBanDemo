package com.android.oner0128.doubandemo.view.fragment;

import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.view.BaseView;

/**
 * Created by rrr on 2017/4/22.
 */
public interface Top250View extends BaseView {
    void updateListItem(MovieBean movieBean);
    void loadingMoreItem(MovieBean movieBean);
}
