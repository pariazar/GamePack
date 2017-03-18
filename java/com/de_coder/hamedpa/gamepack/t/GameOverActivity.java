package com.de_coder.hamedpa.gamepack.t;
//Developed by HamedPa

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.de_coder.hamedpa.gamepack.R;

public class GameOverActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.gameover);
		
		Bundle b = getIntent().getExtras();
		
		int result = b.getInt("result");
		int interactions = b.getInt("interactions");
		int userWon = b.getInt("userWon");
		int cpuWon = b.getInt("cpuWon");
		
		CharSequence playerWonLabel;
		CharSequence interactionsText;
		
		switch(result) {

			case 0:
				playerWonLabel = getResources().getString(R.string.you_won);
				
				interactionsText = String.format(getResources().getString(R.string.with_interactions), interactions);;
				break;
			case 1:
				playerWonLabel = getResources().getString(R.string.you_lost);
				interactionsText = String.format(getResources().getString(R.string.with_interactions), interactions);;
				break;
			default:
				playerWonLabel = getResources().getString(R.string.draw);;
				interactionsText = "";
		}
		
		TextView resultLabel = (TextView) findViewById(R.id.result);
		resultLabel.setText(playerWonLabel);
		
		TextView interactionsLabel = (TextView) findViewById(R.id.interactions);
		interactionsLabel.setText(interactionsText);
		
		TextView statsLabel = (TextView) findViewById(R.id.stats);
		statsLabel.setText(userWon + " - " + cpuWon);
	}
	
	public void backAction (View v) {
		Intent i = new Intent();
		setResult(Activity.RESULT_CANCELED, i);
		finish();
	}
	
	public void newGameAction (View v) {
		Intent i = new Intent();
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
}
