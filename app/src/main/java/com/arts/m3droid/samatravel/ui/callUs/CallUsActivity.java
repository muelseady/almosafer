package com.arts.m3droid.samatravel.ui.callUs;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arts.m3droid.samatravel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleFb;
import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleInsta;
import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleSnap;
import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleTwitter;

public class CallUsActivity extends AppCompatActivity {


    @BindView(R.id.tv_first_number)
    TextView tvFirstNumber;
    @BindView(R.id.tv_second_number)
    TextView tvSecondNumber;
    @BindView(R.id.tv_third_number)
    TextView tvThirdNumber;
    @BindView(R.id.tv_fourth_number)
    TextView tvFourthNumber;
    @BindView(R.id.iv_twitter)
    ImageView ivTwitter;
    @BindView(R.id.iv_fb)
    ImageView ivFb;
    @BindView(R.id.iv_instagram)
    ImageView ivInstagram;
    @BindView(R.id.iv_snap)
    ImageView ivSnap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);
        ButterKnife.bind(this);

        handleSocialMediaButtons();

        setUpToolbar();
        tvFirstNumber.setOnClickListener(v -> makeWhatsAppChat(getResources().getString(R.string.first_number)));
        tvSecondNumber.setOnClickListener(v -> makeWhatsAppChat(getResources().getString(R.string.second_number)));
        tvThirdNumber.setOnClickListener(v -> dailNumber(getResources().getString(R.string.third_number)));
        tvFourthNumber.setOnClickListener(v -> dailNumber(getResources().getString(R.string.fourth_number)));
    }

    private void dailNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
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

    private void handleSocialMediaButtons() {
        //Social media icons listeners
        ivFb.setOnClickListener(v -> handleFb(this));
        ivTwitter.setOnClickListener(v -> handleTwitter(this));
        ivInstagram.setOnClickListener(v -> handleInsta(this));
        ivSnap.setOnClickListener(v -> handleSnap(this));
    }


    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.txt_call_us));
    }
}
