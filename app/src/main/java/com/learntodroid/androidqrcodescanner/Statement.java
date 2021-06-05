package com.learntodroid.androidqrcodescanner;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class Statement {
    private String verb;
    private Timestamp time;
    private String object;

    public Statement(String verb, Timestamp time, String object) {
        this.verb = verb;
        this.time = time;
        this.object = object;
    }

    public String getVerb() {
        return verb;
    }
//
//    public void setVerb(String verb) {
//        this.verb = verb;
//    }
//
    public Timestamp getTime() {
        return time;
    }
//
//    public void setTime(Timestamp time) {
//        this.time = time;
//    }
//
    public String getObject() {
        return object;
    }
//
//    public void setObject(String object) {
//        this.object = object;
//    }
}
