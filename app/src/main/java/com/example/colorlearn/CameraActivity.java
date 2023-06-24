package com.example.colorlearn;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private String currentPrefix = "RU";
    private boolean voiceToggle = true;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private MediaPlayer mpCorrect;
    private ImageView imgCameraCurrentColor;
    private ImageView imgCameraFindColor;
    private TextView txtCameraFindColor;
    private TextView txtCameraCurrentColor;
    private final ColorUtils colorUtils = new ColorUtils();
    private String colorToFind;
    private TextToSpeech tts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        txtCameraFindColor = findViewById(R.id.txtCameraFindColor);
        txtCameraCurrentColor = findViewById(R.id.txtCameraCurrentColor);
        previewView = findViewById(R.id.previewView);
        imgCameraCurrentColor = findViewById(R.id.imgCameraCurrentColor);
        imgCameraFindColor = findViewById(R.id.imgCameraFindColor);

        setLanguage();

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindImageAnalysis(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));

        tts = new TextToSpeech(this, i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale(currentPrefix+"-"+currentPrefix.toLowerCase()));
                tts.setSpeechRate(1.0f);
                tts.setPitch(1.2f);
                generateNewColor();
            }
        });

        mpCorrect = MediaPlayer.create(this, R.raw.correct);

        ImageButton btnCameraVoice = findViewById(R.id.btnCameraVoice);
        btnCameraVoice.setOnClickListener(view -> TTSFindColor());

        ImageButton btnCameraExit = findViewById(R.id.btnCameraExit);
        btnCameraExit.setOnClickListener(view -> finish());

        ImageButton btnCameraNext = findViewById(R.id.btnCameraNext);
        btnCameraNext.setOnClickListener(view -> generateNewColor());
    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1000, 1000))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        ColorAnalyzer colorAnalyzer = new ColorAnalyzer();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), image -> {
            colorAnalyzer.analyze(image);
            Color avgColor = colorAnalyzer.getAverageColor();

            imgCameraCurrentColor.setBackgroundColor(Color.parseColor(String.format("#%02X%02X%02X", (int)avgColor.red(), (int)avgColor.green(), (int)avgColor.blue())));

            String colorName = colorUtils.getColorNameFromRgb(
                    currentPrefix,
                    (int)avgColor.red(),
                    (int)avgColor.green(),
                    (int)avgColor.blue()
            );
            txtCameraCurrentColor.setText(colorName);

            if (Objects.equals(colorName, colorToFind)) {
                txtCameraCurrentColor.setTextColor(Color.GREEN);
                Timer timer1 = new Timer();
                timer1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                Color avgColor = colorAnalyzer.getAverageColor();
                                String colorName = colorUtils.getColorNameFromRgb(currentPrefix, (int)avgColor.red(), (int)avgColor.green(), (int)avgColor.blue());
                                if (Objects.equals(colorName, colorToFind)) {
                                    generateNewColor();
                                    findViewById(R.id.layoutCheckmark).setVisibility(View.VISIBLE);
                                    mpCorrect.start();
                                    Timer timer2 = new Timer();
                                    timer2.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            findViewById(R.id.layoutCheckmark).setVisibility(View.INVISIBLE);
                                        }
                                    }, 750);
                                }
                            }
                        });
                    }
                }, 1500);
            } else {
                txtCameraCurrentColor.setTextColor(Color.WHITE);
            }
            image.close();
        });

        Preview preview = new Preview.Builder()
                .setTargetResolution(new Size(1000, 1000))
                .build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.createSurfaceProvider());
        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }

    private void TTSFindColor() {
        if (voiceToggle)
            tts.speak(txtCameraFindColor.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void generateNewColor() {
        String prevColor = colorToFind;
        do {
            colorToFind = colorUtils.getRandomColorName(currentPrefix);
        } while (Objects.equals(colorToFind, prevColor));

        imgCameraFindColor.setBackgroundColor(colorUtils.getColorFromColorName(currentPrefix, colorToFind));

        String txtFindColor = StringUtils.getStringXML(this, currentPrefix+"_find_color_1") + " " + colorToFind.toLowerCase() + " " + StringUtils.getStringXML(this, currentPrefix+"_find_color_2");
        txtCameraFindColor.setText(txtFindColor);

        TTSFindColor();
    }

    private void setLanguage() {
        SharedPreferences mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        if (mPrefs.contains("currPrefix"))
            currentPrefix = mPrefs.getString("currPrefix", "default_value_if_variable_not_found");
        if (mPrefs.contains("voiceToggle"))
            voiceToggle = mPrefs.getBoolean("voiceToggle", voiceToggle);
    }
}