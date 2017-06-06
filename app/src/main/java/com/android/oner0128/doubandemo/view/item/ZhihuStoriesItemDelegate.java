package com.android.oner0128.doubandemo.view.item;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.view.activity.ZhihuStoryContentActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by rrr on 2017/6/6.
 */

public class ZhihuStoriesItemDelegate implements ItemViewDelegate<ZhihuItem> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_zhihu_stories_list;
    }

    @Override
    public boolean isForViewType(ZhihuItem zhihuItem, int i) {
        return zhihuItem instanceof ZhihuLatestNewsBean.StoriesBean;
    }

    @Override
    public void convert(ViewHolder viewHolder, ZhihuItem zhihuItem, int i) {
        final Context mContext = viewHolder.getConvertView().getContext();

        final ZhihuLatestNewsBean.StoriesBean storiesEntity = (ZhihuLatestNewsBean.StoriesBean)zhihuItem;

        viewHolder.setText(R.id.story_title_tv, storiesEntity.getTitle());

        if (storiesEntity.getImages() != null) {
            ImageView stroyImg = viewHolder.getView(R.id.story_iv);
            //Glide.with(viewHolder.getConvertView().getContext()).load(storiesEntity.getImages().get(0)).into(stroyImg);
            Glide.with(mContext).load(storiesEntity.getImages().get(0)).into(stroyImg);
            if (storiesEntity.getImages().size() > 1) {
                viewHolder.getView(R.id.multi_pic_iv).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.multi_pic_iv).setVisibility(View.GONE);
            }
            viewHolder.getView(R.id.story_frame_iv).setVisibility(View.VISIBLE);
        } else {
            viewHolder.getView(R.id.story_frame_iv).setVisibility(View.GONE);
        }

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ZhihuStoryContentActivity.class);
                intent.putExtra("storyId", storiesEntity.getId());
                mContext.startActivity(intent);
//                Toast.makeText(mContext,storiesEntity.getTitle()+"",Toast.LENGTH_LONG).show();
            }
        });
    }
}
