package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context mContext;
    List<Movie> mMovies;

    // Movie Adapter constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //Get the movie at the passed in position
        Movie movie = mMovies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvTitle;
        TextView mTvOverview;
        ImageView mIvPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvOverview = itemView.findViewById(R.id.tvOverview);
            mIvPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);

        }

        // Method allowing to bind a movie to the VH
        public void bind(Movie movie) {
            mTvOverview.setText(movie.getOverview());
            mTvTitle.setText(movie.getTitle());
            String imgUrl;
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imgUrl = movie.getBackDropPath();
            } else {
                imgUrl = movie.getPosterPath();
            }
            Glide.with(mContext)
                    .load(imgUrl)
                    .placeholder(R.drawable.poster_placeholder)
                    .transform(new RoundedCornersTransformation(30,10))
                    .into(mIvPoster);
        }

        // when the user clicks on a movie, go to MovieDetailsActivity
        @Override
        public void onClick(View v) {
            //get item position
            int position = getAdapterPosition();
            //get Movie at this position
            Movie movie = mMovies.get(position);
            // Create Intent for the new activity
            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
            // serialize the movie using parceler
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
            //show the activity
            mContext.startActivity(intent);
        }
    }
}
