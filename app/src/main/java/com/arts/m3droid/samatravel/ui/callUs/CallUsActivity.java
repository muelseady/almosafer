package com.arts.m3droid.samatravel.ui.callUs;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.arts.m3droid.samatravel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallUsActivity extends AppCompatActivity {


    @BindView(R.id.tv_first_number)
    TextView tvFirstNumber;
    @BindView(R.id.tv_second_number)
    TextView tvSecondNumber;
    @BindView(R.id.tv_third_number)
    TextView tvThirdNumber;
    @BindView(R.id.tv_fourth_number)
    TextView tvFourthNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);
        ButterKnife.bind(this);

        setUpToolbar();
        tvFirstNumber.setOnClickListener(v -> makeWhatsAppChat(getResources().getString(R.string.first_number)));
        tvSecondNumber.setOnClickListener(v -> makeWhatsAppChat(getResources().getString(R.string.second_number)));
        tvThirdNumber.setOnClickListener(v -> makeWhatsAppChat(getResources().getString(R.string.third_number)));
        tvFourthNumber.setOnClickListener(v -> makeWhatsAppChat(getResources().getString(R.string.fourth_number)));
    }


    private void makeWhatsAppChat(String number) {

        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
            startActivity(sendIntent);

        } catch (Exception e) {
            Toast.makeText(this, "Error opening whatsapp", Toast.LENGTH_LONG).show();
        }


    }


    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.txt_call_us));
    }
}
