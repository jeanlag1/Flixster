package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView mTvTitle;
    TextView mTvDate;
    TextView mTvLang;
    TextView mTvVotes;
    ImageView mImPoster;
    RatingBar mRbVoteAverage;
    TextView mTvOverview;
    ImageView mImTrailer;

    Movie mMovie;
    String mYoutubeId;

    private String VIDEOS_URL ="https://api.themoviedb.org/3/movie/%s/videos?api_key=ac613304e0d12cb1ed002f4c8e51e295" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTvTitle = findViewById(R.id.tvTitleD);
        mRbVoteAverage = findViewById(R.id.rbVoteAverage);
        mTvOverview = findViewById(R.id.tvOverviewD);
        mTvDate = findViewById(R.id.tvDate);
        mTvLang = findViewById(R.id.tvLang);
        mTvVotes = findViewById(R.id.tvVotes);
        mImPoster = findViewById(R.id.imPoster);
        mImTrailer = findViewById(R.id.imTrailer);

        //unwrap the movie passed in via intent
        mMovie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MDA", String.format("Showing details for '%s' ", mMovie.getTitle()));

        mTvTitle.setText(mMovie.getTitle());
        mTvOverview.setText(String.format("Description: %s",mMovie.getOverview()));
        mRbVoteAverage.setRating((float) (mMovie.getVoteAverage() / 2.0));
        mTvVotes.setText((String.format("%s votes", String.valueOf(mMovie.getVotes()))));
        mTvLang.setText(String.format("Original Language: %s" ,mMovie.getLang()));
        mTvDate.setText(String.format("Released: %s" ,mMovie.getDate()));
        Glide.with(this)
                .load(mMovie.getPosterPath())
                .placeholder(R.drawable.poster_placeholder)
                    .transform(new RoundedCornersTransformation(30,0))
                .into(mImPoster);
        Glide.with(this)
                .load(mMovie.getBackDropPath())
                .placeholder(R.drawable.poster_placeholder)
                .into(mImTrailer);

        // API Request to get info about a specific movie based on its id
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, mMovie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject trailer = (JSONObject) results.get(0);
                    mYoutubeId = trailer.getString("key");
                } catch (JSONException e) {
                    Log.e("ASYN", "Hit json exceptionn", e);
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });

        /* Listener for when the user click on the trailer.
        * This will direct it to MovieTrailerActivity
        * where they can watch the YouTube video
         */
        mImTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent for the new activity
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                // Pass in extra
                intent.putExtra("videoId", mYoutubeId);
                //show the activity
                startActivity(intent);
            }
        });
    }
}