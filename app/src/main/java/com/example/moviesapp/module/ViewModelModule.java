package com.example.moviesapp.module;

import android.arch.lifecycle.ViewModel;

import com.example.moviesapp.viewModel.MoviesViewModel;
import com.example.moviesapp.viewModel.SelectedMovieViewModel;
import com.example.moviesapp.viewModel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedMovieViewModel.class)
    abstract ViewModel bindMovieDetailViewModel(SelectedMovieViewModel viewModel);
}