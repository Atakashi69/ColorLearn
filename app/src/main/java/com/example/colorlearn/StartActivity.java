package com.example.colorlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {
    String currentPrefix = "RU";
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final String[] INTERNET_PERMISSION = new String[]{Manifest.permission.INTERNET};
    private static final int CAMERA_REQUEST_CODE = 10;
    private static final int INTERNET_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setLanguage();

        MaterialButton btnCameraMode = findViewById(R.id.btnCameraMode);
        btnCameraMode.setOnClickListener(view -> {
            if (hasCameraPermission())
                openCameraActivity();
            else
                requestCameraPermission();
        });

        MaterialButton btnPictureMode = findViewById(R.id.btnPictureMode);
        btnPictureMode.setOnClickListener(view -> {
            if (hasInternetPermission())
                openPictureActivity();
            else
                requestInternetPermission();
        });
    }

    private void openCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    private void openPictureActivity() {
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }

    private boolean hasInternetPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestInternetPermission() {
        ActivityCompat.requestPermissions(this, INTERNET_PERMISSION, INTERNET_REQUEST_CODE);
    }

    private void setLanguage() {
        SharedPreferences mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        if (mPrefs.contains("currPrefix"))
            currentPrefix = mPrefs.getString("currPrefix", "default_value_if_variable_not_found");

        ((TextView)findViewById(R.id.textView)).setText(StringUtils.getStringXML(this, currentPrefix+"_select_mode"));
        ((Button)findViewById(R.id.btnCameraMode)).setText(StringUtils.getStringXML(this, currentPrefix+"_camera_mode"));
        ((Button)findViewById(R.id.btnPictureMode)).setText(StringUtils.getStringXML(this, currentPrefix+"_picture_mode"));
    }
}