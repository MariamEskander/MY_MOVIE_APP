package com.example.android.movie.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {
    private MovieAdapter moviesAdapter;
    private GridView gridView;
    private ArrayList<MovieData> moviesArray;
    private MovieData movie_details;
    /* static SharedPreferences prefs;
    static boolean sortByFavorites;
    static boolean sortBywl;
    static boolean sortByPop = true;*/
    public MoviesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesArray = new ArrayList<>();
        moviesAdapter = new MovieAdapter(getContext(), moviesArray);


    }


    private void upDateMovies() {
        FetchMovieData fetchMovieData = new FetchMovieData(getContext(), moviesAdapter, gridView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_type = sharedPreferences.getString(getString(R.string.pref_sort_Key), getString(R.string.pref_sort_defaultValue));
        if (sort_type.equals("favorites")) {
            fetchMovieData.getFavouriteMovie();
        }
        else if(sort_type.equals("watchlater")){}
        else
            fetchMovieData.execute(sort_type);
    }

    @Override
    public void onStart() {
        super.onStart();
        upDateMovies();
       /* prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        if(prefs.getString("sortby","popularity").equals("popularity"))
        {
            getActivity().setTitle("Most Popular Movies");
            sortByPop = true;
            sortByFavorites=false;
            sortBywl=false;


        }

        else if(prefs.getString("sortby","popularity").equals("rating"))
        {
            getActivity().setTitle("Highest Rated Movies");
            sortByPop = false;
            sortByFavorites=false;
            sortBywl=false;

        }

        else if(prefs.getString("sortby","popularity").equals("favorites")) {

            getActivity().setTitle("Favorited Movies");
            sortByPop = false;
            sortByFavorites = true;
            sortBywl = false;
        }

        else if(prefs.getString("sortby","popularity").equals("watchlater")) {

            getActivity().setTitle("Watch Later Movies");
            sortByPop = false;
            sortByFavorites = false;
            sortBywl = true;
        }
*/
    }

    public interface  Callback{

         void onItemSelected(MovieData movieData);
    }
    /* public void Notconnected() {
         TextView txt = (TextView) getActivity().findViewById(R.id.txt);
         RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.relativelayout);
         gridView.setVisibility(GridView.GONE);
         txt.setText("You are not connected to the Internet");
         Resources res = getResources();
         txt.setTextColor(res.getColor(R.color.white));
         if (layout.getChildCount() == 1) {
             layout.addView(txt);
         }
     }*/
    /*public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null &&activeNetworkInfo.isConnected();
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movie_details = moviesAdapter.getItem(position);

                ((Callback) getActivity()).onItemSelected(movie_details);

            }
        });

        return rootView;
    }

    public class FetchMovieData extends AsyncTask<String, Void, MovieData[]> {

        private final String LOG_TAG = FetchMovieData.class.getSimpleName();
        private MovieAdapter moviesAdapter;
        private GridView gridView;
        private Context context;


        public FetchMovieData(Context context, MovieAdapter moviesAdapter, GridView gridView) {
            this.moviesAdapter = moviesAdapter;
            this.context = context;
            this.gridView = gridView;
        }

        @Override
        protected MovieData[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            try {


                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";


                Uri builduri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, params[0])
                        .appendQueryParameter(API_PARAM, BuildConfig.OPEN_MOVIES_APP_API_KEY)
                        .build();

                URL url = new URL(builduri.toString());
                Log.v(LOG_TAG, "Built URI " + builduri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }


                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }

                movieJsonStr = buffer.toString();

            } catch (IOException e) {

                Log.e(LOG_TAG, "error in connection ", e);
                return null;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();

                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "error in closing reader", e);
                    }

            }
            try {

                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        private MovieData[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {


            final String MOVIE_RESULT = "results";
            final String POSTER_PATH = "poster_path";
            final String MOVIE_TITLE = "title";
            final String MOVIE_ID = "id";
            final String MOVIE_AVERAGE = "vote_average";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_RELEASE_DATE = "release_date";
            MovieData[] movieData;

            JSONObject Json = new JSONObject(movieJsonStr);
            JSONArray result = Json.getJSONArray(MOVIE_RESULT);

            movieData = new MovieData[result.length()];


            for (int i = 0; i < result.length(); i++) {

                JSONObject jsonContainer = result.getJSONObject(i);
                movieData[i] = new MovieData();
                movieData[i].poster_path = jsonContainer.getString(POSTER_PATH);
                movieData[i].title = jsonContainer.getString(MOVIE_TITLE);
                movieData[i].id = jsonContainer.getInt(MOVIE_ID);
                movieData[i].overview = jsonContainer.getString(MOVIE_OVERVIEW);
                movieData[i].release_date = jsonContainer.getString(MOVIE_RELEASE_DATE);
                movieData[i].vote_average = jsonContainer.getDouble(MOVIE_AVERAGE);
            }
            return movieData;
        }

        public void getFavouriteMovie(){

            MovieData [] movieData ;

            FavoriteDBHelper fav= new FavoriteDBHelper(getActivity());
            movieData = fav.query(context);
            if(movieData != null)
                moviesAdapter.clear();
            for(MovieData md:movieData)
                moviesAdapter.add(md);
            moviesAdapter.notifyDataSetChanged();
            gridView.setAdapter(moviesAdapter);

        }
        @Override
        protected void onPostExecute(MovieData[] movieDatas) {
            super.onPostExecute(movieDatas);
            if (movieDatas != null) {
                moviesAdapter.clear();
                for (MovieData md : movieDatas)
                    moviesAdapter.add(md);
                moviesAdapter.notifyDataSetChanged();

                gridView.setAdapter(moviesAdapter);
            }


        }
    }



}
