package com.arts.m3droid.samatravel.utils;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseFactory {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
