package com.de_coder.hamedpa.gamepack.snake;
//Developed by HamedPa

import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;

import com.de_coder.hamedpa.gamepack.R;

public class TitleScreen extends Activity {

    SharedPreferences settings;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        settings = getSharedPreferences("settings", 0);
        if(settings.getInt("theme",0) == 1) setTheme(android.R.style.Theme_Holo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
    }


    public void startGame(View view){
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }


    public void options(View view){
        Intent intent = new Intent(this, OptionsScreen.class);
        startActivity(intent);
        this.finish();
    }

}
