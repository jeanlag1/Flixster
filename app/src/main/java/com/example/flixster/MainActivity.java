package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String sNOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=ac613304e0d12cb1ed002f4c8e51e295";
    public static final String sTAG = "MainActivity";
    List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        mMovies = new ArrayList<>();
        //Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, mMovies);
        // set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);
        // Set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // API Request to get the list of movies
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(sNOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    mMovies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("TAG", "Hit json exceptionn", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
            }
        });
    }
}