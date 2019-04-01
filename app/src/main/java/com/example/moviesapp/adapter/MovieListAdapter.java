package com.example.moviesapp.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.listener.MovieSelectedListener;
import com.example.moviesapp.model.Movie;
import com.example.moviesapp.viewModel.MoviesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private final List<Movie> movies = new ArrayList<>();
    private final MovieSelectedListener movieSelectedListener;

    public MovieListAdapter(MoviesViewModel viewModel, LifecycleOwner lifecycleOwner, MovieSelectedListener movieSelectedListener) {
        this.movieSelectedListener = movieSelectedListener;
        viewModel.getMovies().observe(lifecycleOwner, repos -> {
            movies.clear();
            if (repos != null) {
                movies.addAll(repos);
            }
            notifyDataSetChanged();
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_list_item, parent, false);
        return new MovieViewHolder(view, movieSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(movies.get(i));
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static final class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgMovie)
        ImageView imgMovie;
        @BindView(R.id.tvMovieTitle)
        TextView tvMovieTitle;
        private Movie movie;

        MovieViewHolder(@NonNull View itemView, final MovieSelectedListener movieSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (movie != null) {
                    movieSelectedListener.onMovieSelected(movie);
                }
            });
        }

        void bind(Movie movie) {
            this.movie = movie;
            tvMovieTitle.setText(movie.getTitle());
            String imgPath = "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();
            Picasso.get()
                    .load(imgPath)
                    .placeholder(R.color.white)
                    .into(imgMovie);
        }
    }
}