package com.android.oner0128.doubandemo.view.fragment;

import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.view.BaseView;

/**
 * Created by rrr on 2017/6/5.
 */

public interface ZhihuView extends BaseView {
    void updateNewsLatestItem(ZhihuLatestNewsBean zhihuLatestNewsBean);
    void updateNewsBeforeItem();
}
