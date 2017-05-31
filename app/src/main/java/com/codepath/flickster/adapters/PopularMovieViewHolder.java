package com.codepath.flickster.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;

/**
 * Created by santoshag on 7/22/16.
 */
public class PopularMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    private ImageView ivMovieImage;

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvOverview() {
        return tvOverview;
    }

    private TextView tvTitle;
    private TextView tvOverview;

    public ImageView getIvPlayIcon() {
        return ivPlayIcon;
    }

    public void setIvPlayIcon(ImageView ivPlayIcon) {
        this.ivPlayIcon = ivPlayIcon;
    }

    private ImageView ivPlayIcon;

    public ImageView getIvMovieImage() {
        return ivMovieImage;
    }

    public void setIvMovieImage(ImageView ivMovieImage) {
        this.ivMovieImage = ivMovieImage;
    }

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public PopularMovieViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);
        ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
        ivPlayIcon = (ImageView) itemView.findViewById(R.id.ivPlayIcon);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        itemView.setOnClickListener(this);

    }

    // Handles the row being being clicked
    @Override
    public void onClick(View view) {
        int position = getLayoutPosition(); // gets item position
        // We can access the data within the views
    }

}
