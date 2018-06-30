package com.arts.m3droid.samatravel.ui.mainOffers.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.RequestingOffer;
import com.arts.m3droid.samatravel.model.RequestingOfferDetails;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.utils.DatePicker;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.arts.m3droid.samatravel.utils.Validator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.kingja.switchbutton.SwitchMultiButton;

public class RequestOfferActivity extends AppCompatActivity implements DatePicker.OnDatePickerDialogSet {

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
    @BindView(R.id.et_place_from)
    EditText etPlaceFrom;
    @BindView(R.id.spinner_to_countries)
    AppCompatSpinner spinnerPlaceTo;
    @BindView(R.id.custom_data_container)
    LinearLayout customDataContainer;
    @BindView(R.id.tv_if_invited)
    TextView tvIfInvited;
    @BindView(R.id.switch_pack)
    SwitchMultiButton switchPack;
    @BindView(R.id.switch_hotels)
    SwitchMultiButton switchHotels;
    @BindView(R.id.et_budget)
    EditText etBudget;
    @BindView(R.id.spinner_currency)
    AppCompatSpinner spinnerCurrency;

    private static final String warningMessage = Constants.REQUIRED_FIELD;

    private DatabaseReference userReference =
            FirebaseFactory.getDatabase().getReference().child(Constants.NODE_USERS);

    private DatabaseReference unAnsweredRequests =
            FirebaseFactory.getDatabase().getReference().child(Constants.UNANSWERED_OFFERS);

    private SpecialOffer specialOffer;
    private User user;

    private boolean currentOfferSpecial;

    private String employeeKey;

    private String hotel = Constants.HOTEL;
    private String pack = Constants.PACK_AND_TOURISM;
    private String currency = Constants.CURRENCY_DEFAULT;

