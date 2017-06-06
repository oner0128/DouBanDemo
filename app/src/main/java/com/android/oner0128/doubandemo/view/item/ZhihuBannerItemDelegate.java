package com.android.oner0128.doubandemo.view.item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import cn.bingoogolapple.bgabanner.BGAViewPager;

/**
 * Created by rrr on 2017/6/6.
 */

public class ZhihuBannerItemDelegate implements ItemViewDelegate<ZhihuItem> {
    private static final String TAG = "ZhihuBannerItemDelegate";

    private Context mContext;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_zhihu_header_banner;
    }

    @Override
    public boolean isForViewType(ZhihuItem zhihuItem, int i) {
        return zhihuItem instanceof ZhihuBannerItem;
    }

    @Override
    public void convert(ViewHolder viewHolder, ZhihuItem zhihuItem, int i) {
        mContext = viewHolder.getConvertView().getContext();
        BGABanner banner = viewHolder.getView(R.id.banner_zhihu_header);
        ZhihuBannerItem item = (ZhihuBannerItem) zhihuItem;
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(mContext)
                        .load(model)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                //此处可设置banner子项的点击事件
                Toast.makeText(mContext,"banner"+position,Toast.LENGTH_LONG).show();
            }
        });
        banner.setData(item.getImages(),item.getTitles());
    }

//    @Override
//    public void onClick(View v) {
//        if (mIds == null) {
//            Log.e(TAG, "error : id列表为空！");
//            return;
//        }
//        Intent intent = new Intent(mContext, ArticleDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("ID", mIds.get(position));
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//        Toast.makeText(mContext,TAG+mIds.get(0)+"",Toast.LENGTH_LONG).show();
//    }
}
