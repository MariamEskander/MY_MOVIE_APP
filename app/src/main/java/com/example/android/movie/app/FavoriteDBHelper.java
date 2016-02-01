package com.example.android.movie.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo 2 on 1/26/2016.
 */
public class FavoriteDBHelper extends SQLiteOpenHelper {
    private final String  LOG_TAG =FavoriteDBHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "movies.db";
    private static final String TABLE_FAV= "favorite";
    private static final String COLUMN_TITLE = "title";
   // private static final String COLUMN_ID = "id";
    private static final String COLUMN_OVERVIEW = "overview";
    private static final String COLUMN_DATE = "release_date";
    private static final String COLUMN_PATH = "poster_path";
    private static final String COLUMN_VOTE = "vote_average";


   /* final String SQL_CREATE_FAVORITED_TABLE = "CREATE TABLE " + MoviesContract.Favourited.TABLE_NAME + " (" +
            MoviesContract.Favourited.COLUMN_MOVIES_TITLE + " TEXT UNIQUE PRIMARY KEY, " +
          /*  MoviesContract.Favourited.COLUMN_MOVIES_DATE + " TEXT NOT NULL, " +
            MoviesContract.Favourited.COLUMN_MOVIES_RATING + " TEXT NOT NULL, " +
            MoviesContract.Favourited.COLUMN_MOVIES_POSTER + " TEXT UNIQUE NOT NULL, "  +
            MoviesContract.Favourited.COLUMN_MOVIES_REVIEW + " TEXT UNIQUE NOT NULL, "  +
            MoviesContract.Favourited.COLUMN_MOVIES_YOUTUBE1 + " TEXT NOT NULL, " +
            MoviesContract.Favourited.COLUMN_MOVIES_YOUTUBE2 + " TEXT NOT NULL, " +
            MoviesContract.Favourited.COLUMN_MOVIES_COMMENTS + " TEXT NOT NULL, " +*/

    // " );";

    public FavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITED_TABLE = "CREATE TABLE " + TABLE_FAV+ " (" +
               // COLUMN_ID + "TEXT NOT NULL  ,"+
                COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_PATH + " TEXT NOT NULL, " +
                COLUMN_TITLE + " TEXT NOT NULL, "  +
                COLUMN_VOTE + " TEXT NOT NULL "  +
                " );";

