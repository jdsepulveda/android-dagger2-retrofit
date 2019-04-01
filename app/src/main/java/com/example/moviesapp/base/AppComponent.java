package com.example.moviesapp.base;

import com.example.moviesapp.module.NetworkModule;
import com.example.moviesapp.module.ViewModelModule;
import com.example.moviesapp.view.MovieDetailActivity;
import com.example.moviesapp.view.MoviesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ViewModelModule.class})
public interface AppComponent {
    void inject(MoviesActivity moviesActivity);
    void inject(MovieDetailActivity movieDetailActivity);
}