package com.learntodroid.androidqrcodescanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

public class AlarmHandler {
    private final Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }

    public void setAlarmManager() {
//        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE,41);
        calendar.set(Calendar.SECOND,0);
        System.out.println(calendar.getTimeInMillis());


    // setRepeating() lets you specify a precise custom interval--in this case,
    // 1 minute.

    alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,1000*30,
    1000 * 120  , alarmIntent);}
//        alarmMgr.set(AlarmManager.RTC_WAKEUP,1000*60,alarmIntent);}

    public void cancelAlarmManager(){
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(alarmMgr!=null)
        {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
