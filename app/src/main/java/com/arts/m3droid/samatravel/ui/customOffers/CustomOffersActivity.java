package com.arts.m3droid.samatravel.ui.customOffers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arts.m3droid.samatravel.R;
import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomOffersActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_second_name)
    EditText etSecondName;
    @BindView(R.id.et_third_name)
    EditText etThirdName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_date_from)
    EditText etDateFrom;
    @BindView(R.id.et_date_to)
    EditText etDateTo;
    @BindView(R.id.et_place_from)
    EditText etPlaceFrom;
    @BindView(R.id.et_place_to)
    EditText etPlaceTo;
    @BindView(R.id.et_ppl_adults)
    EditText etPplAdults;
    @BindView(R.id.et_ppl_over65)
    EditText etPplOver65;
    @BindView(R.id.et_children)
    EditText etChildren;
    @BindView(R.id.et_infant)
    EditText etInfant;
//    @BindView(R.id.radio_btn_pack)
//    RadioButton radioBtnPack;
//    @BindView(R.id.radio_btn_air)
    RadioButton radioBtnAir;
    @BindView(R.id.et_quires)
    EditText etQuires;
    @BindView(R.id.tv_if_invited)
    TextView tvIfInvited;
    @BindView(R.id.emp_unique_num)
    EditText empUniqueNum;
    @BindView(R.id.btn_special_offer_request)
    ActionProcessButton btnSpecialOfferRequest;
    @BindView(R.id.container)
    ScrollView container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_offers);
        ButterKnife.bind(this);
//
//        radioBtnPack.setOnClickListener(v -> {
//            radioBtnAir.setChecked(false);
//        radioBtnPack.setChecked(true);});
//        radioBtnAir.setOnClickListener(v -> {
//            radioBtnPack.setChecked(false);
//            radioBtnAir.setChecked(true);});

        setUpToolbar();
    }

    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.txt_custom_offers));
    }

}