    private String placeTo = "لم يحدد";
    private ArrayList<String> countries = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_offer);
        ButterKnife.bind(this);

        //Figures out if this activity opened from Custom order or special order
        currentOfferSpecial = !getIntent().getStringExtra(Constants.NODE_SPECIAL_OFFERS).
                equals(Constants.NODE_CUSTOM_OFFER);

        //Check if this activity opened as custom offer request or special offer
        if (currentOfferSpecial) {
            customDataContainer.setVisibility(View.GONE);
            specialOffer = getIntent().getParcelableExtra(Constants.DATA_SPECIAL_OFFER);
        } else {
            customDataContainer.setVisibility(View.VISIBLE);
            switchPack.setOnSwitchListener((position, tabText) -> pack = tabText);
            switchHotels.setOnSwitchListener((position, tabText) -> hotel = tabText);
            setUpCountriesSpinner();
            spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0)
                        currency = Constants.CURRENCY_DEFAULT;
                    if (position == 1)
                        currency = Constants.CURRENCY_DOLLAR;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinnerPlaceTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    placeTo = countries.get(position);
                    spinnerPlaceTo.setSelection(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    spinnerPlaceTo.setSelection(0);

                }
            });
        }

        user = getIntent().getParcelableExtra(Constants.NODE_USERS);
        extractUserName(user.getName());
        extractUserNumber(user.getNumber());

        setUpToolbar();

        btnSpecialOfferRequest.setOnClickListener(v -> submitButtonClicked());

        etDateFrom.setOnClickListener(v -> setUpDatePicker(true));
        etDateTo.setOnClickListener(v -> setUpDatePicker(false));
    }

    private void setUpCountriesSpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        countries); //selected item will look like a spinner set from XML

        FirebaseFactory.getDatabase().getReference("places")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        countries.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            countries.add(dataSnapshot1.getValue(String.class));
                        }

                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                        spinnerPlaceTo.setAdapter(spinnerArrayAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void setUpDatePicker(Boolean toOrFrom) {
        DatePicker newFragment = new DatePicker();
        Bundle bundle = new Bundle();
        bundle.putBoolean("toOrFrom", toOrFrom);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void submitButtonClicked() {

        String name = null, number = null, dateFrom = null, dateTo = null;
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
            over65 = Integer.parseInt(extractTrimmedString(etPplOver65));

        if (!extractTrimmedString(etChildren).isEmpty())
            children = Integer.parseInt(extractTrimmedString(etChildren));

        if (!extractTrimmedString(etInfant).isEmpty())
            infants = Integer.parseInt(extractTrimmedString(etInfant));

        notes = extractTrimmedString(etNotes);

        if (!etEmpNum.getText().toString().trim().equals(""))
            employeeKey = etEmpNum.getText().toString().trim();

        HandleDataIfSpecialNorCustom(name, number, dateFrom, dateTo, adults, over65,
                children, infants, notes);
    }

    private void HandleDataIfSpecialNorCustom(String name, String number, String dateFrom,
                                              String dateTo, int adults, int over65, int children,
                                              int infants, String notes) {
        RequestingOfferDetails offerRequest;

        if (currentOfferSpecial) { //Its indeed special offer
            offerRequest =
                    new RequestingOfferDetails(name, number,
                            dateFrom, dateTo, user.getUid(),
                            adults, children, infants, over65, notes,
                            specialOffer.getName(), specialOffer.getDetails(), specialOffer.getImageUrl());
            offerRequest.setUserIdToken(FirebaseInstanceId.getInstance().getToken());
        } else {//Custom offer so you need some more fields

            String placeFrom = null, budget = null;
            if (Validator.validateETNotNull(etPlaceFrom, warningMessage))
                placeFrom = extractTrimmedString(etPlaceFrom);
            if (Validator.validateETNotNull(etBudget, warningMessage))
                budget = extractTrimmedString(etBudget);

            offerRequest = new RequestingOfferDetails(name, number,
                    dateFrom, dateTo, user.getUid(),
                    adults, children, infants, over65, notes,
                    Constants.OFFER_CUSTOM, pack, hotel, placeFrom, placeTo, budget, currency);
            offerRequest.setUserIdToken(FirebaseInstanceId.getInstance().getToken());
        }

        if (currentOfferSpecial) {
            // If all needed data are filled done the needed pushing
            if (Validator.validateETHasError(etFirstName, etSecondName, etThirdName,
                    etDateFrom, etDateTo,
                    etNumber)) {
                pushTheValidatedData(offerRequest);
            }
        } else {
            if (Validator.validateETHasError(etFirstName, etSecondName, etThirdName,
                    etDateFrom, etDateTo,
                    etNumber, etPlaceFrom, etBudget)) {
                pushTheValidatedData(offerRequest);
            }
        }
    }

    private void pushTheValidatedData(RequestingOfferDetails specialOfferRequest) {

        //Populate the existing user with the new name and number to update his data
        user.setName(extractTrimmedString(etFirstName) + " " +
                extractTrimmedString(etSecondName) + " "
                + extractTrimmedString(etThirdName));
        user.setNumber(etNumber.getText().toString());

        //Push the updated data and the offer to be in the user node
        DatabaseReference currentUserRef = userReference.child(user.getUid());
        currentUserRef.child("name").setValue(user.getName());
        currentUserRef.child("number").setValue(user.getNumber());

        DatabaseReference currentUserGoingOffersRef =
                currentUserRef.child(Constants.NODE_GOINGON_OFFERS).push();
        String requestedOfferId = currentUserGoingOffersRef.getKey();
        currentUserGoingOffersRef.setValue(specialOfferRequest);

        RequestingOffer requestingOffer;
        if (currentOfferSpecial) {
            requestingOffer = createRequestingOffer
                    (specialOffer.getName(), requestedOfferId, specialOffer.getImageUrl());
        } else {
            requestingOffer = createRequestingOffer
                    ("عرض خاص ", requestedOfferId, null);
        }

        unAnsweredRequests.push().setValue(requestingOffer);

        Toast.makeText(getApplicationContext(), R.string.txt_offer_delivered, Toast.LENGTH_LONG).show();
        finish();
    }

    private RequestingOffer createRequestingOffer(String offerName, String requestedOfferId, String imageUrl) {
        return new RequestingOffer(user.getUid(),
                requestedOfferId,
                offerName,
                user.getName(),
                imageUrl,
                employeeKey);
    }


    //region String and date manipulating


    @NonNull
    private String extractTrimmedString(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void extractUserName(String name) {
        if (name == null) return;
        EditText[] editTexts = {etFirstName, etSecondName, etThirdName};
        String[] tripleName = name.split(" ");
        for (int i = 0; i < 3; i++) {

            editTexts[i].setText(tripleName[i]);
        }

    }

    private void extractUserNumber(String number) {
        if (number == null) return;
        etNumber.setText(number);
    }


    @Override
    public void onFromDateSet(int year, int month, int dayOdMonth) {
        String date = dayOdMonth + " / " + month + " / " + year;
        etDateFrom.setText(date);
        if (etDateFrom.getError() != null) {
            etDateFrom.setError(null);
        }
    }

    @Override
    public void onToDateSet(int year, int month, int dayOdMonth) {
        String date = dayOdMonth + " / " + month + " / " + year;
        etDateTo.setText(date);
        if (etDateTo.getError() != null) {
            etDateTo.setError(null);
        }
    }

    //endregion


    private void setUpToolbar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.txt_done_request);
    }
}
