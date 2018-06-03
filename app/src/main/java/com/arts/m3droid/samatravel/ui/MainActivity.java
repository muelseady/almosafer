package com.arts.m3droid.samatravel.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.arts.m3droid.samatravel.R;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        setUpToolbar();
        setUpRecyclerView();

        tvCustomOffer.setOnClickListener(v -> startAnotherActivity(CustomOffersActivity.class));
        tvHistory.setOnClickListener(v -> startAnotherActivity(HistoryActivity.class));
        tvCallUs.setOnClickListener(v -> startAnotherActivity(CallUsActivity.class));
    }

    private void setUpRecyclerView() {
        rvSpecialOffers.setHasFixedSize(true);
        rvSpecialOffers.setLayoutManager(new LinearLayoutManager(this));
        rvSpecialOffers.setAdapter(new SpecialOffersAdapter(this));
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
