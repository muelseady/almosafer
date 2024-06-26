package com.arts.m3droid.samatravel.ui.userHistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.ui.userHistory.historyDetails.HistoryOfferDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryOffersAdapter.OnItemClicked {

    @BindView(R.id.rv_offers_history)
    RecyclerView rvOffersHistory;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        user = getIntent().getParcelableExtra(Constants.NODE_USERS);
        if (user.getGoinOnOffers() == null) {
            emptyView.setVisibility(View.VISIBLE);
            rvOffersHistory.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            rvOffersHistory.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        }
        setUpToolbar();
    }


    private void setUpRecyclerView() {
        rvOffersHistory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvOffersHistory.setLayoutManager(layoutManager);
        HistoryOffersAdapter adapter =
                new HistoryOffersAdapter(user.getGoinOnOffers(), this);
        rvOffersHistory.setAdapter(adapter);
    }


    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.txt_history));
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, HistoryOfferDetails.class);
        intent.putExtra(Constants.NODE_GOINGON_OFFERS, user.getGoinOnOffers().get(position));
        intent.putExtra(Constants.NODE_USERS, user);
        startActivity(intent);
    }
}
