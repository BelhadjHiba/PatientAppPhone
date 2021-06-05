package com.learntodroid.androidqrcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;


public class ExecutableService extends BroadcastReceiver {

    private String key;

    @Override
    public void onReceive(Context context, Intent intent) {
        key=intent.getStringExtra("eventID");
        Log.e("key",key);
        Constants.db.collection("Patient").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                    Long hearingRate=task.getResult().getLong("hearingRate");
                    Long sightRate=task.getResult().getLong("sightRate");
                    if(hearingRate<50)
                    {

                    }
                     if(sightRate<50 )
                    {
                        Intent alarmIntent = new Intent(context, WakeupAlarmActivity.class);
                        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        alarmIntent.putExtra("Id",key);
                        //open Activitiy
                        context.startActivity(alarmIntent);
                    }
                    if(sightRate>=50 && hearingRate>=50){
                        Intent alarmIntent = new Intent(context, WakeupAlarmActivity.class);
                        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        alarmIntent.putExtra("Id",key);
                        //open Activitiy
                        context.startActivity(alarmIntent);
                    }
            }
        });



    }


}
