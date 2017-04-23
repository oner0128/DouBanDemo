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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

    public Top250Adapter(Context context) {
        mContext = context;
    }


//    @Override
//    public int getItemViewType(int position) {
//        if (position < mTop250MovieList.size() && mTop250MovieList.size() > 0)
//            return TYPE_NOMAL_ITEM;
//        return TYPE_LOADING_MORE;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case TYPE_NOMAL_ITEM:
                return new Top250MoviesViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.menuitem_top250_recyclerview, parent, false));
//            case TYPE_LOADING_MORE:
//                return new LoadingMoreHolder(LayoutInflater.from(mContext)
//                        .inflate(R.layout.infinite_loading, parent, false));
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        int type=getItemViewType(position);
//        switch (type){
//            case TYPE_NOMAL_ITEM:bindViewHolderNormal((Top250MoviesViewHolder) holder,position);break;
//            case TYPE_LOADING_MORE:bindViewHolderLoadingMore((LoadingMoreHolder) holder,position);break;
//            default:return;
//        }
        bindViewHolderNormal((Top250MoviesViewHolder) holder,position);
    }

    private void bindViewHolderNormal(Top250MoviesViewHolder holder, int position) {
        MovieBean.Subjects movie_Subject=mTop250MovieList.get(position);
        Log.v("pic",movie_Subject.getTitle()+"-"+movie_Subject.getImages().getSmall());
        Glide.with(mContext)
                .load(movie_Subject.getImages().getMedium())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.drawable.ic_menu_camera)
//                .error(R.drawable.ic_menu_gallery)
                .into(holder.imageV_top250);
    }

    private void bindViewHolderLoadingMore(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore? View.VISIBLE : View.INVISIBLE);
    }

    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    @Override
    public int getItemCount() {
        return mTop250MovieList.size();
    }

    public void clearData() {
        mTop250MovieList.clear();notifyDataSetChanged();
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
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
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
