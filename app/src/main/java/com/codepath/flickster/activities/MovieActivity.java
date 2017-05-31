package com.codepath.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MovieAdapter;
import com.codepath.flickster.decorators.ItemClickSupport;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.MovieResponse;
import com.codepath.flickster.network.MovieApiInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieActivity extends AppCompatActivity {
    private static String TAG = "MovieActivity";
    private ArrayList<Movie> movies;
    private SwipeRefreshLayout swipeContainer;
    MovieAdapter adapter;
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        RecyclerView rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        adapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(rvMovies).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Movie movie = movies.get(position);
                        Intent intent = new Intent(MovieActivity.this, ExploreActivity.class);
                        intent.putExtra("movie_id", movie.getId());
                        startActivity(intent);
                    }
                }
        );
        setupSwipeToRefreshView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getMovies();
    }

    private void getMovies() {
        //Network Debugging interceptor
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApiInterface apiService =
                retrofit.create(MovieApiInterface.class);
        String api_key = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
        String category = "now_playing";
        Call<MovieResponse> call = apiService.getMovies(category, api_key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                List<Movie> newMovies = movieResponse.getMovies();
                movies.addAll(newMovies);
                adapter.notifyDataSetChanged();
                for (Movie movie : movies) {
                    Log.i("SANT", movie.getOriginalTitle());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.i(TAG, "FAILURE from network call");
                // Log error here since request failed
            }
        });
    }

    private void setupSwipeToRefreshView() {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                getMovies();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

}
