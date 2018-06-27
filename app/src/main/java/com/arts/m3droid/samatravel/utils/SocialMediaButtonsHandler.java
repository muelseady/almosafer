package com.arts.m3droid.samatravel.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

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
        if (facebookIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(facebookIntent);
        } else {
            Toast.makeText(activity, "برجاء التأكد من وجود برنامج facebook", Toast.LENGTH_SHORT).show();
        }

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

    public static void handleSnap(Activity activity) {
        Intent nativeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.snapchat.com/add/sama_almosafer"));
        activity.startActivity(nativeAppIntent);
        activity.startActivity(Intent.createChooser(nativeAppIntent, "برجاء تثبيت برنامج سناب شات اولا"));
    }

    public static void handleInsta(Activity activity) {
        String INSTA_PAGE = "https://www.instagram.com/sama_almosafer";

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (activity.getPackageManager().getPackageInfo("com.instagram.android", 0) != null) {

                final String username = INSTA_PAGE.substring(INSTA_PAGE.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                activity.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(INSTA_PAGE));

        activity.startActivity(intent);
    }


}
