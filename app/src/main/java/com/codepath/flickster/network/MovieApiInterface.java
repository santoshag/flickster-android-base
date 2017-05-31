package com.codepath.flickster.network;

import com.codepath.flickster.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {

    @GET("{category}")
    Call<MovieResponse> getMovies(@Path("category") String category,
            @Query("api_key") String api_key);

}
