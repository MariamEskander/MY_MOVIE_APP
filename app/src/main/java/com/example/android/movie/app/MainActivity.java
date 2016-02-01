package com.example.android.movie.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  MoviesFragment.Callback {
    private boolean presstwice = false;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    //private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
   /*         if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, new DetailActivityFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }*/
        } else {
            mTwoPane = false;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override //when user  press back twice, user will exit the application
    public void onBackPressed() {
        if (presstwice == true) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);

        }
        presstwice = true;
        Toast.makeText(this, "Press back button again to EXIT", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                presstwice = false;
            }
        }, 100000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(MovieData movieData) {
        FavoriteDBHelper fav = new FavoriteDBHelper(this);
        if (mTwoPane) {

            Bundle args = new Bundle();
            args.putString("overView", movieData.overview);
            args.putString("title", movieData.title);
            args.putString("release_date", movieData.release_date);
            args.putDouble("vote_average", movieData.vote_average);
            args.putInt("id", movieData.id);
            args.putString("poster_path", movieData.poster_path);
            args.putBoolean("toggleButton", fav.rowQueryIsFound(this, movieData.title));
            args.putBoolean("testTwoPane", mTwoPane);
            DetailActivityFragment fragmentDetails = new DetailActivityFragment();
            fragmentDetails.setArguments(args);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, fragmentDetails).commit();
        } else {

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("overView", movieData.overview);
            intent.putExtra("title", movieData.title);
            intent.putExtra("release_date", movieData.release_date);
            intent.putExtra("vote_average", movieData.vote_average);
            intent.putExtra("id", movieData.id);
            intent.putExtra("poster_path", movieData.poster_path);
            intent.putExtra("toggleButton", fav.rowQueryIsFound(this, movieData.title));
            intent.putExtra("testTwoPane", mTwoPane);
            startActivity(intent);
        }


    }

    @Override
    protected void onStart() {
        Log.v(LOG_TAG, "in onStart");
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        Log.v(LOG_TAG, "in onResume");
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }

    @Override
    protected void onPause() {
        Log.v(LOG_TAG, "in onPause");
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop() {
        Log.v(LOG_TAG, "in onStop");
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "in onDestroy");
        super.onDestroy();
        // The activity is about to be destroyed.
    }

}
