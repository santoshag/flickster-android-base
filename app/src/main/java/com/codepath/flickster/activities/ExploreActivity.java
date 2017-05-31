package com.codepath.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ExploreActivity extends YouTubeBaseActivity {

    Movie movie;
    TextView tvTitle, tvOverview, tvPopularity;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    private static String TAG = "ExploreActivity";
    private static String API_KEY = "AIzaSyAZlHKhCex4OpVSADaDv5ZHDHUTuEBs5MM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        Intent intent = getIntent();
        Long movieId = intent.getLongExtra("movie_id", -1);
        Log.i(TAG, "movie id: " + movieId);
        if(movieId != -1){
            getMovie(movieId);
        }
    }

    private void getMovie(Long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId +"?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        Log.i(TAG, url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.i(TAG, "title:" + response.getString("original_title"));
                    movie = new Movie(response.getString("original_title"), response.getString("overview"), response.getString("poster_path"), response.getString("backdrop_path"), response.getDouble("vote_average"), response.getInt("id"), response.getDouble("popularity"));
                    setLayoutFields();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setLayoutFields(){

        setYoutubePlayer();
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());
        tvPopularity.setText(String.format( "Popularity: %.2f", movie.getPopularity()));

        //Picasso.with(this).load(movie.getPosterPath()).placeholder(R.mipmap.ic_launcher).transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT)).into(ivMovieImage);
        ratingBar.setNumStars((int)Math.round(movie.getVoteAverage()) + 1);
        Log.i(TAG, "rating: " + movie.getVoteAverage() + " " + movie.getVoteAverage().floatValue());
        ratingBar.setStepSize((float)0.01);

        ratingBar.setRating(movie.getVoteAverage().floatValue());

    }

    private void setYoutubePlayer(){
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        youTubePlayerView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {

                        //get video trailer source
                        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() +"/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(url, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                JSONArray movieTrailerJsonResults = null;
                                try {
                                    movieTrailerJsonResults = response.getJSONArray("youtube");
                                    if(movieTrailerJsonResults.length()>0) {
                                        int randomTrailerId = randomWithRange(0, movieTrailerJsonResults.length()-1);
                                        JSONObject trailer = movieTrailerJsonResults.getJSONObject(randomTrailerId);
                                        String source = trailer.getString("source");
                                        youTubePlayer.cueVideo(source);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });


                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });
    }

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}
