package com.android.oner0128.doubandemo.view.item;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.PersonBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;


/**
 * Created by rrr on 2017/6/21.
 */

public class PersonItemDelegate implements ItemViewDelegate<PersonBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recyclerview_person;
    }

    @Override
    public boolean isForViewType(PersonBean personBean, int i) {
        return personBean instanceof PersonBean;
    }

    @Override
    public void convert(ViewHolder viewHolder, PersonBean personBean, int i) {
        final Context mContext = viewHolder.getConvertView().getContext();
        Glide.with(mContext)
                .load(personBean.getImgUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_launcher)
                .into((ImageView) viewHolder.getView(R.id.imageV_post));
        viewHolder.setText(R.id.tv_name,personBean.getName());

        viewHolder.setText(R.id.tv_job,personBean.getJob());
    }
}
