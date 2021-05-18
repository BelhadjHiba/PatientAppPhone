package com.learntodroid.androidqrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Greeting extends AppCompatActivity {
    String patientId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  SimpleDateFormat formatter =new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        patientId = getIntent().getStringExtra("patient_KEY");
        System.out.println(patientId);
        final Context context = this;
        db.collection("Patient").document(patientId).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String pName = documentSnapshot.getString("name");
                    Toast.makeText(context, "hi " + pName + ", hava a nice Day", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(context, "Does not Exist", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
//                                if(formatter.format(myListOfDocuments.get(i).getTimestamp("time").getSeconds()*1000)==formatter.format(new Date())) {
//                                    long firstTime=myListOfDocuments.get(i).getTimestamp("startTime").getSeconds();
////                                    new AlarmHandler(context.getApplicationContext(),firstTime).scheduleRepeating(myListOfDocuments.get(i),i);
//                                }
//                            }
//
//                        }
//                }
//                    });
    }
}