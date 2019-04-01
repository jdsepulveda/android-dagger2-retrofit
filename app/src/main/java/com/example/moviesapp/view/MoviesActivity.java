package com.example.moviesapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.MovieListAdapter;
import com.example.moviesapp.base.App;
import com.example.moviesapp.listener.MovieSelectedListener;
import com.example.moviesapp.model.Movie;
import com.example.moviesapp.viewModel.MoviesViewModel;
import com.example.moviesapp.viewModel.SelectedMovieViewModel;
import com.example.moviesapp.viewModel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity implements MovieSelectedListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.rvMoviesList)
    RecyclerView rvMoviesList;
    @BindView(R.id.tvErrorMsg)
    TextView tvErrorMsg;
    @BindView(R.id.loadingMovies)
    ProgressBar loadingMovies;

    private MoviesViewModel moviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        App.getAppComponent(this).inject(this);

        moviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);

        rvMoviesList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMoviesList.setAdapter((new MovieListAdapter(moviesViewModel, this, this)));
        rvMoviesList.setLayoutManager(new LinearLayoutManager(this));
        observeMoviesViewModel();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        Intent intent = new Intent(MoviesActivity.this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private void observeMoviesViewModel() {
        moviesViewModel.getMovies().observe(this, repos -> {
            if (repos != null) {
                rvMoviesList.setVisibility(View.VISIBLE);
            }
        });
        moviesViewModel.getError().observe(this, isError -> {
            if (isError) {
                tvErrorMsg.setVisibility(View.VISIBLE);
                rvMoviesList.setVisibility(View.GONE);
                tvErrorMsg.setText(R.string.api_error_movies);
            } else {
                tvErrorMsg.setVisibility(View.GONE);
                tvErrorMsg.setText(null);
            }
        });
        moviesViewModel.getLoading().observe(this, isLoading -> {
            loadingMovies.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                tvErrorMsg.setVisibility(View.GONE);
                rvMoviesList.setVisibility(View.GONE);
            }
        });
    }
}