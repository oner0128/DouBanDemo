package com.android.oner0128.doubandemo.adapter;

import android.content.Context;

import com.android.oner0128.doubandemo.view.item.ZhihuBannerItemDelegate;
import com.android.oner0128.doubandemo.view.item.ZhihuBeforeStoriesItemDelegate;
import com.android.oner0128.doubandemo.view.item.ZhihuHeaderTitleItemDelegate;
import com.android.oner0128.doubandemo.view.item.ZhihuItem;
import com.android.oner0128.doubandemo.view.item.ZhihuStoriesItemDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by rrr on 2017/6/5.
 */

public class ZhihuAdapter extends MultiItemTypeAdapter<ZhihuItem> {
    public ZhihuAdapter(Context context, List<ZhihuItem> datas) {
        super(context, datas);
        addItemViewDelegate(new ZhihuBannerItemDelegate());
        addItemViewDelegate(new ZhihuHeaderTitleItemDelegate());
        addItemViewDelegate(new ZhihuStoriesItemDelegate());
        addItemViewDelegate(new ZhihuBeforeStoriesItemDelegate());
    }
}
