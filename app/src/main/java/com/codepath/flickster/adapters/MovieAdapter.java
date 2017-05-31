package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.activities.YoutubePlayerActivity;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by santoshag on 7/19/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final String TAG = "MovieArrayAdapter";
        // Store a member variable for the movies
        private List<Movie> mMovies;

        private Context mContext;

        private final int POPULAR = 0, UNPOPULAR = 1;

    public Context getContext() {
        return mContext;
    }

    // Pass in the movie list into the constructor
    public MovieAdapter(Context context, List<Movie> movies){
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMovies.get(position).isPopularMovie()) {
            return POPULAR;
        } else {
            return UNPOPULAR;
        }
    }


    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case POPULAR:
                View v1 = inflater.inflate(R.layout.item_movie_popular, viewGroup, false);
                viewHolder = new PopularMovieViewHolder(v1);
                break;
            case UNPOPULAR:
                View v2 = inflater.inflate(R.layout.item_movie_unpopular, viewGroup, false);
                viewHolder = new UnpopularMovieViewHolder(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.item_movie_unpopular, viewGroup, false);
                viewHolder = new UnpopularMovieViewHolder(v);
                break;
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case POPULAR:
                PopularMovieViewHolder vh1 = (PopularMovieViewHolder) viewHolder;
                configurePopularMovieViewHolder(vh1, position);
                break;
            case UNPOPULAR:
                UnpopularMovieViewHolder vh2 = (UnpopularMovieViewHolder) viewHolder;
                configureUnpopularMovieViewHolder(vh2, position);
                break;
            default:
                PopularMovieViewHolder v = (PopularMovieViewHolder) viewHolder;
                configurePopularMovieViewHolder(v, position);
                break;
        }




    }


    private void configurePopularMovieViewHolder(PopularMovieViewHolder viewHolder, int position) {
        final Movie movie = (Movie) mMovies.get(position);

        if(movie != null) {
            // Set item views based on your views and data model
            ImageView ivMovieImage = viewHolder.getIvMovieImage();
            Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.placeholder).transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL)).into(ivMovieImage);

            // Set item views based on your views and data model
            int orientation = getContext().getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                TextView tvTitle = viewHolder.getTvTitle();
                tvTitle.setText(movie.getOriginalTitle());
                TextView tvOverview = viewHolder.getTvOverview();
                tvOverview.setText(movie.getOverview());
            }
            ImageView ivPlayIcon = viewHolder.getIvPlayIcon();

            ivPlayIcon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
                    intent.putExtra("movie_id", movie.getId());
                    getContext().startActivity(intent);
                }
            });
        }
    }

    private void configureUnpopularMovieViewHolder(UnpopularMovieViewHolder viewHolder, int position) {
        Movie movie = (Movie) mMovies.get(position);

        if(movie != null) {
            // Set item views based on your views and data model
            TextView tvTitle = viewHolder.getTvTitle();
            tvTitle.setText(movie.getOriginalTitle());
            TextView tvOverview = viewHolder.getTvOverview();
            tvOverview.setText(movie.getOverview());

            String imagePath = new String();
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imagePath = movie.getPosterPath();

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imagePath = movie.getBackdropPath();
            }

            ImageView ivMovieImage = viewHolder.getIvMovieImage();
            Picasso.with(getContext()).load(imagePath).fit().centerInside().placeholder(R.drawable.placeholder).transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL)).into(ivMovieImage);

        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}