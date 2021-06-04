package com.learntodroid.androidqrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
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
import com.learntodroid.androidqrcodescanner.utils.Save;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Greeting extends AppCompatActivity {
    private String patientId,session;
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
//        patientId = getIntent().getStringExtra("patient_KEY");
        SESSION();
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
        FirebaseFirestore.getInstance()
                .collection("Patient").document(patientId).collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for(int i=0; i<myListOfDocuments.size();i++)
                            {
                                if(formatter.format(myListOfDocuments.get(i).getTimestamp("startTime").getSeconds()*1000).compareTo(formatter.format(new Date()))==0) {
                                    new AlarmHandler(getApplicationContext()).scheduleRepeating(myListOfDocuments.get(i),i);
                                }
                            }

                        }
                    }
                });
    }

    private void SESSION() {
         session = Save.read(getApplicationContext(), "patientID", null);
        if(session==null)
        {
            //on first use
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            patientId=session;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}