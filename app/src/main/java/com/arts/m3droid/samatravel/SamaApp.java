package com.arts.m3droid.samatravel;

import android.app.Application;

import timber.log.Timber;

public class SamaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
