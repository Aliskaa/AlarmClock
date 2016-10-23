package com.example.lucky.alarmclock;

/**
 * Created by lucky on 28/09/16.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class MyDialogFragment extends DialogFragment {

    private int dateDay;
    private int dateMonth;
    private int dateYear;
    private int timeHour;
    private int timeMinute;
    private Handler handler;
    private String type;

    public MyDialogFragment(Handler handler, String s){

        this.handler = handler;
        this.type = s;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        dateDay = bundle.getInt(MyConstants.DAY);
        dateMonth = bundle.getInt(MyConstants.MONTH);
        dateYear = bundle.getInt(MyConstants.YEAR);
        timeHour = bundle.getInt(MyConstants.HOUR);
        timeMinute = bundle.getInt(MyConstants.MINUTE);

        Dialog d;

        if(type.equals(MyConstants.TIME_PICKER)){
            TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeHour = hourOfDay;
                    timeMinute = minute;
                    Bundle b = new Bundle();
                    b.putInt(MyConstants.HOUR, timeHour);
                    b.putInt(MyConstants.MINUTE, timeMinute);
                    Message msg = new Message();
                    msg.setData(b);
                    handler.sendMessage(msg);
                }
            };
            d = new TimePickerDialog(getActivity(), listener, timeHour, timeMinute, true);

        } else {
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateDay = dayOfMonth;
                    dateMonth = month;
                    dateYear = year;
                    Bundle b = new Bundle();
                    b.putInt(MyConstants.DAY, dateDay);
                    b.putInt(MyConstants.MONTH, dateMonth);
                    b.putInt(MyConstants.YEAR, dateYear);
                    Message msg = new Message();
                    msg.setData(b);
                    handler.sendMessage(msg);
                }
            };
            d =  new DatePickerDialog(getActivity(), listener, dateYear, dateMonth, dateDay);
        }

        return d;
    }
}
