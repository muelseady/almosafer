package com.arts.m3droid.samatravel.utils;

import android.widget.ImageView;

import com.arts.m3droid.samatravel.R;


public class ImageUtils {

    public static void setImageOnImageView(String url, ImageView imageView) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.special_card_holder)
                .error(R.drawable.special_card_holder)
                .into(imageView);
    }
}
