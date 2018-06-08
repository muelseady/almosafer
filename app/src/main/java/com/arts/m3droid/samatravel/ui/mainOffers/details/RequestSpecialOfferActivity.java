package com.arts.m3droid.samatravel.ui.mainOffers.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.model.SpecialOfferRequest;
import com.arts.m3droid.samatravel.utils.DatePicker;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.arts.m3droid.samatravel.utils.Validator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestSpecialOfferActivity extends AppCompatActivity implements DatePicker.OnDatePickerDialogSet {


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
    @BindView(R.id.et_ppl_adults)
    EditText etPplAdults;
    @BindView(R.id.et_ppl_over65)
    EditText etPplOver65;
    @BindView(R.id.et_children)
    EditText etChildren;
    @BindView(R.id.et_infant)
    EditText etInfant;
    @BindView(R.id.et_quires)
    EditText etNotes;
    @BindView(R.id.btn_special_offer_request)
    ActionProcessButton btnSpecialOfferRequest;
    @BindView(R.id.emp_unique_num)
    EditText etEmpNum;

    private DatabaseReference reqSpecialOfferRef =
            FirebaseFactory.getDatabase().getReference(Constants.NODE_SPECIAL_OFFER_REQUEST);

    private SpecialOffer specialOffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_special_offer);
        ButterKnife.bind(this);


        specialOffer = getIntent().getParcelableExtra(Constants.DATA_SPECIAL_OFFER);

        setUpToolbar();

        btnSpecialOfferRequest.setOnClickListener(v -> submitButtonClicked());

        etDateFrom.setOnClickListener(v -> setUpDatePicker(true));
        etDateTo.setOnClickListener(v -> setUpDatePicker(false));
    }

    private void setUpDatePicker(Boolean toOrFrom) {
        DatePicker newFragment = new DatePicker();
        Bundle bundle = new Bundle();
        bundle.putBoolean("toOrFrom", toOrFrom);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void submitButtonClicked() {

        String warningMessage = Constants.REQUIRED_FIELD;
        String name = null;
        String number = null;
        String dateFrom = null;
        String dateTo = null;
        int adults = 0, over65 = 0, children = 0, infants = 0;
        String notes;

        if (Validator.validateETNotNull(etFirstName, warningMessage))
            name = extractTrimmedString(etFirstName);


        if (Validator.validateETNotNull(etSecondName, warningMessage))
            name = name + " " + extractTrimmedString(etSecondName);

        if (Validator.validateETNotNull(etThirdName, warningMessage))
            name = name + " " + extractTrimmedString(etThirdName);

        if (Validator.validateETNotNull(etNumber, warningMessage))
            number = extractTrimmedString(etNumber);

        if (Validator.validateETNotNull(etDateFrom, warningMessage))
            dateFrom = extractTrimmedString(etDateFrom);

        if (Validator.validateETNotNull(etDateTo, warningMessage))
            dateTo = extractTrimmedString(etDateTo);


        if (!extractTrimmedString(etPplAdults).isEmpty())
            adults = Integer.parseInt(extractTrimmedString(etPplAdults));


        if (!extractTrimmedString(etPplOver65).isEmpty())
            adults = Integer.parseInt(extractTrimmedString(etPplOver65));

        if (!extractTrimmedString(etChildren).isEmpty())
            adults = Integer.parseInt(extractTrimmedString(etChildren));

        if (!extractTrimmedString(etInfant).isEmpty())
            adults = Integer.parseInt(extractTrimmedString(etInfant));


        notes = extractTrimmedString(etNotes);

        SpecialOfferRequest specialOfferRequest =
                new SpecialOfferRequest(name, number,
                        dateFrom, dateTo, "ahmedsd1212",
                        adults, children, infants, over65,
                        notes, specialOffer.getName());

        if (etEmpNum.getText() != null)
            specialOfferRequest.setEmpPriKey(etEmpNum.getText().toString().trim());

        if (Validator.validateETHasError(etFirstName, etSecondName, etThirdName,
                etDateFrom, etDateTo,
                etNumber)) {

            reqSpecialOfferRef.push().setValue(specialOfferRequest);
            finish();

            Toast.makeText(getApplicationContext(), R.string.txt_offer_delivered, Toast.LENGTH_LONG).show();
        }

    }

    private String extractTrimmedString(EditText editText) {
        return editText.getText().toString().trim();
    }


    @Override
    public void onFromDateSet(int year, int month, int dayOdMonth) {
        String date = dayOdMonth + " / " + month + " / " + year;
        etDateFrom.setText(date);
    }

    @Override
    public void onToDateSet(int year, int month, int dayOdMonth) {
        String date = dayOdMonth + " / " + month + " / " + year;
        etDateTo.setText(date);
    }

    private void setUpToolbar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.txt_done_request);
    }
}
