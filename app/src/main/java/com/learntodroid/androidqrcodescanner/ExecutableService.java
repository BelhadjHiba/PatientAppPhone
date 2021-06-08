package com.learntodroid.androidqrcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.learntodroid.androidqrcodescanner.utils.Save;


public class ExecutableService extends BroadcastReceiver {

    private String key,name,id;

    @Override
    public void onReceive(Context context, Intent intent) {
        key=intent.getStringExtra("eventID");
        name=intent.getStringExtra("name");
        id= Save.read(context,"patientID",null);
        Log.e("key",key);
        Constants.db.collection("Patient").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                    Long hearingRate=task.getResult().getLong("hearingRate");
                    Long sightRate=task.getResult().getLong("sightRate");
                    if(hearingRate<50)
                    {
                        Intent alarmIntent = new Intent(context, TOPActivity.class);
                        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        alarmIntent.putExtra("eventID",key);
                        alarmIntent.putExtra("name",name);
                        //open Activitiy
                        context.startActivity(alarmIntent);
                    }
                    else if(sightRate<50 || (sightRate>=50 && hearingRate>=50) )
                    {
                        Intent alarmIntent = new Intent(context, WakeupAlarmActivity.class);
                        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        alarmIntent.putExtra("eventID",key);
                        alarmIntent.putExtra("name",name);
                        //open Activitiy
                        context.startActivity(alarmIntent);
                    }
                    else if(sightRate==0 && hearingRate==0)
                    {
//                        Intent alarmIntent= new Intent(context,)
                    }

            }
        });



    }


}
