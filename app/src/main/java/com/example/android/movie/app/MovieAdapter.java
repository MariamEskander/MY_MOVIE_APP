package com.example.android.movie.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lenovo 2 on 1/25/2016.
 */
public class MovieAdapter extends ArrayAdapter<MovieData> {


    private ImageView  imageView;
    private Context context;


    public  MovieAdapter(Context context,ArrayList<MovieData> data){
        super(context,R.layout.movies_grid,data);
        this.context=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieData movieData = getItem(position);
        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.movies_grid, parent, false);
        }

        imageView = (ImageView) convertView.findViewById(R.id.image_id);
        Drawable d =context.getResources().getDrawable(R.drawable.load);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" +movieData.poster_path).
                resize(400,600).placeholder(d).into(imageView);
        return convertView;

    }


}
