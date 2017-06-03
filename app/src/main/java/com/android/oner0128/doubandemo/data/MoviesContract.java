package com.android.oner0128.doubandemo.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hrong on 2016/10/16.
 */

public class MoviesContract {
    public static final String CONTENT_AUTHORITY = "com.android.oner0128.doubandemo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TOPRATED = "toprated";
    public static final String PATH_IN_THEATERS = "in_theaters";
    public static final String PATH_COMINGSOON = "comingsoon";
    public static final class TopRatedMoviesEntry implements BaseColumns{
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOPRATED).build();
        public static final String TABLE_NAME = "toprated";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_TMDB_ID="tmdb_id";
        public static final String COLUMN_RELEASE_DATE="release_date";
        public static final String COLUMN_GENERE_IDS="genre_ids";//影片类型
        public static final String COLUMN_IMAGE_BACKDROP="backdrop_path";
        public static final String COLUMN_IMAGE_POSTER="poster_path";
        public static final String COLUMN_VOTE_COUNT="vote_count";
        public static final String COLUMN_VOTE_AVERAGE="vote_average";
        public static final String COLUMN_POPULARITY="popularity";
        public static Uri buildTopRatedUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildTopRatedUriWithTitle(String title){
            return CONTENT_URI.buildUpon().appendPath(title).build();
        }
        public static String getTitleFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }
    public static final class InTheatersMoviesEntry implements BaseColumns{
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_IN_THEATERS).build();
        public static final String TABLE_NAME = "in_theaters";
        public static final String COLUMN_TITLE="title";
//        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_ALT="alt";
        public static final String COLUMN_DOUBAN_ID ="douban_id";
        public static final String COLUMN_RELEASE_DATE="release_date";
        public static final String COLUMN_GENERE_IDS="genre_ids";//影片类型
//        public static final String COLUMN_IMAGE_BACKDROP="backdrop_path";
        public static final String COLUMN_IMAGE_POSTER="poster_path";
        public static final String COLUMN_VOTE_COUNT="vote_count";
        public static final String COLUMN_VOTE_AVERAGE="vote_average";
//        public static final String COLUMN_POPULARITY="popularity";
        public static Uri buildInTheatersUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildInTheatersUriWithTitle(String title){
            return CONTENT_URI.buildUpon().appendPath(title).build();
        }
        public static String getTitleFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }
    public static final class ComingsoonMoviesEntry implements BaseColumns{
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMINGSOON).build();
        public static final String TABLE_NAME = "comingsoon";
        public static final String COLUMN_TITLE="title";
        //        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_ALT="alt";
        public static final String COLUMN_DOUBAN_ID ="douban_id";
        public static final String COLUMN_RELEASE_DATE="release_date";
        public static final String COLUMN_GENERE_IDS="genre_ids";//影片类型
        //        public static final String COLUMN_IMAGE_BACKDROP="backdrop_path";
        public static final String COLUMN_IMAGE_POSTER="poster_path";
        public static final String COLUMN_VOTE_COUNT="vote_count";
        public static final String COLUMN_VOTE_AVERAGE="vote_average";
        //        public static final String COLUMN_POPULARITY="popularity";
        public static Uri buildComingsoonUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildComingsoonUriWithTitle(String title){
            return CONTENT_URI.buildUpon().appendPath(title).build();
        }
        public static String getTitleFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }
}
