package com.development.damian.testapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.development.damian.testapp.Alarms.BroadcastReceiverNotificationAlarm;
import com.development.damian.testapp.ApplicationState;
import com.development.damian.testapp.R;
import com.development.damian.testapp.statusbarnotification.StatusBarNotification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    StatusBarNotification statusBarNotification;
    ApplicationState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Show Notification", Snackbar.LENGTH_LONG)
                        .setAction("Show", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                createNotification();
                            }
                        }).show();
            }
        });

        appState = (ApplicationState)getApplicationContext();
        statusBarNotification = new StatusBarNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //appState.setNotificatioVisible(false);

        startNotificationAlarm();

        if(getIntent().hasExtra("MessageID")) {

            statusBarNotification.clearNotification(getApplicationContext(), getIntent().getIntExtra("MessageID",0));
        }
    }

    private void createNotification(){
        DateFormat df = new SimpleDateFormat("h:mm a");
        String[] events = new String[2];
        events[0] = "VIAJE Nº: " + 123;
        events[1] = "HORA RECIBIDO: " + df.format(Calendar.getInstance().getTime());
        statusBarNotification.showTestNotification(MainActivity.this, "TestNotification", "", events, 123, appState);
    }

    private void startNotificationAlarm(){
        BroadcastReceiverNotificationAlarm alarm = new BroadcastReceiverNotificationAlarm();
        alarm.SetAlarm(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        appState.setNotificatioVisible(true);
    }
}
