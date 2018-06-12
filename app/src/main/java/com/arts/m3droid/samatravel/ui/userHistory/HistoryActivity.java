package com.arts.m3droid.samatravel.ui.userHistory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOfferRequest;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class HistoryActivity extends AppCompatActivity implements HistoryOffersAdapter.OnItemClicked {

    @BindView(R.id.rv_offers_history)
    RecyclerView rvOffersHistory;

    private User user;

    private DatabaseReference doneOfferReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        user = getIntent().getParcelableExtra(Constants.NODE_USERS);
        setUpRecyclerView();
        setUpToolbar();

        retrieveOffersData();
    }

    private void retrieveOffersData() {
        String userID = user.getUid();
        DatabaseReference goingOnReference = FirebaseFactory.
                getDatabase().
                getReference(Constants.NODE_USERS).
                child(userID).
                child(Constants.NODE_SPECIAL_OFFER_REQUEST);

        List<String> offersIds = new ArrayList<>();
        List<SpecialOfferRequest> offerRequested = new ArrayList<>();

        goingOnReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Timber.d("offer Ids " + childSnapShot.getKey());
//                    offersIds.add(String.valueOf(dataSnapshot.getValue()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference specialOffersRequests = FirebaseFactory.
                getDatabase().
                getReference(Constants.NODE_SPECIAL_OFFER_REQUEST);
        specialOffersRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    Timber.d("special offer req triggered %s ", childSnap.getKey());

                    for (int i = 0; i < offersIds.size(); i++) {
                        if (Objects.requireNonNull(childSnap.getKey()).equals(offersIds.get(i))) {
                            offerRequested.add(childSnap.getValue(SpecialOfferRequest.class));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setUpRecyclerView() {
        rvOffersHistory.setHasFixedSize(true);
        rvOffersHistory.setLayoutManager(new LinearLayoutManager(this));
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

    }
}
