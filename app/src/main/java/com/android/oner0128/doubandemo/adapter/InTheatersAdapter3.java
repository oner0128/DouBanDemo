package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.Contact;
import com.android.oner0128.doubandemo.bean.MovieBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rrr on 2017/4/26.
 */

public class InTheatersAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isLoading;
    private Context context;
    private List<MovieBean.Subjects> movies;
    private List<Contact> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }
    public InTheatersAdapter3(RecyclerView recyclerView, ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }
    public InTheatersAdapter3(RecyclerView recyclerView, List<MovieBean.Subjects> movies, Context context) {
        this.movies = movies;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.v("position",lastVisibleItem+"-"+totalItemCount);
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                if (!isLoading && totalItemCount <= (visibleItemCount + pastVisiblesItems)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }}
            }
        });
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_row, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading_more, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
//            MovieBean.Subjects movie = movies.get(position);
//            UserViewHolder userViewHolder = (UserViewHolder) holder;
//            userViewHolder.phone.setText(movie.getTitle());
//            userViewHolder.email.setText(movie.getYear());
            Contact contact = contacts.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.phone.setText(contact.getEmail());
            userViewHolder.email.setText(contact.getPhone());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return movies.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
//        return movies == null ? 0 : movies.size();
        return contacts == null ? 0 : contacts.size();
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
    private class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView phone;
        public TextView email;

        public UserViewHolder(View view) {
            super(view);
            phone = (TextView) view.findViewById(R.id.txt_phone);
            email = (TextView) view.findViewById(R.id.txt_email);
        }
    }



}
