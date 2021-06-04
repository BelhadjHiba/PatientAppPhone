package com.learntodroid.androidqrcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ExecutableService extends BroadcastReceiver {

    private Boolean key;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent(context, WakeupAlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        alarmIntent.putExtra("Stop",0);
        //open Activitiy
        context.startActivity(alarmIntent);

    }


}
