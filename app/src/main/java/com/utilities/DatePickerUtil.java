package com.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import com.lotus.R;

import java.util.Calendar;

/**
 * Created by ubuntu on 31/3/18.
 */

public class DatePickerUtil {


    /**
     * use for show date picker
     *
     * @param clickedView
     */
    public static void showDatePicker(Context context, final View clickedView,
                                      boolean isCurrentMin, final OnDateSelectedListener onDateSelectedListener) {

        Calendar previousSelectedCal = null;
        Calendar now = Calendar.getInstance();
        if (clickedView.getTag() != null) {
            previousSelectedCal = (Calendar) clickedView.getTag();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar now = Calendar.getInstance();
                        now.set(Calendar.YEAR, year);
                        now.set(Calendar.MONTH, month);
                        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        now.set(Calendar.HOUR_OF_DAY, 23);
                        now.set(Calendar.MINUTE, 59);
                        now.set(Calendar.SECOND, 59);
                        now.set(Calendar.MILLISECOND, 999);
                        if (onDateSelectedListener!=null)
                            onDateSelectedListener.onDateSelected(now);


                    }
                }, previousSelectedCal == null ? now.get(Calendar.YEAR) : previousSelectedCal.get(Calendar.YEAR),
                previousSelectedCal == null ? now.get(Calendar.MONTH) : previousSelectedCal.get(Calendar.MONTH),
                previousSelectedCal == null ? now.get(Calendar.DAY_OF_MONTH) : previousSelectedCal.get(Calendar.DAY_OF_MONTH));

        if (isCurrentMin) {
            datePickerDialog.getDatePicker().setMinDate(now.getTimeInMillis());
        }
        else {
            datePickerDialog.getDatePicker().setMaxDate(now.getTimeInMillis());
        }


        datePickerDialog.show();
    }

    public interface  OnDateSelectedListener{
        void onDateSelected(Calendar calendar);
    }
}
