package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/4/25.
 */

public class InTheatersAdapter2 extends RecyclerView.Adapter<InTheatersAdapter2.MyViewHolder> {
    Context mContext;
    private ArrayList<MovieBean.Subjects> mMovieList = new ArrayList<>();
    private boolean showLoadingMore;
    public InTheatersAdapter2(Context context) {
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.menuitem_in_theaters_recyclerview, parent, false));
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(mContext).load(mMovieList.get(position).getImages().getSmall())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .override(300,450)
                .crossFade().centerCrop()
                .into(holder.imageV_in_theaters);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public void loadingStart() {
        if (showLoadingMore) return;
        showLoadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }
    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void loadingEnd() {

    }
    public void addItems(MovieBean movieBean) {
        mMovieList.addAll(movieBean.getSubjects());
        notifyDataSetChanged();
    }

    public void clearData() {
        mMovieList.clear();
        notifyDataSetChanged();
    }
    public void setData(List<MovieBean.Subjects>list) {
        mMovieList.clear();
        mMovieList.addAll(list);
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageV_in_theaters)
        ImageView imageV_in_theaters;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
