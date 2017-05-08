package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.data.MoviesContract;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/5/7.
 */

public class InTheatersListCursorAdapter extends CursorRecyclerViewAdapter<InTheatersListCursorAdapter.ViewHodler> {
    private final String LOG_TAG = InTheatersListCursorAdapter.class.getSimpleName();
    private final Context mContext;
    public final String[] MOVIEDS_COLUNMS = {
            MoviesContract.TopRatedMoviesEntry._ID,
            MoviesContract.TopRatedMoviesEntry.COLUMN_TITLE,
            MoviesContract.TopRatedMoviesEntry.COLUMN_IMAGE_POSTER,
    };
    public static final int COLUMN_MOVIE_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_IMAGE_POSTER = 2;
    public InTheatersListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mContext = context;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menuitem_in_theaters_recyclerview, parent, false);
        ViewHodler viewHodler = new ViewHodler(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(final InTheatersListCursorAdapter.ViewHodler viewHolder, final Cursor cursor) {
        //movie_image
        String imagePosterURL = cursor.getString(COLUMN_IMAGE_POSTER);
        Glide.with(mContext)
                .load(imagePosterURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .override(700, 933)
                .centerCrop()
                .into(viewHolder.imagePoster);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
//                    intent.putExtra(MovieDetailFragment.MOVIE_TITLE, viewHolder.title);
//                    context.startActivity(intent);
                Toast.makeText(mContext,cursor.getString(COLUMN_TITLE),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.imageV_in_theaters)
        ImageView imagePoster;
        View mView;

        public ViewHodler(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}
