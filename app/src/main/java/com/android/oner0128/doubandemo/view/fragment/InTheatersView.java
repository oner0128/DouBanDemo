package com.android.oner0128.doubandemo.view.fragment;

import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.view.BaseView;

/**
 * Created by rrr on 2017/4/24.
 */

public interface InTheatersView extends BaseView {
    void updateInTheatersItems(MoviesBean moviesBean);
    void updateMoreItems(MoviesBean moviesBean);
}
