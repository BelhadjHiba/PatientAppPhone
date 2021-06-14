package com.learntodroid.androidqrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.widget.TextView;
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
    private TextView text;

    private  SimpleDateFormat formatter =new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent1 =new Intent(this,TriggerDevice.class);
        startService(intent1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        SESSION();

        if(ActivityCompat.checkSelfPermission(Greeting.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Intent intent =new Intent(this,LocationService.class);
            startService(intent);
        }
        else
        {
            ActivityCompat.requestPermissions(Greeting.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
        text=findViewById(R.id.greeting);
        System.out.println(patientId);
        final Context context = this;
        db.collection("Patient").document(patientId).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String pName = documentSnapshot.getString("name");
                    text.setText("hi "+pName+" have a nice day");

                } else Toast.makeText(context, "Does not Exist", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent =new Intent(this,LocationService.class);
        startService(intent);
        Intent intent1 =new Intent(this,TriggerDevice.class);
        startService(intent1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent =new Intent(this,LocationService.class);
        startService(intent);
        Intent intent1 =new Intent(this,TriggerDevice.class);
        startService(intent1);
    }
}