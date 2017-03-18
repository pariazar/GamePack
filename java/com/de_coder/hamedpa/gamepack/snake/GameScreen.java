package com.de_coder.hamedpa.gamepack.snake;
//Developed by HamedPa

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.de_coder.hamedpa.gamepack.R;

public class GameScreen extends Activity {

    private Game game;
    private FrameLayout frameView;
    private TextView score;
    private Activity mActivity;
    SharedPreferences userPreferences, speedSetting;
    private boolean darkTheme=false,snakeOriented=false,classicMode=false;
    private int speed;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        userPreferences = getSharedPreferences("settings", 0);
        speedSetting = getSharedPreferences("speed", 0);
        if(userPreferences.getInt("theme",0) == 1){
            setTheme(android.R.style.Theme_Holo);
            darkTheme=true;
        }
        if(userPreferences.getInt("view",0) == 1)  classicMode = true;
        if(userPreferences.getInt("controls",0) == 1)  snakeOriented = true;
        speed = speedSetting.getInt("speed", 1);


        super.onCreate(savedInstanceState);
        if(snakeOriented)
            setContentView(R.layout.game_2arrow);
        else
            setContentView(R.layout.game_4arrow);
        mActivity = this;


        score = (TextView) findViewById(R.id.score);
        game = new Game(this,this,score,darkTheme,classicMode,snakeOriented,speed);
        frameView = (FrameLayout) findViewById(R.id.gameFrame);
        frameView.addView(game);

    }


    public void leftClick(View view){
        game.snake.turnLeft();
    }


    public void rightClick(View view){
        game.snake.turnRight();
    }


    public void downClick(View view){
        game.snake.turnDown();
    }


    public void upClick(View view){
        game.snake.turnUp();
    }


    public void gameOver(){

        final CharSequence[] items = {"Play Again","Go Back"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.gameover);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                switch(item){

                    case 0:
                        game.setup();
                        game.invalidate();
                        break;


                    default:
                        mActivity.finish();
                }
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }


    public void pauseGame(){


        if(game.gameOver) return;

        game.snake.stopped = true;

        final CharSequence[] items = {"Continue","Start Over","Go Back"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.paused);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                switch(item){

                    case 1:
                        game.setup();
                        game.invalidate();
                        break;


                    case 2:
                        mActivity.finish();
                        break;


                    default:
                        game.snake.stopped=false;
                        game.invalidate();
                }
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {


        if ((keyCode == KeyEvent.KEYCODE_MENU || keyCode ==  KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0)
            pauseGame();


        if((keyCode == KeyEvent.KEYCODE_DPAD_LEFT) && event.getRepeatCount()==0)
            game.snake.turnLeft();


        if((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) && event.getRepeatCount()==0)
            game.snake.turnRight();

        return true;
    }


    @Override
    public void onPause(){
        super.onPause();
        pauseGame();
    }

}

