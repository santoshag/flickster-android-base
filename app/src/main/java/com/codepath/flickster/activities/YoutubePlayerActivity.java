package com.codepath.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codepath.flickster.R;
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

/**
 * Created by santoshag on 7/23/16.
 */
public class YoutubePlayerActivity extends YouTubeBaseActivity {
    private YouTubePlayerView youTubePlayerView;
    private static String TAG = "YoutubePlayerActivity";
    private static String API_KEY = "AIzaSyAZlHKhCex4OpVSADaDv5ZHDHUTuEBs5MM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Intent intent = getIntent();
        final Long movieId = intent.getLongExtra("movie_id", -1);
        Log.i(TAG, "movie id: " + movieId);
        if (movieId != -1) {
            youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
            youTubePlayerView.initialize(API_KEY,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            final YouTubePlayer youTubePlayer, boolean b) {

                            //get video trailer source
                            String url = "https://api.themoviedb.org/3/movie/" + movieId.toString() +"/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

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
                                            youTubePlayer.loadVideo(source);
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
        } else {
            finish();
        }
    }

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}
