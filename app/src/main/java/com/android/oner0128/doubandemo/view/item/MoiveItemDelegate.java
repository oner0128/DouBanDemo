package com.android.oner0128.doubandemo.view.item;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.MoviesBean;
import com.android.oner0128.doubandemo.util.StringFormatUtils;
import com.android.oner0128.doubandemo.view.activity.MovieDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by rrr on 2017/6/21.
 */

public class MoiveItemDelegate implements ItemViewDelegate<MoviesBean.Subjects> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recyclerview_top250;
    }

    @Override
    public boolean isForViewType(MoviesBean.Subjects subjects, int i) {
        return subjects instanceof MoviesBean.Subjects;
    }

    @Override
    public void convert(ViewHolder viewHolder, final MoviesBean.Subjects subjects, int i) {
        final Context mContext = viewHolder.getConvertView().getContext();
        final String imageViewURL = subjects.getImages().getSmall();
        Glide.with(mContext)
                .load(imageViewURL)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_launcher)
                .into((ImageView) viewHolder.getView(R.id.imageV_post));

        ((TextView)viewHolder.getView(R.id.tv_title)).setText(i+1+"."+subjects.getTitle());

        ((TextView)viewHolder.getView(R.id.tv_rating)).setText(subjects.getRating().getAverage()+"/10.0");

        ((TextView)viewHolder.getView(R.id.tv_casts)).setText("主演:"+ StringFormatUtils.formatCastsToString(subjects.getCasts()));

        ((TextView)viewHolder.getView(R.id.tv_director)).setText("导演:"+StringFormatUtils.formatCastsToString(subjects.getDirectors()));

        ((TextView)viewHolder.getView(R.id.tv_years)).setText("年份:"+subjects.getYear());

        ((TextView)viewHolder.getView(R.id.tv_genres)).setText("类型:"+StringFormatUtils.formatListToString(subjects.getGenres()));

        ((CardView)viewHolder.getView(R.id.card_view_item_recycler_view)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("MovieID",subjects.getId());
                intent.putExtra("MovieTitle",subjects.getTitle());
                intent.putExtra("MovieImg",imageViewURL);
                mContext.startActivity(intent);
            }
        });
    }
}
