package com.arts.m3droid.samatravel.ui.callUs;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.widget.Toast;

import com.arts.m3droid.samatravel.R;

public class CallUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);

        setUpToolbar();

        makeDirectCall();

        makeWhatsAppChat();
    }

    private void makeDirectCall() {
        String phone = "+34666777888";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    private void makeWhatsAppChat() {

        try {
            String number = "966 55 559 3814";

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
