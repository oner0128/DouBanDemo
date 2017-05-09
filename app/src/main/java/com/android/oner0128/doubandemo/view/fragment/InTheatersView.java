package com.android.oner0128.doubandemo.view.fragment;

import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.view.BaseView;

/**
 * Created by rrr on 2017/4/24.
 */

public interface InTheatersView extends BaseView {
    void updateInTheatersItems(MovieBean movieBean);
    void updateMoreItems(MovieBean movieBean);
}
