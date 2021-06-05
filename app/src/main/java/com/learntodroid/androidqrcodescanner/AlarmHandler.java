package com.learntodroid.androidqrcodescanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.System.currentTimeMillis;


public class AlarmHandler {
    private final Context context;
    private long Interval;
    private  FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Boolean Stop=false;
    public AlarmHandler(Context context) {
        this.context = context;


    }
//
    public void setAlarmManager(DocumentSnapshot doc, int id ) {
//        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ExecutableService.class);
        intent.putExtra("eventID",doc.getId());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP,
                doc.getTimestamp("startTime").getSeconds()*1000,
//                1000,
                alarmIntent);


    }
    
    public void cancelAlarmManager(int id){
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(alarmMgr!=null)
        {
            alarmMgr.cancel(alarmIntent);
        }
    }
    public void scheduleRepeating(DocumentSnapshot doc,int id ){
        Timestamp endTime=doc.getTimestamp("endTime"),startTime=doc.getTimestamp("startTime"),sleepTime=doc.getTimestamp("sleepTime");
        switch((String)doc.get("repeat")) {
            case "EveryDay": {
                Log.e("case1", "true");
                if (endTime != null) {
                    if (startTime.getSeconds() <= endTime.getSeconds()) {
                        Log.e("everydayWithEndTime","true");
                        setAlarmManager(doc, id);
                        db.collection("Events").document(doc.getId()).update("startTime", new Timestamp(new Date(startTime.getSeconds() * 1000 + AlarmManager.INTERVAL_DAY)));
                    } else {
                        cancelAlarmManager(id);
                    }
                } else {
                    Log.e("everydayWithoutEndTime","true");
                    setAlarmManager(doc, id);
                    db.collection("Events").document(doc.getId()).update("startTime", new Timestamp(new Date(startTime.getSeconds() * 1000 + AlarmManager.INTERVAL_DAY)));
                }
                break;
            }
            case "EveryWeek": {
                if (endTime != null) {
                    if(startTime.getSeconds()<endTime.getSeconds()) {
                        Log.e("everyWeekWithEndTime","true");
                        setAlarmManager(doc,id);
                        List<Object> weekDays = (List<Object>) doc.get("weekDays");
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(currentTimeMillis());
                        int start = cal.get(Calendar.DAY_OF_WEEK);
                        start--;
                        int i=1;
                        while ((boolean)weekDays.get((start+1)%7)==false){
                            start++;
                            i++;
                        }
                        db.collection("Events").document(doc.getId()).
                                update("startTime",new Timestamp(new Date(startTime.getSeconds()*1000+i*AlarmManager.INTERVAL_DAY)));

                    }
                    else
                        cancelAlarmManager(id);

                }else {
                    setAlarmManager(doc,id);
                    List<Object> weekDays = (List<Object>) doc.get("weekDays");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(currentTimeMillis());
                    int start = cal.get(Calendar.DAY_OF_WEEK);
                    start--;
                    int i=1;
                    while ((boolean)weekDays.get((start+1)%7)==false){
                        start++;
                        i++;
                    }
                    db.collection("Events").document(doc.getId()).
                            update("startTime",new Timestamp(new Date(startTime.getSeconds()*1000+i*AlarmManager.INTERVAL_DAY)));

                }
                break;
            }
            case"ConstantDays":{
                if(endTime!=null){
                    if(startTime.getSeconds()<=endTime.getSeconds()){
                        setAlarmManager(doc   ,id);
                        db.collection("Events").document(doc.getId()).update("startTime",new Timestamp(new Date(startTime.getSeconds()*1000+doc.getLong("interval")*AlarmManager.INTERVAL_DAY)));
                    }else
                        cancelAlarmManager(id);

                }else{
                    setAlarmManager(doc,id);
                    db.collection("Events").document(doc.getId()).update("startTime",new Timestamp(new Date(startTime.getSeconds()*1000+doc.getLong("interval")*AlarmManager.INTERVAL_DAY)));
                }
                break;
            }


            }
        }
    }


