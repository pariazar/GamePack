package com.de_coder.hamedpa.gamepack.connect4;
//Developed by HamedPa

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.de_coder.hamedpa.gamepack.R;

public class QuadConActivity extends Activity {
    private ConnectFour c4;
    private Player player_1;
    private Player player_2;
    private Player currentPlayer;
    private TableLayout field;
    private AlertDialog gameEnded;
    private Toast p1Toast;
    private Toast p2Toast;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quad_con);
        if (c4 == null) {
            player_1 = new Player("1", 'X');
            player_2 = new Player("2", 'O');
            currentPlayer = player_1;
            field = (TableLayout) findViewById(R.id.tableLayout1);
            p1Toast = Toast.makeText(getApplicationContext(), "Player 1's turn (X)", Toast.LENGTH_SHORT);
            p2Toast = Toast.makeText(getApplicationContext(), "Player 2's turn (O)", Toast.LENGTH_SHORT);
            gameEnded = new AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setMessage("The Game is over.")
                    .setPositiveButton("Start New Game", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            c4 = new ConnectFour(player_1, player_2, 6, 7);
                            currentPlayer = player_1;
                            updateField(field);
                        }
                    })
                    .create();

            AlertDialog onCreateDialog = new AlertDialog.Builder(this)
                    .setTitle("Welcome to Connect 4!")
                    .setMessage("Hello there.\nThis is a two player game of 'Connect 4' - use the buttons to drop a token " +
                            "into any slot.\nThe symbol on the button indicates whose turn it is.")
                    .setPositiveButton("Start the game", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            c4 = new ConnectFour(player_1, player_2, 6, 7);
                        }
                    }).show();
            setButtonListeners();
        }
    }

    @Override
    public boolean
    onMenuItemSelected(int featureID, MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Really restart?")
                .setMessage("All progess in this round *will* be lost")
                .setPositiveButton("Yes, restart.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        c4 = new ConnectFour(player_1, player_2, 6, 7);
                        currentPlayer = player_1;
                        updateField(field);
                    }
                })
                .setNegativeButton("No, Continue", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .show();
        return super.onMenuItemSelected(featureID, item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setButtonListeners() {
        ((Button) findViewById(R.id.button1)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(0);
            }
        });
        ((Button) findViewById(R.id.button2)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(1);
            }
        });
        ((Button) findViewById(R.id.button3)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(2);
            }
        });
        ((Button) findViewById(R.id.button4)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(3);
            }
        });
        ((Button) findViewById(R.id.button5)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(4);
            }
        });
        ((Button) findViewById(R.id.button6)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(5);
            }
        });
        ((Button) findViewById(R.id.button7)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                dropIntoColumn(6);
            }
        });
    }


    private void switchPlayer() {
        if (currentPlayer == player_1) {
            currentPlayer = player_2;
            setButtonSymbol(Character.toString(player_2.getColor()));
            p2Toast.show();
        } else {
            currentPlayer = player_1;
            setButtonSymbol(Character.toString(player_1.getColor()));
            p1Toast.show();
        }
    }

    private void setButtonSymbol(String sym) {
        ((Button) findViewById(R.id.button1)).setText(sym);
        ((Button) findViewById(R.id.button2)).setText(sym);
        ((Button) findViewById(R.id.button3)).setText(sym);
        ((Button) findViewById(R.id.button4)).setText(sym);
        ((Button) findViewById(R.id.button5)).setText(sym);
        ((Button) findViewById(R.id.button6)).setText(sym);
        ((Button) findViewById(R.id.button7)).setText(sym);
    }

    private void dropIntoColumn(int i) {
        int tmp = -1;
        try {
            tmp = c4.put(i, currentPlayer);
        } catch (IllegalArgumentException e) {};
        updateField(field);
        switch (tmp) {
            case 0:
                switchPlayer();
                break;
            case 1:
                gameEnded.setMessage("Player 1 (X) won this game.");
                gameEnded.show();
                break;
            case 2:
                gameEnded.setMessage("Player 2 (O) won this game.");
                gameEnded.show();
            default:

        }
    }

    public void updateField(TableLayout tl) {
        for (int i = 5; i >= 0; i--) {
            for(int j = 0; j < 7; j++) {
                ((TextView) ((TableRow) tl.getChildAt(i)).getChildAt(j)).setText(Character.toString(c4.getFieldAsChar(i, j)));
            }
        }
    }
}
//Developed by HamedPa
