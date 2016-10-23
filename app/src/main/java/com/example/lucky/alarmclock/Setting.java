package com.example.lucky.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by lucky on 28/09/16.
 */

public class Setting extends FragmentActivity implements View.OnClickListener {

    private static int dateDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private static int dateMonth = Calendar.getInstance().get(Calendar.MONTH);
    private static int dateYear = Calendar.getInstance().get(Calendar.YEAR);
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    private static String theme = null;

    // Pouvoir afficher nombre à 2 chiffres
    private static DecimalFormat nf = new DecimalFormat("00");

    TextView textViewDate;
    private static TextView textViewHour;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        textViewDate = (TextView)findViewById(R.id.msgDate);
        textViewDate.setText(nf.format(dateDay) + "/" + nf.format(dateMonth) + "/" + dateYear);

        textViewHour = (TextView)findViewById(R.id.msgHour);
        textViewHour.setText(nf.format(timeHour) + ":" + nf.format(timeMinute));

        View.OnClickListener listenerDate = new View.OnClickListener() {
            public void onClick(View view) {
                textViewDate.setText("");
                Bundle bundle = new Bundle();
                bundle.putInt(MyConstants.DAY, dateDay);
                bundle.putInt(MyConstants.MONTH, dateMonth);
                bundle.putInt(MyConstants.YEAR, dateYear);
                MyDialogFragment fragment = new MyDialogFragment(new HandlerDate(), MyConstants.DATE_PICKER);
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment, MyConstants.DATE_PICKER);
                transaction.commit();
            }
        };
        Button btnDate = (Button)findViewById(R.id.buttonDate);
        btnDate.setOnClickListener(listenerDate);

        View.OnClickListener listenerHour = new View.OnClickListener() {
            public void onClick(View view) {
                textViewHour.setText("");
                Bundle bundle = new Bundle();
                bundle.putInt(MyConstants.HOUR, timeHour);
                bundle.putInt(MyConstants.MINUTE, timeMinute);
                MyDialogFragment fragment = new MyDialogFragment(new HandlerHour(), MyConstants.TIME_PICKER);
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment, MyConstants.TIME_PICKER);
                transaction.commit();
            }
        };
        Button btnHour = (Button)findViewById(R.id.buttonHour);
        btnHour.setOnClickListener(listenerHour);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioTheme);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBeach:
                        theme = "Beach";
                        break;
                    case R.id.radioForest:
                        theme = "Forest";
                        break;
                }
            }
        });

        Button buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
    }

    class HandlerDate extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            dateDay = bundle.getInt(MyConstants.DAY);
            dateMonth = bundle.getInt(MyConstants.MONTH);
            dateYear = bundle.getInt(MyConstants.YEAR);
            textViewDate.setText(nf.format(dateDay) + "/" + nf.format(dateMonth) + "/" + dateYear);
        }
    }

    class HandlerHour extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            timeHour = bundle.getInt(MyConstants.HOUR);
            timeMinute = bundle.getInt(MyConstants.MINUTE);
            textViewHour.setText(nf.format(timeHour) + ":" + nf.format(timeMinute));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Setting.this, MainActivity.class);
        intent.putExtra("dateDay", dateDay);
        intent.putExtra("dateMonth", dateMonth);
        intent.putExtra("dateYear", dateYear);
        intent.putExtra("timeHour", timeHour);
        intent.putExtra("timeMinute", timeMinute);

        Log.i("APPLI", "Theme numero : "+theme);
        intent.putExtra("theme", theme);
        startActivity(intent);
    }
}

