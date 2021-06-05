package com.learntodroid.androidqrcodescanner;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public abstract class Constants {
    public static long IntervalMonth=2592000000L;

    public static FirebaseFirestore db=FirebaseFirestore.getInstance();

}
