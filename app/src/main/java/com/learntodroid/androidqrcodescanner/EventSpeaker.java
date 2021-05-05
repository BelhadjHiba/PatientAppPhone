package com.learntodroid.androidqrcodescanner;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class EventSpeaker implements TextToSpeech.OnInitListener {
    static final String TAG = "TTS";

    private TextToSpeech tts;
    private  Context context;
    private String text;
    private Boolean closed = false;



    public void setText(String text) {
        this.text = text;
    }

    public EventSpeaker(Context context, String text){
        tts = new TextToSpeech(context, this);
        this.context=context;
        this.text=text;
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "This language is not supported", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.v("TTS","onInit succeeded");
                     speak(text);
            }
        } else {
            Toast.makeText(context, "Initialization failed", Toast.LENGTH_SHORT).show();        }
    }
    public void speak(String s){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.v(TAG, "Speak new API");
                Bundle bundle = new Bundle();
                bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
                tts.speak(s, TextToSpeech.QUEUE_ADD, bundle, null);
                System.out.println("speaking");
            } else {
                Log.v(TAG, "Speak old API");
                HashMap<String, String> param = new HashMap<>();
                param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                tts.speak(s, TextToSpeech.QUEUE_ADD, param);
                System.out.println("speaking");
            }

    }
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            Log.v(TAG,"onDestroy: shutdown TTS");
            tts.stop();
            tts.shutdown();
        }

    }
}
