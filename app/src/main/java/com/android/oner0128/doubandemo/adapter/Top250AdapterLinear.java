package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.Contact;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/4/26.
 */

public class Top250AdapterLinear extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isLoading;
    private Context mContext;
    private List<MovieBean.Subjects> movies;
    private int visibleThreshold = 20;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public Top250AdapterLinear(RecyclerView recyclerView, Context mContext, ArrayList<MovieBean.Subjects> movies) {
        this.mContext = mContext;
        this.movies = movies;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_row, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_loading_more, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            final MovieBean.Subjects movie = movies.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tv_title.setText(movie.getTitle());
            userViewHolder.tv_rating.setText(movie.getYear());
            String imageViewURL = movie.getImages().getLarge();
            Glide.with(mContext)
                    .load(imageViewURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .override(700, 933)
                    .centerCrop()
                    .into(userViewHolder.imageView);
            userViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, movie.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
//            Contact contact = contacts.get(position);
//            UserViewHolder userViewHolder = (UserViewHolder) holder;
//            userViewHolder.tv_title.setText(contact.getEmail());
//            userViewHolder.tv_rating.setText(contact.getPhone());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return movies.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
//        return contacts == null ? 0 : contacts.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void addItems(MovieBean movieBean) {
        movies.addAll(movieBean.getSubjects());
        notifyDataSetChanged();
    }

    public void clearData() {
        movies.clear();
        notifyDataSetChanged();
    }

    public void loadingStart() {
        if (isLoading) return;
        isLoading = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    private int getLoadingMoreItemPosition() {
        return isLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }


    // "Loading item" ViewHolder
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        }
    }

    // "Normal item" ViewHolder
    public class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageV_top250)
        ImageView imageView;
        @BindView(R.id.card_view_item_recycler_view)
        CardView cardView;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_rating)
        TextView tv_rating;
        @BindView(R.id.tv_director)
        TextView tv_director;
        @BindView(R.id.tv_years)
        TextView tv_years;

        public UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
