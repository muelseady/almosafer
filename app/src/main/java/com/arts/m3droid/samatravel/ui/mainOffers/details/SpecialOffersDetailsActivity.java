package com.arts.m3droid.samatravel.ui.mainOffers.details;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.tv_offer_details)
    TextView tvOfferDetails;
    @BindView(R.id.iv_twitter)
    ImageView ivTwitter;
    @BindView(R.id.iv_fb)
    ImageView ivFb;
    @BindView(R.id.iv_instagram)
    ImageView ivInstagram;
    @BindView(R.id.iv_snap)
    ImageView ivSnap;

    private SpecialOffer specialOffer;

    //Todo When click on the icons of social medias links works xD

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

        ivFb.setOnClickListener(v -> handleFb());
        ivTwitter.setOnClickListener(v -> handleTwitter());

        //https://www.facebook.com/سما-المسافر-للسفر-والسياحة-518141868537030/
        //https://twitter.com/Sama_Almosafer?lang=en
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
        ImageUtils.setImageOnImageView(specialOffer.getImageUrl(), ivSpecialOffer);
        tvOfferDetails.setText(specialOffer.getDetails());
    }

    void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(specialOffer.getName());
    }

    private void handleFb() {
        String FACEBOOK_URL = "https://www.facebook.com/سما-المسافر-للسفر-والسياحة-518141868537030";
        String FACEBOOK_PAGE_ID = "سما-المسافر-للسفر-والسياحة-518141868537030";

        String finalUrl = null;

//method to get the right URL to use in the intent
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                finalUrl = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                finalUrl = "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            finalUrl = FACEBOOK_URL; //normal web url
        }

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = finalUrl;
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);

    }

    private void handleTwitter() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://Sama_Almosafer?lang=en"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/Sama_Almosafer?lang=en")));
        }
    }

    private void handleSnap() {
    }

    private void handleInsta() {
    }
}
