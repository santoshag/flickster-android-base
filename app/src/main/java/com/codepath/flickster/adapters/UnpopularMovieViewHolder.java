package com.codepath.flickster.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;

/**
 * Created by santoshag on 7/22/16.
 */
public class UnpopularMovieViewHolder extends RecyclerView.ViewHolder {

    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    private TextView tvTitle;
    private TextView tvOverview;
    private ImageView ivMovieImage;

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvOverview() {
        return tvOverview;
    }

    public void setTvOverview(TextView tvOverview) {
        this.tvOverview = tvOverview;
    }

    public ImageView getIvMovieImage() {
        return ivMovieImage;
    }

    public void setIvMovieImage(ImageView ivMovieImage) {
        this.ivMovieImage = ivMovieImage;
    }

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public UnpopularMovieViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.

        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);

    }

}
