package com.example.android.movie.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {
    private static int ID;
    private final String  LOG_TAG =MainActivity.class.getSimpleName();
    FavoriteDBHelper favoriteDBHelper;
    private static String TITLE;
    private static String OVERVIEW;
    private static double rate;
    private static String IMAGE_PATH;
    private static String RELEASE_DATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
             .add(R.id.fragment_detail_container, new DetailActivityFragment())
                    .commit();
        }*/


                 FavoriteDBHelper fav = new FavoriteDBHelper(this);
        if(savedInstanceState == null) {
                Bundle  args = new Bundle();
                Intent intent = getIntent();
            args.putString("overView",intent.getStringExtra("overView"));
            args.putString("title",intent.getStringExtra("title"));
            args.putString("release_date", intent.getStringExtra("release_date"));
            args.putDouble("vote_average", (intent.getDoubleExtra("vote_average", 0.0)));
            args.putInt("id", intent.getIntExtra("id",1));
            args.putString("poster_path", intent.getStringExtra("poster_path"));
            args.putBoolean("toggleButton", fav.rowQueryIsFound(this, intent.getStringExtra("title")));
            args.putBoolean("testTowPane", intent.getBooleanExtra("testTwoPane", false));



            args.putParcelable("data_from_fragmentMain",getIntent().getData());

             DetailActivityFragment fragmentDetails = new DetailActivityFragment();
                fragmentDetails.setArguments(args);


            getSupportFragmentManager().beginTransaction().add(R.id.fragment_detail_container,fragmentDetails)
            .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        favoriteDBHelper = new FavoriteDBHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_activiy, menu);
        return true;
    }

    public void favorite(View v) {
        Button b = (Button) findViewById(R.id.favorite);
        if (b.getText().equals("FAVORITE")) {
     /*       String title=intent.getStringExtra("title");
            if(title!=null)Log.d(LOG_TAG, "111111111111111111111111111111111111111111111111111111111111111111111");
            Favorited favoo= new Favorited(1,title);
            MyDBHelper.AddFavorite(favoo);*/
            Log.v(LOG_TAG, "favorite");
            Intent intent = this.getIntent();
            TITLE= intent.getStringExtra("title");
            OVERVIEW= intent.getStringExtra("overView");
            ID= intent.getIntExtra("id",1);
            rate= intent.getDoubleExtra("vote_average", 0.0);
            String VOTE_AVERAGE =Double.toString(rate)+"/10";
            IMAGE_PATH = intent.getStringExtra("poster_path");
            RELEASE_DATE=intent.getStringExtra("release_date");
            FavoriteDB favoriteDB=new FavoriteDB(OVERVIEW,RELEASE_DATE,IMAGE_PATH,TITLE,VOTE_AVERAGE,ID);
            favoriteDBHelper.AddFavorite(favoriteDB);
            b.setText("UNFAVORITE");
            b.setBackgroundColor(Color.CYAN);

        } else {
            Intent intent = this.getIntent();
            TITLE= intent.getStringExtra("title");
            favoriteDBHelper.UnFavorited(TITLE);
            b.setText("FAVORITE");
            b.setBackgroundColor(Color.LTGRAY);


        }
    }

    public void wat(View v) {
        Button b = (Button) findViewById(R.id.wat);
        if (b.getText().equals("WATCH LATER")) {
            //code to store movie data in database
            b.setText("UNWATCH");
            b.setBackgroundColor(Color.CYAN);

        } else {
            b.setText("WATCH LATER");
            b.setBackgroundColor(Color.LTGRAY);
        }
    }

}
