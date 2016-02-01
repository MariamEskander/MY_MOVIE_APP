package com.example.android.movie.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

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
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
   /* public static String overview;
    public static double rating;
    public static String date;
    public static String title;
    public static String poster;
    public static int id;;*/
    private static int id;
    private ImageView poster;
    private TextView release_date;
    private TextView  overview;
    private TextView  title;
    private TextView vote_average;
    private ToggleButton add;
    private ListView ListForVideos;
    private VideoAdapter VideoAdapter;
    private static ListView ListForReviews;
    private static ArrayAdapter<String> adapterReviews;
    private static boolean testToggleButton;
    public static MovieData movieData;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateVideoReview() {
        FetchvideoData fetchVedioData = new FetchvideoData(getContext(), ListForVideos, VideoAdapter);
        FetchReviews fetchReviewsData = new FetchReviews(getContext(), ListForReviews, adapterReviews);
        fetchVedioData.execute(id);
        fetchReviewsData.execute(id);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sharedPreferences.getString(getString(R.string.pref_sort_Key), getString(R.string.pref_sort_defaultValue)).equals("favorites"))
        {}
        else{
         updateVideoReview();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_activity, container, false);

        poster= (ImageView)rootView.findViewById(R.id.poster);
        overview=(TextView) rootView.findViewById(R.id.overview);
        release_date=(TextView) rootView.findViewById(R.id.date);
        title = (TextView) rootView.findViewById(R.id.title);
        vote_average=(TextView) rootView.findViewById(R.id.rating);
        ListForVideos=(ListView) rootView.findViewById(R.id.list_view_for_videos);
        ListForReviews=(ListView) rootView.findViewById(R.id.reviews);

        /*VideoAdapter= new VideoAdapter(getContext(),new ArrayList<VideoData>());
        adapterReviews = new ArrayAdapter<String>(getContext(),R.layout.review_item,R.id.text_view_reviews);
        ListForReviews=(ListView) rootView.findViewById(R.id.reviews);
        ListForVideos=(ListView) rootView.findViewById(R.id.list_view_for_videos);*/
        movieData = new MovieData();

        final  Bundle arguments = getArguments();
        if (arguments !=null ){

            movieData.poster_path=arguments.getString("poster_path");
            movieData.vote_average=arguments.getDouble("vote_average");
            movieData.release_date=arguments.getString("release_date");
            movieData.overview=arguments.getString("overView");
            movieData.title=arguments.getString("title");
            movieData.id=arguments.getInt("id");


//            if(arguments.getBoolean("testTwoPane")) {

            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+arguments.getString("poster_path")).resize(150,300).into(poster);

            title.setText(arguments.getString("title"));
            release_date.setText(arguments.getString("release_date").substring(0, 4));
            vote_average.setText(Double.toString(arguments.getDouble("vote_average"))+ "/10");
            overview.setText(arguments.getString("overView"));
            id=arguments.getInt("id");
            testToggleButton = arguments.getBoolean("toggleButton");


        }

        updateVideoReview();
