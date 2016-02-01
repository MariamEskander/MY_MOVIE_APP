package com.example.android.movie.app;

/**
 * Created by lenovo 2 on 1/26/2016.
 */
public class FavoriteDB {

    private String overview;
    private String release_date;
    private String poster_path;
    private String title;
    private String vote_average;
    private int id;

    public FavoriteDB(){}
    public FavoriteDB(String overview,String release_date,String poster_path, String title,String vote_average,int id)
    {

        this.overview=overview;
        this.release_date=release_date;
        this.poster_path=poster_path;
        this.title=title;
        this.vote_average=vote_average;
        this.id=id;

    }
    public void setId(int id){
        this.id=id;
    }
    public void setOverview(String overview){
        this.overview=overview;
    }
    public void setRelease_date(String release_date){
        this.release_date=release_date;
    }
    public void setPoster_path(String poster_path){
        this.poster_path=poster_path;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setVote_average(String vote_average){
        this.vote_average=vote_average;
    }
    public String getOverview(){
        return overview;
    }
    public String getRelease_date(){
        return release_date;
    }
    public String getPoster_path(){
        return poster_path;
    }
    public String getVote_average(){
        return vote_average;
    }
    public String getTitle(){
        return title;
    }
    public int getId(){
        return id;
    }


}
