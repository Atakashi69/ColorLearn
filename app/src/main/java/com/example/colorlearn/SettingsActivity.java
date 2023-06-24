package com.example.colorlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {
    String currentPrefix = "RU";
    boolean voiceToggle = true;
    String[] prefixes = {"RU", "EN"};
    String[] languages = {"Русский", "English"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        autoCompleteTextView = findViewById(R.id.auto_complete_textview);
        adapterLanguages = new ArrayAdapter<>(this, R.layout.list_languages, languages);

        setLanguage();
        setStyle();

        autoCompleteTextView.setAdapter(adapterLanguages);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, index, l) -> {
            SharedPreferences mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("currPrefix", prefixes[index]).apply();
            setLanguage();
        });

        MaterialButton btnVoiceToggle = findViewById(R.id.btnVoiceToggle);
        btnVoiceToggle.setOnClickListener(v -> {
            SharedPreferences mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            voiceToggle = !voiceToggle;
            mEditor.putBoolean("voiceToggle", voiceToggle).apply();
            setLanguage();
            setStyle();
        });
    }

    private void setLanguage() {
        SharedPreferences mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        if (mPrefs.contains("currPrefix"))
            currentPrefix = mPrefs.getString("currPrefix", currentPrefix);
        if (mPrefs.contains("voiceToggle"))
            voiceToggle = mPrefs.getBoolean("voiceToggle", voiceToggle);

        String currentLanguage = StringUtils.getStringXML(this, currentPrefix + "_lang");
        autoCompleteTextView.setHint(currentLanguage);

        ((TextView)findViewById(R.id.textView2)).setText(StringUtils.getStringXML(this, currentPrefix+"_select_lang"));
        ((TextView)findViewById(R.id.textView3)).setText(StringUtils.getStringXML(this, currentPrefix+"_settings"));
        ((TextView)findViewById(R.id.textView5)).setText(StringUtils.getStringXML(this, currentPrefix+"_settings_voice"));
        ((MaterialButton)findViewById(R.id.btnVoiceToggle)).setText(StringUtils.getStringXML(this, currentPrefix+"_settings_voice_"+(voiceToggle ? "on" : "off")));
    }

    private void setStyle() {
        MaterialButton btnVoiceToggle = findViewById(R.id.btnVoiceToggle);
        if (voiceToggle) {
            btnVoiceToggle.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.play_button)));
        } else {
            btnVoiceToggle.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        }
    }
}





















