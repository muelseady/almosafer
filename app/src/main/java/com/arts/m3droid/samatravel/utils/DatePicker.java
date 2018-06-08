package com.arts.m3droid.samatravel.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDatePickerDialogSet onDatePickerDialogSet;
    private Boolean toOrFrom = false;

    public interface OnDatePickerDialogSet {
        void onFromDateSet(int year, int month, int dayOdMonth);

        void onToDateSet(int year, int month, int dayOdMonth);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onDatePickerDialogSet = (OnDatePickerDialogSet) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        // Do something with the date chosen by the user
        if (getArguments() != null) {
            toOrFrom = getArguments().getBoolean("toOrFrom");
        }

        if (toOrFrom) {
            onDatePickerDialogSet.onFromDateSet(year, month +1 , dayOfMonth);
        } else {

            onDatePickerDialogSet.onToDateSet(year, month +1 , dayOfMonth);
        }

    }
}
