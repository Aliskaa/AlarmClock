package com.example.lucky.alarmclock;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends FragmentActivity {
    private static int dateDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private static int dateMonth = Calendar.getInstance().get(Calendar.MONTH);
    private static int dateYear = Calendar.getInstance().get(Calendar.YEAR);
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    private static DecimalFormat nf = new DecimalFormat("00");

    public static String theme = null;

    TextView textViewTime, textViewDate, textViewTheme;
    private static TextView textView2;

    public static TextView getTextView2() {
        return textView2;
    }

    public static String getSongTheme() {
        return theme;
    }

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            Toast.makeText(this, "Permission checking", Toast.LENGTH_SHORT).show();
            checkPermission();
        }

        textViewTime = (TextView) findViewById(R.id.time);
        textViewDate = (TextView) findViewById(R.id.date);
        textViewTheme = (TextView) findViewById(R.id.theme);

        textView2 = (TextView) findViewById(R.id.msg2);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        OnClickListener listenerSet = new OnClickListener() {
            public void onClick(View view) {
                textView2.setText("");
                setAlarm();
            }
        };
        Button btnSet = (Button) findViewById(R.id.buttonSet);
        btnSet.setOnClickListener(listenerSet);

        OnClickListener listenerCancel = new OnClickListener() {
            public void onClick(View view) {
                textView2.setText("");
                cancelAlarm();
            }
        };
        Button btnCancel = (Button) findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(listenerCancel);

        OnClickListener listenerSetting = new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        };
        Button btnSetting = (Button) findViewById(R.id.buttonSetting);
        btnSetting.setOnClickListener(listenerSetting);
    }

    public void onResume() {
        super.onResume();

        dateDay = getIntent().getIntExtra("dateDay", dateDay);
        dateMonth = getIntent().getIntExtra("dateMonth", dateMonth);
        dateYear = getIntent().getIntExtra("dateYear", dateYear);
        timeHour = getIntent().getIntExtra("timeHour", timeHour);
        timeMinute = getIntent().getIntExtra("timeMinute", timeMinute);
        theme = getIntent().getStringExtra("theme");

        textViewTime.setText(nf.format(timeHour) + ":" + nf.format(timeMinute));
        textViewDate.setText(nf.format(dateDay) + "/" + nf.format(dateMonth) + "/" + dateYear);
        textViewTheme.setText(theme);
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dateDay);
        calendar.set(Calendar.MONTH, dateMonth);
        calendar.set(Calendar.YEAR, dateYear);
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.i("APPLI", "Alarme activée");
    }

    private void cancelAlarm() {
        if (alarmManager != null) {
            if(theme != null) {
                AlarmReceiver.setMediaPlayer();
            } else {
                AlarmReceiver.setRingtone();
            }

            alarmManager.cancel(pendingIntent);
            Log.i("APPLI", "Alarme désactivée");
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        } else {

        }
    }
}