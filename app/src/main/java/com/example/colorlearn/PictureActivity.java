package com.example.colorlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PictureActivity extends AppCompatActivity {

    private String currentPrefix = "RU";
    private boolean voiceToggle = true;
    private MediaPlayer mpCorrect;
    private ImageView imgPicturePlaceholder;
    private Bitmap pictureBM;
    private ImageView imgPictureCurrentColor;
    private ImageView imgPictureFindColor;
    private TextView txtPictureFindColor;
    private TextView txtPictureCurrentColor;
    private final ColorUtils colorUtils = new ColorUtils();
    private String colorToFind;
    private TextToSpeech tts;
    private int currX = 0, currY = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        txtPictureFindColor = findViewById(R.id.txtPictureFindColor);
        txtPictureCurrentColor = findViewById(R.id.txtPictureCurrentColor);
        imgPicturePlaceholder = findViewById(R.id.imgPicturePlaceholder);
        imgPictureCurrentColor = findViewById(R.id.imgPictureCurrentColor);
        imgPictureFindColor = findViewById(R.id.imgPictureFindColor);

        setLanguage();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale(currentPrefix+"-"+currentPrefix.toLowerCase()));
                    tts.setSpeechRate(1.0f);
                    tts.setPitch(1.2f);
                    generateNewImage();
                }
            }
        });

        mpCorrect = MediaPlayer.create(this, R.raw.correct);

        imgPicturePlaceholder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                currX = (int)motionEvent.getX();
                currY = (int)motionEvent.getY();

                int pixel = pictureBM.getPixel(currX, currY);
                String colorName = colorUtils.getColorNameFromRgb(currentPrefix, Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                imgPictureCurrentColor.setBackgroundColor(Color.parseColor(String.format("#%02X%02X%02X", Color.red(pixel), Color.green(pixel), Color.blue(pixel))));

                Log.d("Clicked color:", String.format("#%02X%02X%02X", Color.red(pixel), Color.green(pixel), Color.blue(pixel)));

                txtPictureCurrentColor.setText(colorName);

                if (Objects.equals(colorName, colorToFind)) {
                    txtPictureCurrentColor.setTextColor(Color.GREEN);
                    Timer timer1 = new Timer();
                    timer1.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    int pixel = pictureBM.getPixel(currX, currY);
                                    String colorName = colorUtils.getColorNameFromRgb(currentPrefix, Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                                    if (Objects.equals(colorName, colorToFind)) {
                                        generateNewImage();
                                        mpCorrect.start();
                                    }
                                }
                            });
                        }
                    }, 1500);
                } else {
                    txtPictureCurrentColor.setTextColor(Color.WHITE);
                }
                return true;
            }
        });

        ImageButton btnPictureVoice = findViewById(R.id.btnPictureVoice);
        btnPictureVoice.setOnClickListener(view -> TTSFindColor());

        ImageButton btnPictureExit = findViewById(R.id.btnPictureExit);
        btnPictureExit.setOnClickListener(view -> finish());

        ImageButton btnPictureNext = findViewById(R.id.btnPictureNext);
        btnPictureNext.setOnClickListener(view -> generateNewImage());
    }

    private void setLanguage() {
        SharedPreferences mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        if (mPrefs.contains("currPrefix"))
            currentPrefix = mPrefs.getString("currPrefix", "default_value_if_variable_not_found");
        if (mPrefs.contains("voiceToggle"))
            voiceToggle = mPrefs.getBoolean("voiceToggle", voiceToggle);
    }


    private void generateNewImage() {
        txtPictureCurrentColor.setTextColor(Color.WHITE);
        txtPictureCurrentColor.setText(StringUtils.getStringXML(this, currentPrefix+"_click_picture"));

        int[] images = {
                R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
                R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8,
                R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12,
                R.drawable.img13,
        };
        Random rand = new Random();
        Picasso.get().load(images[rand.nextInt(images.length)])
                .fit()
                .centerCrop()
                .into(imgPicturePlaceholder, new Callback() {
            @Override
            public void onSuccess() {
                pictureBM = ((BitmapDrawable)imgPicturePlaceholder.getDrawable()).getBitmap();
                generateNewColor();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void TTSFindColor() {
        if (voiceToggle)
            tts.speak(txtPictureFindColor.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void generateNewColor() {
        int randomX = (int)((Math.random() * (pictureBM.getWidth() - 100 - 100)) + 100);
        int randomY = (int)((Math.random() * (pictureBM.getHeight() - 200 - 200)) + 200);

        int pixel = pictureBM.getPixel(randomX, randomY);
        while (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
            randomX = (int)((Math.random() * (pictureBM.getWidth() - 100 - 100)) + 100);
            randomY = (int)((Math.random() * (pictureBM.getHeight() - 100 - 100)) + 100);

            pixel = pictureBM.getPixel(randomX, randomY);
        }
        colorToFind = colorUtils.getColorNameFromRgb(currentPrefix, Color.red(pixel), Color.green(pixel), Color.blue(pixel));

        Log.d("ColorToFind:", String.format("#%02X%02X%02X", Color.red(pixel), Color.green(pixel), Color.blue(pixel)));

        imgPictureFindColor.setBackgroundColor(Color.parseColor(String.format("#%02X%02X%02X", Color.red(pixel), Color.green(pixel), Color.blue(pixel))));

        String txtFindColor = StringUtils.getStringXML(this, currentPrefix+"_find_color_1") + " " + colorToFind.toLowerCase() + " " + StringUtils.getStringXML(this, currentPrefix+"_find_color_2");
        txtPictureFindColor.setText(txtFindColor);
        TTSFindColor();
    }
}