package com.arts.m3droid.samatravel.ui.userHistory.historyDetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.RequestingOfferDetails;
import com.ramotion.foldingcell.FoldingCell;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class HistoryOfferDetails extends AppCompatActivity {

    @BindView(R.id.folding_cell)
    FoldingCell foldingCell;
    @BindView(R.id.iv_blured_background)
    ImageView ivBluredBackground;
    @BindView(R.id.tv_date_from)
    TextView tvDateFrom;
    @BindView(R.id.tv_date_to)
    TextView tvDateTo;
    @BindView(R.id.et_place_from)
    TextView etPlaceFrom;
    @BindView(R.id.et_place_to)
    TextView etPlaceTo;
    @BindView(R.id.tv_client_name)
    TextView tvClientName;
    @BindView(R.id.tv_client_number)
    TextView tvClientNumber;
    @BindView(R.id.iv_title_background)
    ImageView ivTitleBackground;
    @BindView(R.id.et_ppl_adults)
    TextView etPplAdults;
    @BindView(R.id.et_ppl_over65)
    TextView etPplOver65;
    @BindView(R.id.et_children)
    TextView etChildren;
    @BindView(R.id.et_infant)
    TextView etInfant;


    private RequestingOfferDetails offerDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        ButterKnife.bind(this);

        handleComingIntent();

    }

    private void handleComingIntent() {
        Intent intent = getIntent();
        offerDetails = intent.getParcelableExtra(Constants.NODE_GOINGON_OFFERS);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Blurry.with(this).from(bitmap).into(ivTitleBackground);

        setUpViews();
    }

    private void setUpViews() {
        String children = "الاطفال " + String.valueOf(offerDetails.getChildren());
        tvClientName.setText(offerDetails.getUserName());
        tvClientNumber.setText(offerDetails.getUserNumber());
        tvDateFrom.setText(offerDetails.getDateFrom());
        tvDateTo.setText(offerDetails.getDateTo());
        etPlaceFrom.setText(offerDetails.getPlaceFrom());
        etPlaceTo.setText(offerDetails.getPlaceTo());
        etPplAdults.setText(children);

        // attach click listener to folding cell
        foldingCell.setOnClickListener(v -> {
            foldingCell.toggle(false);
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.background);
            Blurry.with(HistoryOfferDetails.this).from(icon).into(ivBluredBackground);

        });


    }
}
