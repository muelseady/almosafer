package com.arts.m3droid.samatravel.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class SocialMediaButtonsHandler {

    public static void handleFb(Activity activity) {
        String FACEBOOK_URL = "https://www.facebook.com/سما-المسافر-للسفر-والسياحة-518141868537030";
        String FACEBOOK_PAGE_ID = "سما-المسافر-للسفر-والسياحة-518141868537030";

        String finalUrl = null;

//method to get the right URL to use in the intent
        PackageManager packageManager = activity.getPackageManager();
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
        activity.startActivity(facebookIntent);

    }

    public static void handleTwitter(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://Sama_Almosafer?lang=en"));
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/Sama_Almosafer?lang=en")));
        }
    }

    private void handleSnap() {
    }

    private void handleInsta() {
    }


}
