package com.learntodroid.androidqrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class    MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private Button qrCodeFoundButton;
    private String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore.getInstance()
                .collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            Log.e("Snapshot", String.valueOf(myListOfDocuments));
                            for (DocumentSnapshot s:myListOfDocuments)
                            {
                                System.out.println(s.get("time").getClass().getName());
                                Log.e("DOC", String.valueOf(s));
                                 new AlarmHandler(getApplicationContext(), (com.google.firebase.Timestamp) s.get("time")).setAlarmManager();


                            }
                        }
                    }
                });

        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();

//        final Context context = this;
//        previewView = findViewById(R.id.activity_main_previewView);
//
//        qrCodeFoundButton = findViewById(R.id.activity_main_qrCodeFoundButton);
//        qrCodeFoundButton.setVisibility(View.INVISIBLE);
//        qrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), qrCode, Toast.LENGTH_SHORT).show();
//                Log.i(MainActivity.class.getSimpleName(), "QR Code Found: " + qrCode);
//                System.out.println("QR Code Found: " + qrCode);
//                Intent intent = new Intent(context, Greeting.class);
//                intent.putExtra("patient_KEY",qrCode);
//                startActivity(intent);
//            }
//        });
//
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//        requestCamera();
//    }
//
//    private void requestCamera() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            startCamera();
//        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CAMERA) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera();
//            } else {
//                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void startCamera() {
//        cameraProviderFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                bindCameraPreview(cameraProvider);
//            } catch (ExecutionException | InterruptedException e) {
//                Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
//        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);
//
//        Preview preview = new Preview.Builder()
//                .build();
//
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//
//        preview.setSurfaceProvider(previewView.createSurfaceProvider());
//
//        ImageAnalysis imageAnalysis =
//                new ImageAnalysis.Builder()
//                        .setTargetResolution(new Size(1280, 720))
//                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                        .build();
//
//        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
//            @Override
//            public void onQRCodeFound(String _qrCode) {
//                qrCode = _qrCode;
//                qrCodeFoundButton.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void qrCodeNotFound() {
//                qrCodeFoundButton.setVisibility(View.INVISIBLE);
//            }
//        }));
//
//        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }
}