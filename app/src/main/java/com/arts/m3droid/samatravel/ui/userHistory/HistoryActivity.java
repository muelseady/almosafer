package com.arts.m3droid.samatravel.ui.userHistory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.arts.m3droid.samatravel.R;
import com.google.firebase.database.DatabaseReference;

public class HistoryActivity extends AppCompatActivity {

    private DatabaseReference goingOnReference;
    private DatabaseReference doneOffers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setUpToolbar();
    }


    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.txt_history));
    }
}
