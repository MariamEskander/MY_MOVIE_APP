package com.example.android.movie.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo 2 on 1/29/2016.
 */
public class VideoAdapter extends ArrayAdapter<VideoData> {


    private Context context ;
    private TextView textView;


    public VideoAdapter(Context context, ArrayList<VideoData> datas) {
        super(context,R.layout.video_item,datas);
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView ;
        if (item == null){

            item= LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
        }
        textView =(TextView)item.findViewById(R.id.text_view_for_video);
        final VideoData videoData = getItem(position);
        textView.setText(videoData.Name);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoData.key));
                context.startActivity(intent);
            }
        });

       /* imageView = (ImageView) convertView.findViewById(R.id.image_id);
        Drawable d =context.getResources().getDrawable(R.drawable.load);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" +movieData.poster_path).
                resize(400,600).placeholder(d).into(imageView);*/
        return item;
    }
}