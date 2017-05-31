package com.codepath.flickster.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> newMovies = new ArrayList<Movie>();
        newMovies.addAll(movies);
        return newMovies;
    }

    // public constructor is necessary for collections
    public MovieResponse() {
        movies = new ArrayList<Movie>();
    }


    public static MovieResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
        return movieResponse;
    }

}