/*
        if(testToggleButton){

            add.setChecked(true);

            add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!isChecked) {
                        FavoriteDBHelper fav = new FavoriteDBHelper(getActivity());
                        fav.UnFavorited(arguments.getString("title"));
                        //((CallBackToMainFromDetails) getActivity()).updateFragmentMain();

                        Toast.makeText(getContext(),movieData.title+
                                " deleted from your favourite",Toast.LENGTH_LONG).show();
                    }
                    if (isChecked){
                        FavoriteDBHelper fav = new FavoriteDBHelper(getActivity());
                        fav.AddFavorite();
                        //((CallBackToMainFromDetails) getActivity()).updateFragmentMain();
                        Toast.makeText(getContext(),movieData.title+ " added to your favourite",Toast.LENGTH_LONG).show();
                    }

                }
            });


        }       else {

            add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        Operation operation = new Operation();
                        operation.insert(movieData, getContext());
                        //  ((CallBackToMainFromDetails) getActivity()).updateFragmentMain();
                        Toast.makeText(getContext(), movieData.title + " added to your favourite", Toast.LENGTH_LONG).show();
                    }
                    if (!isChecked){
                        Operation operation = new Operation();
                        operation.delete(getContext(), movieData.id);

                        //((CallBackToMainFromDetails) getActivity()).updateFragmentMain();

                        Toast.makeText(getContext(), movieData.title + " deleted from your favourite", Toast.LENGTH_LONG).show();

                    }

                }
            });*/

       /* Intent intent = getActivity().getIntent();
        if(intent !=null && intent.hasExtra("title"))
        {
            title = intent.getStringExtra("title");
            TextView tv = (TextView) rootView.findViewById(R.id.title);
            tv.setText(title);

        }
        if(intent !=null && intent.hasExtra("poster_path"))
        {
            poster = intent.getStringExtra("poster_path");
            ImageView iv = (ImageView) rootView.findViewById(R.id.poster);

            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+ poster).into(iv);

        }
       if(intent !=null && intent.hasExtra("overView"))
        {
            overview = intent.getStringExtra("overView");
            TextView tv = (TextView) rootView.findViewById(R.id.overview);
            tv.setText(overview);

        }
        if(intent !=null && intent.hasExtra("vote_average"))
        {
            rating = intent.getDoubleExtra("vote_average", 0.0);
            TextView tv = (TextView) rootView.findViewById(R.id.rating);
            String rate =Double.toString(rating)+"/10";
            tv.setText(rate);
        }
        if(intent !=null && intent.hasExtra("release_date"))
        {
            date = intent.getStringExtra("release_date");
            TextView tv = (TextView) rootView.findViewById(R.id.date);
            tv.setText(date);

        }
        if(intent !=null && intent.hasExtra("id"))
        {
            id = intent.getIntExtra("id", 1);}*/
        VideoAdapter = new VideoAdapter(getContext(), new ArrayList<VideoData>());
        adapterReviews = new ArrayAdapter<String>(getContext(),R.layout.review_item,R.id.text_view_reviews);


        return rootView;
    }

    public class FetchReviews extends AsyncTask<Integer,Void,String[]> {

        private Context context;
        private ListView listView;
        private ArrayAdapter<String> arrayAdapter;
        private  String ReviejsonString;
        private  String  Log_cat =FetchReviews.class.getSimpleName();
        public FetchReviews(Context context,ListView listView ,ArrayAdapter<String> arrayAdapter)
        {
            this.context = context;
            this.listView= listView;
            this.arrayAdapter=arrayAdapter;

        }


        @Override
        protected String[] doInBackground(Integer... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            ReviejsonString = null;


            if(params.length == 0) {
                return null;
            }
            try {

                String BaseURI = "http://api.themoviedb.org/3/movie/"+id+"/reviews";
                String API_PARAM = "api_key";
                Uri builtUri = Uri.parse(BaseURI).buildUpon()
                        .appendQueryParameter(API_PARAM, BuildConfig.OPEN_MOVIES_APP_API_KEY).build();
                URL url = new URL(builtUri.toString());
                Log.i(Log_cat, "Uri id : " + builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();


                if(inputStream == null)
                    return  null ;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line ;
                while((line = reader.readLine())!=null){

                    buffer.append(line+"\n");
                }

                if(buffer.length() ==0)
                    return  null;



                ReviejsonString = buffer.toString();



            }catch (IOException e){
                Log.e(Log_cat,"error in connection ",e);
                return null;

            }
            finally {
                if(urlConnection !=null)
                    urlConnection.disconnect();

                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(Log_cat,"error in closing reader",e);
                    }

            }
            try {
                return  getReviwsDataFromJson(ReviejsonString);

            }  catch (JSONException e){


            }





            return null;
        }

        private String[] getReviwsDataFromJson(String ReviejsonString) throws  JSONException{

            String [] resultReviews ;
            final String  results ="results";
            final String content="content";

            JSONObject json = new JSONObject(ReviejsonString);
            JSONArray results_from_json_string  = json.getJSONArray(results);

            resultReviews = new String[results_from_json_string.length()];
            for (int i=0;i<results_from_json_string.length();i++) {

                JSONObject jsonItem = results_from_json_string.getJSONObject(i);
                resultReviews[i] =jsonItem.getString(content);
            }






            return resultReviews;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

            if (strings!= null)
                arrayAdapter.clear();
            arrayAdapter.addAll(strings);

            listView.setAdapter(arrayAdapter);
        }
    }

    public class FetchvideoData extends AsyncTask<Integer ,Void,VideoData[]> {

        private final String Log_cat= FetchvideoData.class.getSimpleName();
        private  Context context;
        private   String jsonString;
        private  ListView listView ;
        private  VideoAdapter adapter ;
        public  FetchvideoData(Context c ,ListView listView ,VideoAdapter adapter){
            this.context =c;
            this.listView=listView;
            this.adapter=adapter;

        }

        @Override
        protected VideoData[] doInBackground(Integer... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            jsonString = null;


            if(params.length == 0) {
                return null;
            }
            try {

                String BaseURI = "http://api.themoviedb.org/3/movie/"+id+"/videos";
                String API_PARAM = "api_key";
                Uri builtUri = Uri.parse(BaseURI).buildUpon()
                        .appendQueryParameter(API_PARAM, BuildConfig.OPEN_MOVIES_APP_API_KEY).build();
                URL url = new URL(builtUri.toString());
                Log.i(Log_cat, "Uri id : " + builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();


                if(inputStream == null)
                    return  null ;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line ;
                while((line = reader.readLine())!=null){

                    buffer.append(line+"\n");
                }

                if(buffer.length() ==0)
                    return  null;



                jsonString = buffer.toString();



            }catch (IOException e){
                Log.e(Log_cat,"error in connection ",e);
                return null;

            }
            finally {
                if(urlConnection !=null)
                    urlConnection.disconnect();

                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(Log_cat,"error in closing reader",e);
                    }

            }
            try {
                return  getVideoDataFromJson(jsonString);

            }  catch (JSONException e){


            }
            return null;
        }

        private VideoData[] getVideoDataFromJson(String jsonString) throws  JSONException{
            VideoData[]  video  ;
            final String  results ="results";
            final String ID="id";
            final String KEY="key";
            final String NAME="name";
            final String SITE="site";
            final String TYPE="type";
            final String SIZE="size";

            JSONObject json = new JSONObject(jsonString);
            JSONArray results_from_json_string  = json.getJSONArray(results);
            video = new VideoData[results_from_json_string.length()];
            for (int i=0;i<results_from_json_string.length();i++) {

                JSONObject jsonItem = results_from_json_string.getJSONObject(i);
                video[i] = new VideoData();

                video[i].Id = jsonItem.getString(ID);
                video[i].key = jsonItem.getString(KEY);
                video[i].Name = jsonItem.getString(NAME);
                video[i].site = jsonItem.getString(SITE);
                video[i].type = jsonItem.getString(TYPE);
                video[i].size = jsonItem.getInt(SIZE);

            }


            return  video;
        }

        @Override
        protected void onPostExecute(final VideoData[] videoData) {
            super.onPostExecute(videoData);
            if (videoData != null)
            { adapter.clear();
               for (VideoData vd : videoData)
            adapter.add(vd);
            listView.setAdapter(adapter);
            listView.setScrollContainer(false);}
        }
    }

}

