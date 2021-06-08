package com.learntodroid.androidqrcodescanner;

import android.media.AudioManager;
import android.os.Bundle;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.learntodroid.androidqrcodescanner.utils.Save;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WakeupAlarmActivity extends AppCompatActivity {
    static final String TAG = "TTS";
    TextToSpeech mTts;
    EventSpeaker eventSpeaker;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Boolean  stateBox=true;
    private Boolean Stop=false;
   String text = "",ID, eventName,eventID;
private   ListenerRegistration rgDrawer,rg,registration0;
    Handler handler = new Handler();
    List<Boolean> instructions=new ArrayList<Boolean>();
   private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            eventSpeaker = new EventSpeaker(getApplicationContext(), text);
            handler.postDelayed(this,7000);
        }
    };
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wakeup);
            eventName =getIntent().getStringExtra("name");
            eventID=getIntent().getStringExtra("eventID");
            Log.e("EVentId", eventName);
            ID=Save.read(this,"patientID",null);
            Log.e("patientId",ID);
            if(eventName =="Dinner" ||"Lunch"== eventName || "Snack"== eventName)
            {
                Eat();
            }
            else {
               if( Constants.db.collection("/House/Kitchen/Drawer/MedDrawer/PillBox").document(eventName).get()!=null)
               {
                   Med();
               }
               else if(Constants.db.collection("/House/Kitchen/Drawer/MedDrawer/SyropBox").document(eventName).get()!=null)
               {
                    Syrup();
               }
               else
               {
                   Dropper();

               }
            }
            Log.e("Alarm","WORKING");





//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    finish();
//                }
//            }, 1000*30);
//            Vibrator vibrator= (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
//            if(Build.VERSION.SDK_INT>=26)
//                vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
//            else
//                vibrator.vibrate(500);
//            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
//            System.out.println(Calendar.getInstance());
//            Date alarmDate=new Date();
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//
//                setShowWhenLocked(true);
//                setTurnScreenOn(true);
//                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
//                keyguardManager.requestDismissKeyguard(this, null);
//            }
//            else {
//
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
//                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
//                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
//                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            }
//            Scanner scanner = new Scanner(System.in);

//                msg=scanner.nextBoolean();
//
//            mTts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    if (status == TextToSpeech.SUCCESS) {
//                        int result = mTts.setLanguage(Locale.ENGLISH);
//                        if (result == TextToSpeech.LANG_MISSING_DATA
//                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                            Toast.makeText(getApplicationContext(), "This language is not supported", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Log.v("TTS","onInit succeeded");
//                            do {
//                                speak(emailId);
//                            }while (true);
//
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Initialization failed", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });

    setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }

    private void Eat() {
    }

    private void Dropper() {
    }

    private void Syrup() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        rgDrawer.remove();
        rg.remove();
        registration0.remove();
    }
    private void Med(){
        db.collection("Patient").document(ID).collection("Events").document(eventName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                System.out.println((ArrayList<Boolean>)task.getResult().get("instructions"));
                instructions = (ArrayList<Boolean>) task.getResult().get("instructions");
                Log.e("instructions", String.valueOf(instructions));
                rgDrawer=db.collection("House").document("Kitchen").collection("Drawer")
                        .whereEqualTo("isLocked",false).whereEqualTo("DrawerLed",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.e("Error", String.valueOf(error));
                                }
                                if(value!=null){
                                    for(QueryDocumentSnapshot doc : value)
                                    { if(!doc.getBoolean("drawerState") && instructions.get(0)){
                                        text = "Open The Medicine Drawer In The Kitchen";
                                        runnable.run();
                                    }
                                    else{
                                        if(doc.getBoolean("drawerState")){
                                            db.collection("Patient").document(ID).collection("Statement").add(new Statement("open",new Timestamp(new Date(System.currentTimeMillis())),doc.getId()));
//                                     Log.e("Statement1",state.getObject()+" "+state.getVerb()+"ed at"+state.getTime().getSeconds());
                                        }
                                        rg= db.collection("/House/Kitchen/Drawer/MedDrawer/PillBox").whereEqualTo("isLocked",false).whereEqualTo("boxLed",true).
                                                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        if (error != null) {
                                                            Log.e(TAG, "OnEvent ", error);
                                                            return;
                                                        }
                                                        if (value != null) {
                                                            Log.e("Document changes", String.valueOf(value)) ;

                                                            for (QueryDocumentSnapshot pillBox : value) {
                                                                if (!pillBox.getBoolean("boxState")  && instructions.get(1)==true){
                                                                    text = "Open The Pillbox";
                                                                    runnable.run();
                                                                    Log.e("doc id","worked ");
                                                                }
                                                                else {
                                                                    if(pillBox.getBoolean("boxState"))
                                                                    {
                                                                        db.collection("Patient").document(ID).collection("Statement").add(new Statement("open",new Timestamp(new Date(System.currentTimeMillis())),pillBox.getId()));

                                                                    }
                                                                    System.out.println("--------------Opened");
                                                                    registration0 = pillBox.getReference().collection("Slot").whereEqualTo("isLocked",false).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                            if (error != null) {
                                                                                Log.e(TAG, "OnEvent ", error);
                                                                                return;
                                                                            }
                                                                            if (value != null) {
                                                                                for (QueryDocumentSnapshot doc : value){
                                                                                    System.out.println(doc);
                                                                                    if (instructions.get(2)==true && doc.getBoolean("slotState")==false){
                                                                                        text = "Open the luminous slot";
                                                                                        runnable.run();
                                                                                        Log.e("doc id",doc.getId());
                                                                                    }
                                                                                    else if (instructions.get(3)==true && !doc.getBoolean("isEmpty")) {
                                                                                        db.collection("Patient").document(ID).collection("Statement").add(new Statement("open",new Timestamp(new Date(System.currentTimeMillis())),doc.getId()));
                                                                                        text = "Take Pills in the luminous slot";
                                                                                        runnable.run();
                                                                                        Log.e("doc id",doc.getId());
                                                                                    }
                                                                                    if (doc.getBoolean("isEmpty") == true) {
                                                                                        db.collection("Patient").document(ID).collection("Statement").add(new Statement("Drink",new Timestamp(new Date(System.currentTimeMillis())),pillBox.getId()));
                                                                                        Log.d("finished",doc.getId()+"Done");
                                                                                        handler.removeCallbacks(runnable);
                                                                                        if (mTts != null) {
                                                                                            Log.v(TAG, "onDestroy: shutdown TTS");
                                                                                            mTts.stop();
                                                                                            mTts.shutdown();
                                                                                        }
                                                                                        finish();

                                                                                    }

                                                                                }
                                                                            }
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                    }
                                }
                            }
                        });


            }
        });
    }
}