        /*final String SQL_CREATE_WATCHLATER_TABLE = "CREATE TABLE " + MoviesContract.WatchLater.TABLE_NAME + " (" +
                MoviesContract.WatchLater.COLUMN_MOVIES_TITLE + " TEXT UNIQUE PRIMARY KEY, " +
                MoviesContract.WatchLater.COLUMN_MOVIES_DATE + " TEXT NOT NULL, " +
                MoviesContract.WatchLater.COLUMN_MOVIES_RATING + " TEXT NOT NULL, " +
                MoviesContract.WatchLater.COLUMN_MOVIES_POSTER + " TEXT UNIQUE NOT NULL, "  +
                MoviesContract.WatchLater.COLUMN_MOVIES_REVIEW + " TEXT UNIQUE NOT NULL, "  +
                MoviesContract.WatchLater.COLUMN_MOVIES_YOUTUBE1 + " TEXT NOT NULL, " +
                MoviesContract.WatchLater.COLUMN_MOVIES_YOUTUBE2 + " TEXT NOT NULL, " +
                MoviesContract.WatchLater.COLUMN_MOVIES_COMMENTS + " TEXT NOT NULL, " +

                " );";*/

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITED_TABLE);
       /* sqLiteDatabase.execSQL(SQL_CREATE_WATCHLATER_TABLE);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.WatchLater.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void AddFavorite(FavoriteDB favoo){
        String q="x";
        SQLiteDatabase d= getWritableDatabase();
        q=("SELECT * FROM " + TABLE_FAV
                 + " WHERE " + COLUMN_TITLE + "=\"" + favoo.getTitle() + "\";");
        Cursor cursor = d.rawQuery(q, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            d.close();
            q=null;
        }
        if(q!=null){}
        else{ContentValues values=new ContentValues();
        values.put(COLUMN_OVERVIEW, favoo.getOverview());
        values.put(COLUMN_DATE, favoo.getRelease_date());
        values.put(COLUMN_PATH, favoo.getPoster_path());
        values.put(COLUMN_TITLE, favoo.getTitle());
        values.put(COLUMN_VOTE, favoo.getVote_average());
      // values.put(COLUMN_ID, favoo.getId()
               // );
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_FAV, null, values);
        db.close();}

    }
    public boolean rowQueryIsFound(Context context,String i){
        boolean row =false;
        SQLiteDatabase db =getWritableDatabase();

        String query =("SELECT "+COLUMN_TITLE+" FROM " +TABLE_FAV
                +" WHERE " +COLUMN_TITLE + " =\" " +i+"\";");


        Cursor cursor= db.rawQuery(query, null);

        if (cursor.getCount()>0)
            row=true ;
          db.close();
        return row;

    }





    public void UnFavorited(String title){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FAV
                + " WHERE " + COLUMN_TITLE + "=\"" + title + "\";");
        db.close();

    }


   /* public MovieData[] getFav(){
        MovieData[] fav;
        SQLiteDatabase db=getReadableDatabase();
        *//*ArrayList<String> fav = {COLUMN_OVERVIEW,COLUMN_DATE,COLUMN_PATH,COLUMN_TITLE
                ,COLUMN_VOTE
   *//*

        String q="SELECT * FROM"+ TABLE_FAV;
        Cursor cursor = db.rawQuery(q, null);
        fav = new MovieData[cursor.getCount()];

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getCount(); i++) {
                    fav[i] = new MovieData();
                    fav[i].poster_path = COLUMN_PATH;
                    fav[i].title = COLUMN_TITLE;
                    fav[i].overview = COLUMN_OVERVIEW;
                    fav[i].release_date = COLUMN_DATE;
                    fav[i].vote_average = Double.parseDouble(COLUMN_VOTE);
                }
            } while (cursor.moveToNext());
        }

        db.close();
        return fav;

    }*/
   public  MovieData[] query(Context context){
       MovieData [] movieData;
       int index =0;
       String query ="SELECT * FROM " +TABLE_FAV;
       SQLiteDatabase  db = getReadableDatabase();
       Cursor cursor= db.rawQuery(query,null);
       movieData= new MovieData[cursor.getCount()];
       cursor.moveToFirst();

       while (!cursor.isAfterLast()){

           movieData[index]= new MovieData();
           if(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))!=null){
               movieData[index].overview = cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW));
               movieData[index].title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
               movieData[index].release_date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
               movieData[index].poster_path = cursor.getString(cursor.getColumnIndex(COLUMN_PATH));
               movieData[index].vote_average = cursor.getDouble(cursor.getColumnIndex(COLUMN_VOTE));
              // movieData[index].id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));


           }
           cursor.moveToNext();
           index++;

       }
       db.close();
       return movieData;
   }

}
/*
JSONArray result = Json.getJSONArray(MOVIE_RESULT);

movieData = new MovieData[result.length()];


       while) {

        JSONObject jsonContainer = result.getJSONObject(i);
        movieData[i] = new MovieData();
        movieData[i].poster_path = jsonContainer.getString(POSTER_PATH);
        movieData[i].title = jsonContainer.getString(MOVIE_TITLE);
        movieData[i].vote_count = jsonContainer.getDouble(MOVIE_COUNT);
        movieData[i].id = jsonContainer.getInt(MOVIE_ID);
        movieData[i].overview = jsonContainer.getString(MOVIE_OVERVIEW);
        movieData[i].popularity = jsonContainer.getDouble(MOVIE_POPULARITY);
        movieData[i].release_date = jsonContainer.getString(MOVIE_RELEASE_DATE);
        movieData[i].vote_average = jsonContainer.getDouble(MOVIE_AVERAGE);
        }*/
