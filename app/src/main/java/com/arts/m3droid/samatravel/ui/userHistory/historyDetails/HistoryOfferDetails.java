package com.arts.m3droid.samatravel.ui.userHistory.historyDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.RequestingOfferDetails;
import com.ramotion.foldingcell.FoldingCell;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryOfferDetails extends AppCompatActivity {

    @BindView(R.id.folding_cell)
    FoldingCell foldingCell;
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
    @BindView(R.id.ppl_count)
    TextView tvPplCount;


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
        setUpViews();
    }

    private void setUpViews() {
        tvClientName.setText(offerDetails.getUserName());
        tvClientNumber.setText(offerDetails.getUserNumber());
        tvDateFrom.setText(offerDetails.getDateFrom());
        tvDateTo.setText(offerDetails.getDateTo());
        tvPplCount.setText(String.valueOf(pplCount()));
        if (offerDetails.getPlaceFrom() == null) {
            String placeFrom = "بلدك";
            etPlaceFrom.setText(placeFrom);
            etPlaceTo.setText(offerDetails.getOfferName());
        } else {
            etPlaceFrom.setText(offerDetails.getPlaceFrom());
            etPlaceTo.setText(offerDetails.getPlaceTo());
        }

        // attach click listener to folding cell
        foldingCell.setOnClickListener(v -> foldingCell.toggle(false));
    }

    private int pplCount() {
        return offerDetails.getChildren() + offerDetails.getAdults() +
                offerDetails.getInfants() + offerDetails.getOver65();
    }
}
