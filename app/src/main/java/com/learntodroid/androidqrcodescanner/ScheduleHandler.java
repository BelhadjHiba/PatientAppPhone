package com.learntodroid.androidqrcodescanner;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.Timestamp;

import java.util.Calendar;

public class ScheduleHandler  {
    private final Context context;

    public ScheduleHandler(Context context) {
        this.context = context;


    }

    public void setSchedule(){

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SchedulingBroadcast.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR,3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        alarmMgr.setRepeating(AlarmManager.RTC, 2000,

               5000, alarmIntent);


    }



    }

