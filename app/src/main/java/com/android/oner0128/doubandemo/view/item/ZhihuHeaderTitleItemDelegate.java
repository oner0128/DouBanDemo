package com.android.oner0128.doubandemo.view.item;

import com.android.oner0128.doubandemo.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by rrr on 2017/6/6.
 */

public class ZhihuHeaderTitleItemDelegate implements ItemViewDelegate<ZhihuItem> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_zhihu_header_title;
    }

    @Override
    public boolean isForViewType(ZhihuItem zhihuItem, int i) {
        return zhihuItem instanceof ZhihuHeaderTitleItem;
    }

    @Override
    public void convert(ViewHolder viewHolder, ZhihuItem zhihuItem, int i) {
        if (i == 1) {
            viewHolder.setText(R.id.tv_zhihu_header, "今日热闻");
        } else {
            viewHolder.setText(R.id.tv_zhihu_header, ((ZhihuHeaderTitleItem)zhihuItem).getFormatDate());
        }
    }
}
