package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.view.activity.MovieDetailActivity;
import com.android.oner0128.doubandemo.view.fragment.FragmentComingsoon;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/5/7.
 */

public class ComingsoonListCursorAdapter extends CursorRecyclerViewAdapter<ComingsoonListCursorAdapter.ViewHodler> {
    private final String LOG_TAG = ComingsoonListCursorAdapter.class.getSimpleName();
    private final Context mContext;
    private final Fragment mFragment;


    public ComingsoonListCursorAdapter(Context context, Fragment fragment, Cursor cursor) {
        super(context, cursor);
        this.mContext = context;
        this.mFragment=fragment;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_comingsoon, parent, false);
        ViewHodler viewHodler = new ViewHodler(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(final ComingsoonListCursorAdapter.ViewHodler viewHolder, final Cursor cursor) {
        //movie_image
        String imagePosterURL = cursor.getString(FragmentComingsoon.COLUMN_IMAGE_POSTER);
        final String title=cursor.getString(FragmentComingsoon.COLUMN_TITLE);
        final String id=cursor.getString(FragmentComingsoon.COLUMN_DOUBAN_ID);
        Glide.with(mFragment)
                .load(imagePosterURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .override(700, 933)
                .centerCrop()
                .into(viewHolder.imagePoster);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("MovieID",id);
                intent.putExtra("MovieTitle",title);
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_comingsoon)
        ImageView imagePoster;
        View mView;

        public ViewHodler(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}
