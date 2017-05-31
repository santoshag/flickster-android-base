package com.codepath.flickster.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    // public constructor is necessary for collections
    public MovieResponse() {
        movies = new ArrayList<Movie>();
    }


}

