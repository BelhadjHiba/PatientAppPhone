package com.learntodroid.androidqrcodescanner;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.learntodroid.androidqrcodescanner.utils.Save;


public class TriggerDevice extends Service {
    private String pID;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pID= Save.read(getApplicationContext(),"patientID",null);
        Constants.db.collection("Patient").document(pID).collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Error", String.valueOf(error));
                }
                if(value!=null){
                    for(DocumentChange doc:value.getDocumentChanges())
                    {
                        switch (doc.getType())
                        {
                            case MODIFIED:
                            {
                                if(doc.getDocument().getLong("Device")==1)
                                {
                                    new AlarmHandler(getApplicationContext()).setAlarmManager(doc.getDocument());
                                }
                            }
                        }

                    }
                }
            }
        });
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
