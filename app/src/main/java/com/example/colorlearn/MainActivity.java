package com.example.colorlearn;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnMenuStart = findViewById(R.id.btnMenuStart);
        btnMenuStart.setOnClickListener(view -> openStartActivity());

        MaterialButton btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(view -> openSettingsActivity());

        MaterialButton btnMenuExit = findViewById(R.id.btnMenuExit);
        btnMenuExit.setOnClickListener(view -> {
            finish();
            System.exit(0);
        });
    }

    public void openStartActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}