package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel // indicates that the class is Parcelable
public class Movie {
    String mPosterPath;
    String mTitle;
    String mOverview;
    String mBackDropPath;
    Double mVoteAverage;
    String mLang;
    String mDate;
    Integer mVotes;
    Integer mId;

    //no-arg constructor required for Parceler
    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        mVotes = jsonObject.getInt("vote_count");
        mLang = jsonObject.getString("original_language");
        mDate = jsonObject.getString("release_date");
        mVoteAverage = jsonObject.getDouble("vote_average");
        mBackDropPath = jsonObject.getString("backdrop_path");
        mPosterPath = jsonObject.getString("poster_path");
        mTitle = jsonObject.getString("title");
        mOverview = jsonObject.getString("overview");
        mId = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> mMovies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            mMovies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return mMovies;
    }

    // Getters
    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",mBackDropPath);
    }
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",mPosterPath);
    }
    public String getTitle() {
        return mTitle;
    }
    public Integer getId() {
        return mId;
    }
    public String getOverview() {
        return mOverview;
    }
    public String getLang() {
        return mLang;
    }
    public Double getVoteAverage() { return mVoteAverage; }
    public String getDate() {
        return mDate;
    }
    public int getVotes() {
        return mVotes;
    }
}
