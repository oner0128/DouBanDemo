package com.android.oner0128.doubandemo.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.data.MoviesContract;

import org.json.JSONException;

import java.util.List;
import java.util.Vector;

/**
 * Created by rrr on 2017/5/7.
 */

public class PutMoviesToSQLite {
    private static final String LOG_TAG = PutMoviesToSQLite.class.getSimpleName();

    //从DouBan获取intheatersMovies
    public static void getInTheatersMoviesFromJson(Activity activity , MovieBean movieBean) {
        activity.getContentResolver().delete(MoviesContract.InTheatersMoviesEntry.CONTENT_URI, null, null);
        List<MovieBean.Subjects> list = movieBean.getSubjects();
        int size = list.size();
        Vector<ContentValues> valuesVector = new Vector<>(size);
        String title;
        String douban_id;
        String alt;
        String release_date;
        String genre_ids;//影片类型
        String image_poster;
        int vote_count;
        double vote_average;
        for (int i = 0; i < size; i++) {


            //title
            title = list.get(i).getTitle();
            //douban_id
            douban_id = list.get(i).getId();
            //alt
            alt = list.get(i).getAlt();
            //release_date
            release_date = list.get(i).getYear();
            //genre_ids
            genre_ids = list.get(i).getGenres().get(0);
            //image_poster
            image_poster = list.get(i).getImages().getLarge();
            //Log.v(LOG_TAG, image_poster);
            //vote_count;
            vote_count = list.get(i).getCollect_count();
            //vote_average;
            vote_average = list.get(i).getRating().getAverage();

            ContentValues weatherValues = new ContentValues();


            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_TITLE, title);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_DOUBAN_ID, douban_id);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_ALT, alt);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_RELEASE_DATE, release_date);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_GENERE_IDS, genre_ids);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_IMAGE_POSTER, image_poster);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_VOTE_COUNT, vote_count);
            weatherValues.put(MoviesContract.InTheatersMoviesEntry.COLUMN_VOTE_AVERAGE, vote_average);
            valuesVector.add(weatherValues);
        }
        int isInsert = 0;
        if (valuesVector.size() > 0) {
            ContentValues[] contentValues = new ContentValues[valuesVector.size()];
            valuesVector.toArray(contentValues);
            isInsert = activity.getContentResolver().bulkInsert(MoviesContract.InTheatersMoviesEntry.CONTENT_URI, contentValues);
        }
        if (isInsert != 0)
            Log.d(LOG_TAG, LOG_TAG + " complete." + isInsert + " inserted (in_theater)");
    }
}
