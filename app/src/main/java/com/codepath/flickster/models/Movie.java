package com.codepath.flickster.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by santoshag on 7/19/16.
 */
public class Movie {

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public void setId(long id) {
        this.id = id;
    }

    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("original_language")
    String overview;
    @SerializedName("backdrop_path")
    String backdropPath;
    @SerializedName("vote_average")
    Double vote_average;
    @SerializedName("id")
    long id;
    @SerializedName("popularity")
    Double popularity;

    public long getId() {
        return id;
    }



    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1280/%s", backdropPath);
    }

    public Double getVoteAverage() {
        return vote_average;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Boolean isPopularMovie() {
        if(vote_average < 5.0)
            return false;
        return true;
    }



    public Movie(String originalTitle, String overview, String posterPath, String backdropPath, Double vote_average, long id, Double popularity){
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.vote_average = vote_average;
        this.id = id;
        this.popularity = popularity;
    }


    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.vote_average = jsonObject.getDouble("vote_average");
        this.id = jsonObject.getLong("id");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for(int x = 0; x < array.length(); x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
