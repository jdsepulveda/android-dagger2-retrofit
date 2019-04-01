package com.example.moviesapp.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moviesapp.model.Movie;
import com.example.moviesapp.model.MovieDBResponse;
import com.example.moviesapp.networking.APIService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> moviesLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final APIService apiService;

    private Call<MovieDBResponse> apiCall;

    @Inject
    public MoviesViewModel(APIService apiService) {
        this.apiService = apiService;
        fetchMovies();
    }

    private void fetchMovies() {
        loading.setValue(true);
        apiCall = apiService.getMovies();
        apiCall.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                moviesLoadError.setValue(false);
                movies.setValue(response.body().getMovies());
                loading.setValue(false);
                apiCall = null;
            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                moviesLoadError.setValue(true);
                loading.setValue(false);
                apiCall = null;
            }
        });
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getError() {
        return moviesLoadError;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @Override
    protected void onCleared() {
        if (apiCall != null) {
            apiCall.cancel();
            apiCall = null;
        }
    }
}