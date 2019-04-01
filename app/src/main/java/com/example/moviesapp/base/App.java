package com.example.moviesapp.base;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private UserComponent userComponent;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        userComponent = DaggerUserComponent.create();
        appComponent = DaggerAppComponent.create();
    }

    public static UserComponent getUserComponent(Context context) {
        return ((App) context.getApplicationContext()).userComponent;
    }

    public static AppComponent getAppComponent(Context context) {
        return ((App) context.getApplicationContext()).appComponent;
    }
}