package com.de_coder.hamedpa.gamepack.t;
//Developed by HamedPa

import java.util.ArrayList;
import java.util.Random;

public class AI {
	

	protected int level = 0;
	

	protected int[] fields;
	

	protected ArrayList<Integer> available;
	

	protected int playerId;
	

	protected int aiId;
	

	public AI(int level, int playerId, int aiId) {
		this.level = level;
		this.playerId = playerId;
		this.aiId = aiId;
	}
	

	public int getTurn(int[] fields, ArrayList<Integer> available) {
		this.fields = fields;
		this.available = available;
		

		if(available.size() == 0) {
			return -1;
		}
		

		if(available.size() == 1) {
			return available.get(0);
		}
		

		if(this.level >= 1) {
			int winTurn = this.winTurn();
			if(winTurn >= 0) {
				return winTurn;
			}
		}
		

		if(this.level >= 2) {
			int preventUserWinTurn = this.preventUserWinTurn();
			if(preventUserWinTurn >= 0) {
				return preventUserWinTurn;
			}
		}
		
		return this.randomTurn();
	}
	

	protected int randomTurn() {
		int index = new Random().nextInt(this.available.size());
		return this.available.get(index);
	}
	

	protected int winTurn() {
		for(int i = 0; i < available.size(); i++) {
			this.fields[available.get(i)] = this.aiId;
			if(TicTacToe.checkGameComplete(this.fields, this.playerId) == 1) {
				// @TODO this should probably only happen with a specific chance
				return available.get(i);
			}
			this.fields[available.get(i)] = -1;
		}
		return -1;
	}
	

	protected int preventUserWinTurn() {
		for(int i = 0; i < available.size(); i++) {
			this.fields[available.get(i)] = this.playerId;
			if(TicTacToe.checkGameComplete(this.fields, this.playerId) == 0) {
				this.fields[available.get(i)] = this.aiId;
				// @TODO this should probably only happen with a specific chance
				return available.get(i);
			}
			this.fields[available.get(i)] = -1;
		}
		return -1;
	}
}
