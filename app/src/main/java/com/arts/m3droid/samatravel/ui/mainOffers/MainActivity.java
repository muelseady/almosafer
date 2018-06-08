package com.arts.m3droid.samatravel.ui.mainOffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.ui.callUs.CallUsActivity;
import com.arts.m3droid.samatravel.ui.customOffers.CustomOffersActivity;
import com.arts.m3droid.samatravel.ui.mainOffers.details.SpecialOffersDetailsActivity;
import com.arts.m3droid.samatravel.ui.userHistory.HistoryActivity;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        specialOffers = new ArrayList<>();

        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        setUpAnimations();
        setUpToolbar();
        loadData();

        tvCustomOffer.setOnClickListener(v -> startAnotherActivity(CustomOffersActivity.class));
        tvHistory.setOnClickListener(v -> startAnotherActivity(HistoryActivity.class));
        tvCallUs.setOnClickListener(v -> startAnotherActivity(CallUsActivity.class));
    }

    private void setUpAnimations() {
        YoYo.with(Techniques.Landing)
                .duration(2000)
                .playOn(rvSpecialOffers);
    }

    private void loadData() {
        specialOffersDatabaseRef =
                FirebaseFactory.getDatabase().getReference().child(Constants.NODE_SPECIAL_OFFERS);

        specialOffersDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                SpecialOffer specialOffer = dataSnapshot.getValue(SpecialOffer.class);
                specialOffers.add(specialOffer);
                setUpRecyclerView();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpRecyclerView() {
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
        Intent intent = new Intent(this, SpecialOffersDetailsActivity.class);
        intent.putExtra(Constants.DATA_SPECIAL_OFFER, specialOffers.get(position));

        startActivity(intent);
    }
}
