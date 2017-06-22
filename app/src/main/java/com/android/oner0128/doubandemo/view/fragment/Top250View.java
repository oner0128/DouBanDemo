package com.android.oner0128.doubandemo.view.fragment;

import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.view.BaseView;

/**
 * Created by rrr on 2017/4/22.
 */
public interface Top250View extends BaseView {
    void updateListItem(MoviesBean moviesBean);
    void loadingMoreItem(MoviesBean moviesBean);
}
