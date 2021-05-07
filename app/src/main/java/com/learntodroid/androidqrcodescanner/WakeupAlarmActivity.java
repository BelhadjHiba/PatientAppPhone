package com.learntodroid.androidqrcodescanner;

import android.annotation.TargetApi;
import android.app.KeyguardManager;

import android.media.AudioManager;
import android.media.MediaDrm;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class WakeupAlarmActivity extends AppCompatActivity {
    static final String TAG = "TTS";
    TextToSpeech mTts;
    EventSpeaker eventSpeaker;
    Character a='a';
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Boolean  stateBox=true;

   String text = "";
    Handler handler = new Handler();
   private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//
            eventSpeaker = new EventSpeaker(getApplicationContext(), text);
            handler.postDelayed(this,5000);

        }
    };
    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wakeup);
//
//do {


    db.collection("PillBox").document("Ibuprofen").addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            if (error != null) {
                Log.e(TAG, "OnEvent ", error);

                return;
            }
            if (value != null) {

                System.out.println(value.getBoolean("boxState"));

                if(!value.getBoolean("boxState"))
                {
                    text="Open the pillBox";
                    runnable.run();
                }
                else {
//                    handler.removeCallbacks(runnable);

////                    for (int i = 0; i < 5; i++) {
//
////                            break;
//
//
//                    handler.postDelayed(runnable,1000);
//
//
//
//
////                    if (value.getBoolean("boxState") == true )
////                        eventSpeaker.onDestroy();
////                                   try {
////                                       TimeUnit.SECONDS.sleep(10);
////                                   } catch (InterruptedException e) {
////                                       e.printStackTrace();
////                                   }
//
////                    }
//
////                                System.out.println("-----------closed");
////                                eventSpeaker=new EventSpeaker(getApplicationContext(),"open the  pillbox");
//                }
                    System.out.println("--------------Opened");


                        value.getReference().collection("Slot").document("2").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.e(TAG, "OnEvent ", error);

                                    return;
                                }
                                if (value != null) {
                                    System.out.println(value.getBoolean("slotState"));

                                    if (!value.getBoolean("slotState")) {
                                           text= "Open the luminous slot";

                                        runnable.run();

                                        //                                   try {
                                            //                                       TimeUnit.SECONDS.sleep(10);
                                            //                                   } catch (InterruptedException e) {
                                            //                                       e.printStackTrace();
                                            //                                   }

                                        }
                                        //                                System.out.println("-----------closed");
                                        //                                eventSpeaker=new EventSpeaker(getApplicationContext(),"open the  pillbox");
                                    else if (value.getBoolean("slotState") == true) {

                                            text= "Take Pills in the luminous slot";
                                            runnable.run();
                                            //                                   try {
                                            //                                       TimeUnit.SECONDS.sleep(10);
                                            //                                   } catch (InterruptedException e) {
                                            //                                       e.printStackTrace();
                                            //                                   }


                                    }

                                    if (value.getBoolean("isEmpty") == true) {
                                        handler.removeCallbacks(runnable);
                                    }


                                }
                            }
                        });

                }
            }
        }

    });


//            eventSpeaker.setText("Open the Fridge");


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
//}while (slotState == false);
        }


    void speak(String s){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.v(TAG, "Speak new API");
            Bundle bundle = new Bundle();
            bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
            mTts.speak(s, TextToSpeech.QUEUE_ADD, bundle, null);
        } else {
            Log.v(TAG, "Speak old API");
            HashMap<String, String> param = new HashMap<>();
            param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            mTts.speak(s, TextToSpeech.QUEUE_ADD, param);
        }
    }
    @Override
    protected void onDestroy(){
            super.onDestroy();
            // Don't forget to shutdown tts!
            if (mTts != null) {
                Log.v(TAG, "onDestroy: shutdown TTS");
                mTts.stop();
                mTts.shutdown();
            }
        }
        public void loopSpeak(){

        }


}
