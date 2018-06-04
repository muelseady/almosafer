package com.arts.m3droid.samatravel.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void setImageOnImageView(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
