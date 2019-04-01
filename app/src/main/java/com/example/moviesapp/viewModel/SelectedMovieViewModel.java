package com.example.moviesapp.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

import com.example.moviesapp.model.MovieDetail;
import com.example.moviesapp.networking.APIService;

import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedMovieViewModel extends ViewModel {
    private final MutableLiveData<MovieDetail> movieSelected = new MutableLiveData<>();
    private MutableLiveData<String> movieId = new MutableLiveData<>();
    private final APIService apiService;
    private Call<MovieDetail> apiCall;

    @Inject
    public SelectedMovieViewModel(APIService apiService) {
        this.apiService = apiService;
    }

    public LiveData<MovieDetail> getSelectedMovie() {
        return movieSelected;
    }

    public void setSelectMovie(MovieDetail movieDetail) {
        movieSelected.setValue(movieDetail);
    }

    public void setMovieId(String movie_Id) {
        movieId.setValue(movie_Id+"");
    }

    public void saveToBundle(Bundle outState) {
        if (movieSelected.getValue() != null) {
            outState.putString("movie_details", movieSelected.getValue().getId().toString());
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if (movieSelected.getValue() == null) {
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_details")) {
                loadMovie(Objects.requireNonNull(savedInstanceState.getString("repo_details")));
            } else {
                loadMovie(movieId.getValue());
            }
        }
    }

    private void loadMovie(String movieId) {
        apiCall = apiService.getMovieDetail(movieId);
        apiCall.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                movieSelected.setValue(response.body());
                apiCall = null;
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error getting movie details", t);
                apiCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (apiCall != null) {
            apiCall.cancel();
            apiCall = null;
        }
    }
}