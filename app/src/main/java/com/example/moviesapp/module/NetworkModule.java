package com.example.moviesapp.module;

import com.example.moviesapp.networking.APIService;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkModule {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @Provides
    @Singleton
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static APIService provideRepoService(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }
}