package com.learntodroid.androidqrcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

public class SchedulingBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        FirebaseFirestore.getInstance()
//                .collection("Events")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
//                            Log.e("Snapshot", String.valueOf(myListOfDocuments));
//                            for(int i=0; i<myListOfDocuments.size();i++)
//                            {
//                                Date date = new Date(myListOfDocuments.get(i).getTimestamp("startTime").getSeconds()*1000);
//                                if(date==new Date())
//                                new AlarmHandler(context.getApplicationContext()).setAlarmManager(myListOfDocuments.get(i).getTimestamp("time"),i);
//                            }
//
//                        }
//                    }
//                });
        Log.e("broadcast","worked"+System.currentTimeMillis());
    }
}
