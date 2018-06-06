package com.arts.m3droid.samatravel.ui.mainOffers.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.utils.ImageUtils;
import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SpecialOffersDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_offer_image)
    ImageView ivSpecialOffer;

    @BindView(R.id.btn_special_offer_request)
    ActionProcessButton btnRequestOffer;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.container_nested_scroll)
    NestedScrollView nestedScrollView;

    private SpecialOffer specialOffer;

    //Todo Use the image of the offer in scrollable toolbar && the title be the name of the offer

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offer_details);
        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());

        handleReceivedIntent();
        setUpToolbar();
        setUpRequestButton();

        nestedScrollView.setNestedScrollingEnabled(true);

        //https://www.facebook.com/سما-المسافر-للسفر-والسياحة-518141868537030/
        //https://twitter.com/search?src=typd&q=%40sama_almosafer&lang=en
    }

    private void setUpRequestButton() {
        btnRequestOffer.setMode(ActionProcessButton.Mode.ENDLESS);
        btnRequestOffer.setText(getResources().getText(R.string.txt_request_offer));
        btnRequestOffer.setProgress(75);
        btnRequestOffer.setOnClickListener(v -> {
            Intent intent = new Intent(this, RequestSpecialOfferActivity.class);
            intent.putExtra(Constants.DATA_SPECIAL_OFFER, specialOffer);
            startActivity(intent);
        });
    }

    private void handleReceivedIntent() {
        specialOffer = getIntent().getParcelableExtra(Constants.DATA_SPECIAL_OFFER);
        Timber.d("received %s", specialOffer.getName());
        ImageUtils.setImageOnImageView(specialOffer.getImageUrl(), ivSpecialOffer);
    }

    void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(specialOffer.getName());
    }
}
