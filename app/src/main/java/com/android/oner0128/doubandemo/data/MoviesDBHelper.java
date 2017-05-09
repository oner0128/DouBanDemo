package com.android.oner0128.doubandemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hrong on 2016/10/16.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies.db";
    public static final int VERSION = 1;
    private static final String TAG_LOG = MoviesDBHelper.class.getSimpleName();

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREAT_TOP_RATED_TABLE = "CREATE TABLE " + MoviesContract.TopRatedMoviesEntry.TABLE_NAME + " (" +
                MoviesContract.TopRatedMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_TMDB_ID + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_GENERE_IDS + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_IMAGE_BACKDROP + " TEXT NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_IMAGE_POSTER + " TEXT NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL );";
        final String SQL_CREAT_POPULAR_TABLE = "CREATE TABLE " + MoviesContract.InTheatersMoviesEntry.TABLE_NAME + " (" +
                MoviesContract.InTheatersMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_DOUBAN_ID + " TEXT NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_ALT + " TEXT NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_GENERE_IDS + " TEXT NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_IMAGE_POSTER + " TEXT NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MoviesContract.InTheatersMoviesEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL);" ;
        db.execSQL(SQL_CREAT_TOP_RATED_TABLE);
        db.execSQL(SQL_CREAT_POPULAR_TABLE);
//        Log.d(TAG_LOG, "SQL_CREATE_LOCATION:" + SQL_CREAT_TOP_RATED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TopRatedMoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.InTheatersMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
