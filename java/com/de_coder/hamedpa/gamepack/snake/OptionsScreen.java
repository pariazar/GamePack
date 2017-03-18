package com.de_coder.hamedpa.gamepack.snake;
//Developed by HamedPa

import android.app.Activity;
import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.de_coder.hamedpa.gamepack.R;

public class OptionsScreen extends Activity {

    SharedPreferences userPreferences, speedSetting;
    SharedPreferences.Editor userPreferencesEditor, speedSettingEditor;
    Spinner themeSpinner,controlsSpinner,viewSpinner,speedSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        userPreferences  = getSharedPreferences("settings", 0);
        int theme = userPreferences.getInt("theme",0);
        int controls = userPreferences.getInt("controls",0);
        int view  = userPreferences.getInt("view",0);
        speedSetting = getSharedPreferences("speed", 0);
        int speed = speedSetting.getInt("speed",0);


        if(theme == 1) setTheme(android.R.style.Theme_Holo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);


        themeSpinner = (Spinner) findViewById(R.id.spinnerTheme);
        controlsSpinner = (Spinner) findViewById(R.id.spinnerControls);
        viewSpinner  = (Spinner) findViewById(R.id.spinnerView);
        speedSpinner = (Spinner) findViewById(R.id.spinnerSpeed);


        themeSpinner.setSelection(theme);
        controlsSpinner.setSelection(controls);
        viewSpinner.setSelection(view);
        speedSpinner.setSelection(speed);
    }


    public void back(View view){
        onBackPressed();
    }


    @Override
    public void onBackPressed(){


        int theme = themeSpinner.getSelectedItemPosition();
        int controls = controlsSpinner.getSelectedItemPosition();
        int view = viewSpinner.getSelectedItemPosition();
        int speed = speedSpinner.getSelectedItemPosition();


        userPreferencesEditor = userPreferences.edit();
        userPreferencesEditor.putInt("theme", theme);
        userPreferencesEditor.putInt("controls", controls);
        userPreferencesEditor.putInt("view", view);
        speedSettingEditor = speedSetting.edit();
        speedSettingEditor.putInt("speed", speed);
        userPreferencesEditor.commit();
        speedSettingEditor.commit();


        BackupManager backupManager = new BackupManager(this);
        backupManager.dataChanged();


        Intent intent = new Intent(this, TitleScreen.class);
        startActivity(intent);
        this.finish();
    }
}
