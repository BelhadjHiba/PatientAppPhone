package com.learntodroid.androidqrcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ExecutableService extends BroadcastReceiver {
    static final boolean deaf =true;
    static final boolean deafBlind =false;


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent(context, WakeupAlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //open Activitiy
        context.startActivity(alarmIntent);
    }


}
