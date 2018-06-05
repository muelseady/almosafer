package com.arts.m3droid.samatravel.ui.mainOffers.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.utils.ImageUtils;
import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SpecialOffersDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_offer_image)
    ImageView ivSpecialOffer;

    @BindView(R.id.btn_special_offer_request)
    ActionProcessButton btnRequestOffer;

    //Todo Use the image of the offer in scrollable toolbar && the title be the name of the offer

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offer_details);
        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());

        setUpToolbar();
        handleReceivedIntent();
        setUpRequestButton();
    }

    private void setUpRequestButton() {
        btnRequestOffer.setMode(ActionProcessButton.Mode.ENDLESS);
        btnRequestOffer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnRequestOffer.setProgress(new Random(100).nextInt());
    }

    private void handleReceivedIntent() {

        SpecialOffer specialOffer = getIntent().getParcelableExtra(Constants.DATA_SPECIAL_OFFER);
        Timber.d("received %s", specialOffer.getName());

        ImageUtils.setImageOnImageView(specialOffer.getImageUrl(), ivSpecialOffer);
    }

    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(getIntent().getExtras().getString);
    }
}
