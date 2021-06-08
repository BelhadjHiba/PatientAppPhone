package com.learntodroid.androidqrcodescanner;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.learntodroid.androidqrcodescanner.databinding.TopActivityBinding;
import com.learntodroid.androidqrcodescanner.utils.Save;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TOPActivity extends AppCompatActivity {
    private TopActivityBinding binding;
    private TextView text;
    private  String ID,eventID,eventName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding=TopActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventName =getIntent().getStringExtra("name");
        ID= Save.read(this,"patientID",null);
        eventID=getIntent().getStringExtra("eventID");
        Log.e("eid",eventID);
        text=binding.instruction;
        if(eventName =="Dinner" ||"Lunch"== eventName || "Snack"== eventName)
        {
            Eat();
        }
        else {
            Constants.db.collection("/House/Kitchen/Drawer/MedDrawer/PillBox").document(eventName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("pillBox","true");
                        Med();
                    } else {
                        Log.e("error","doesn't exist");
                        Constants.db.collection("/House/Kitchen/Drawer/MedDrawer/SyropBox").document(eventName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.e("syrup","true");
                                    Syrup();
                                } else {
                                    Log.e("dropper","true");
                                    Dropper();
                                }
                            }
                        });

                    }
                }
            });
        }

    }

    private void Syrup() {
    }

    private void Dropper() {
    }

    private void Eat() {
    }

    List<Boolean> instructions=new ArrayList<Boolean>();
    private ListenerRegistration rgDrawer,rg,registration0;

    public void Med(){
        Constants.db.collection("Patient").document(ID).collection("Events").document(eventID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                System.out.println((ArrayList<Boolean>)task.getResult().get("instructions"));
                instructions = (ArrayList<Boolean>) task.getResult().get("instructions");
                Log.e("instructions", String.valueOf(instructions));
                rgDrawer=Constants.db.collection("House").document("Kitchen").collection("Drawer")
                        .whereEqualTo("isLocked",false).whereEqualTo("DrawerLed",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable  QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.e("Error", String.valueOf(error));
                                }
                                if(value!=null){
                                    for(QueryDocumentSnapshot doc : value)
                                    { if(!doc.getBoolean("drawerState") && instructions.get(0)){
                                        text.setText("Open The Medicine Drawer In The Kitchen");
                                    }
                                    else{
                                        if(doc.getBoolean("drawerState")){
                                            Constants.db.collection("Patient").document(ID).collection("Statement").add(new Statement("open",new Timestamp(new Date(System.currentTimeMillis())),doc.getId()));
//                                     Log.e("Statement1",state.getObject()+" "+state.getVerb()+"ed at"+state.getTime().getSeconds());
                                        }
                                        rg= Constants.db.collection("/House/Kitchen/Drawer/MedDrawer/PillBox").whereEqualTo("isLocked",false).whereEqualTo("boxLed",true).
                                                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        if (error != null) {
                                                            Log.e("error", "OnEvent ", error);
                                                            return;
                                                        }
                                                        if (value != null) {
                                                            Log.e("Document changes", String.valueOf(value)) ;

                                                            for (QueryDocumentSnapshot pillBox : value) {
                                                                if (!pillBox.getBoolean("boxState")  && instructions.get(1)==true){
                                                                   text.setText("Open The Pillbox");

                                                                }
                                                                else {
                                                                    if(pillBox.getBoolean("boxState"))
                                                                    {
                                                                        Constants.db.collection("Patient").document(ID).collection("Statement").add(new Statement("open",new Timestamp(new Date(System.currentTimeMillis())),pillBox.getId()));

                                                                    }
                                                                    System.out.println("--------------Opened");
                                                                    registration0 = pillBox.getReference().collection("Slot").whereEqualTo("isLocked",false).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                            if (error != null) {
                                                                                Log.e("error", "OnEvent ", error);
                                                                                return;
                                                                            }
                                                                            if (value != null) {
                                                                                for (QueryDocumentSnapshot doc : value){
                                                                                    System.out.println(doc);
                                                                                    if (instructions.get(2)==true && doc.getBoolean("slotState")==false){
                                                                                        text.setText("Open the luminous slot");

                                                                                    }
                                                                                    else if (instructions.get(3)==true && !doc.getBoolean("isEmpty")) {
                                                                                        Constants.db.collection("Patient").document(ID).collection("Statement").add(new Statement("open", new Timestamp(new Date(System.currentTimeMillis())), doc.getId()));
                                                                                        text.setText("Take Pills in the luminous slot");
                                                                                    }
                                                                                    if (doc.getBoolean("isEmpty") == true) {
                                                                                        Constants.db.collection("Patient").document(ID).collection("Statement").add(new Statement("Drink",new Timestamp(new Date(System.currentTimeMillis())),pillBox.getId()));
                                                                                        Log.d("finished",doc.getId()+"Done");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rg.remove();
        registration0.remove();
        rgDrawer.remove();
    }
}

