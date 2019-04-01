package com.example.moviesapp.base;

import com.example.moviesapp.module.UserDataModule;
import com.example.moviesapp.view.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = UserDataModule.class)
public interface UserComponent {
    void inject(LoginActivity loginActivity);
}