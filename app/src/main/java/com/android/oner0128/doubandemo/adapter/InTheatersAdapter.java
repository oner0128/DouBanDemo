package com.android.oner0128.doubandemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by rrr on 2017/4/24.
 */

public class InTheatersAdapter extends BaseQuickAdapter<MovieBean.Subjects,BaseViewHolder>{



    public InTheatersAdapter(int layoutResId, List<MovieBean.Subjects> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieBean.Subjects item) {
        Glide.with(mContext).load(item.getImages().getLarge())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .override(300,450)
                .crossFade().centerCrop()
                .into((ImageView) helper.getView(R.id.imageV_in_theaters));
    }
}
