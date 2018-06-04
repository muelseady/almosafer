package com.arts.m3droid.samatravel.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements SpecialOffersAdapter.OnItemClicked {

    @BindView(R.id.drawerlayout)
    FlowingDrawer mDrawer;

    @BindView(R.id.rv_special_offers)
    RecyclerView rvSpecialOffers;
    @BindView(R.id.tv_custom_offer)
    TextView tvCustomOffer;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.tv_call_us)
    TextView tvCallUs;

    private DatabaseReference specialOffersDatabaseRef;
    private List<SpecialOffer> specialOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        specialOffers = new ArrayList<>();

        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        setUpToolbar();
        loadData();

        tvCustomOffer.setOnClickListener(v -> startAnotherActivity(CustomOffersActivity.class));
        tvHistory.setOnClickListener(v -> startAnotherActivity(HistoryActivity.class));
        tvCallUs.setOnClickListener(v -> startAnotherActivity(CallUsActivity.class));
    }

    private void loadData() {
        specialOffersDatabaseRef =
                FirebaseFactory.getDatabase().getReference().child(Constants.NODE_SPECIAL_OFFERS);

        specialOffersDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SpecialOffer specialOffer = dataSnapshot1.getValue(SpecialOffer.class);
                    Timber.d("special offer " + specialOffer.getName());
                    specialOffers.add(specialOffer);
                }
                setUpRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setUpRecyclerView() {
        Timber.d("special offers array " + specialOffers.get(0).getImageUrl());
        rvSpecialOffers.setHasFixedSize(true);
        rvSpecialOffers.setLayoutManager(new LinearLayoutManager(this));
        rvSpecialOffers.setAdapter(new SpecialOffersAdapter(specialOffers, this));
    }

    void startAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(getString(R.string.txt_special_offer));
        actionBar.setHomeAsUpIndicator(R.drawable.menu_drawer_icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            mDrawer.toggleMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {

    }
}
