package com.example.moviesapp.networking;

import com.example.moviesapp.model.MovieDBResponse;
import com.example.moviesapp.model.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    String ApiKey = "Personal API KEY";

    @GET("movie/popular?api_key=" + ApiKey)
    Call<MovieDBResponse> getMovies();

    @GET("movie/{movie_id}?api_key=" + ApiKey + "&language=en-US")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") String movieId);
}