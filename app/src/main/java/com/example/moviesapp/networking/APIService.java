package com.example.moviesapp.networking;

import com.example.moviesapp.model.MovieDBResponse;
import com.example.moviesapp.model.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    String ApiKey = "4afa87898b6b5c83294b554c53123f64";

    @GET("movie/popular?api_key=" + ApiKey)
    Call<MovieDBResponse> getMovies();

    @GET("movie/{movie_id}?api_key=" + ApiKey + "&language=en-US")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") String movieId);
}