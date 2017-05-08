package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.oner0128.doubandemo.MainActivity;
import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.fragment.Top250Fragment;
import com.android.oner0128.doubandemo.util.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/4/22.
 */

public class Top250Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.loadingMore {
    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NOMAL_ITEM = 1;
    boolean showLoadingMore;
    private Context mContext;
    private ArrayList<MovieBean.Subjects> mTop250MovieList = new ArrayList<>();
    int w_screen;
    int h_screen;
    int widthPx;
    int heighPx;
    Top250Fragment fragment;

    public Top250Adapter(Context context) {
        mContext = context;
    }

    public Top250Adapter(Context context, int width, int height) {
        mContext = context;
        w_screen = width;
        h_screen = height;
        Log.v("w_screen--h_screen", width + " " + height);
    }

    public Top250Adapter(Context context, Top250Fragment fragment) {
        mContext = context;
        this.fragment = fragment;
        int width = 200;
        widthPx = DensityUtil.dip2px(mContext, width);
        heighPx = widthPx * 4 / 3;
    }


    @Override
    public int getItemViewType(int position) {
//        if (position < mTop250MovieList.size() && mTop250MovieList.size() > 0)
//            return TYPE_NOMAL_ITEM;
//        return TYPE_LOADING_MORE;
        if (position >= mTop250MovieList.size() - 2) {
            return TYPE_LOADING_MORE;
        }
        return TYPE_NOMAL_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NOMAL_ITEM:
                return new Top250MoviesViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.menuitem_top250_recyclerview, parent, false));
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_loading_more, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        Log.v("getItemViewType:", type + " ");
        switch (type) {
            case TYPE_NOMAL_ITEM:
                bindViewHolderNormal((Top250MoviesViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindViewHolderLoadingMore((LoadingMoreHolder) holder, position);
                break;
            default:
                return;
        }
//        bindViewHolderNormal((IntheatersViewHolder) holder,position);
    }

    private void bindViewHolderNormal(Top250MoviesViewHolder holder, int position) {
        MovieBean.Subjects movie_Subject = mTop250MovieList.get(position);
        Log.v("pic", movie_Subject.getTitle() + "-" + movie_Subject.getImages().getMedium());
        Log.v("pic", widthPx + "-" + heighPx);
        Glide.with(fragment)
                .load(movie_Subject.getImages().getLarge())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .override(widthPx, heighPx)
                .centerCrop()
                .into(holder.imageV_top250);
    }

    private void bindViewHolderLoadingMore(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore ? View.VISIBLE : View.INVISIBLE);
    }

    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    @Override
    public int getItemCount() {
        return mTop250MovieList.size();
    }

    public void clearData() {
        mTop250MovieList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void loadingStart() {
        if (showLoadingMore) return;
        showLoadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingEnd() {
        if (!showLoadingMore) return;
        final int loadingPos = getLoadingMoreItemPosition();
        showLoadingMore = false;
        notifyItemRemoved(loadingPos);
    }

    public void addItems(MovieBean movieBean) {
        mTop250MovieList.addAll(movieBean.getSubjects());
        notifyDataSetChanged();
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar2)
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class Top250MoviesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageV_top250)
        ImageView imageV_top250;

        public Top250MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
