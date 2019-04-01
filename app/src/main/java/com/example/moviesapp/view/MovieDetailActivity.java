package com.example.moviesapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.base.App;
import com.example.moviesapp.model.Genre;
import com.example.moviesapp.model.Movie;
import com.example.moviesapp.viewModel.SelectedMovieViewModel;
import com.example.moviesapp.viewModel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.imgMovieDetail)
    ImageView imgMovieDetail;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvGenre)
    TextView tvGenre;

    private SelectedMovieViewModel selectedMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        App.getAppComponent(this).inject(this);

        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            Movie movie = getIntent().getParcelableExtra("movie");

            selectedMovieViewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectedMovieViewModel.class);
            selectedMovieViewModel.setMovieId(movie.getId()+"");
            selectedMovieViewModel.restoreFromBundle(savedInstanceState);
            showMovieDetail();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        selectedMovieViewModel.saveToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    private void showMovieDetail() {
        selectedMovieViewModel.getSelectedMovie().observe(this, movie -> {
            String imgPath = "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();
            Picasso.get()
                    .load(imgPath)
                    .placeholder(R.color.white)
                    .into(imgMovieDetail);

            tvTitle.setText(movie.getOriginalTitle());

            String movieGenre = "";
            for (Genre g:movie.getGenres()) {
                movieGenre = movieGenre + g.getName() + " - ";
            }
            tvGenre.setText(movieGenre);
        });
    }
}