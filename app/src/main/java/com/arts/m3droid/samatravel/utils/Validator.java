package com.arts.m3droid.samatravel.utils;

import android.widget.EditText;

public class Validator {
    public static boolean validateETNotNull(EditText editText, String warningMessage) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(warningMessage);
            return false;
        }
        return true;
    }


    public static boolean validateETHasError(EditText... editText) {

        for (EditText text : editText) {
            if (text.getError() != null) {
                return false;
            }
        }
        return true;
    }


}
