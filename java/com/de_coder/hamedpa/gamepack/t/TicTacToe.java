package com.de_coder.hamedpa.gamepack.t;
//Developed by HamedPa

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {


	protected int userId;
	

	protected int computerId;
	

	protected String[] signs;
	

	protected int[] fields;
	
	protected AI ai;
	

	public TicTacToe(int difficulty) {
		
		this.fields = new int[9];
		for(int i = 0; i < this.fields.length; i++) {
			this.fields[i] = -1;
		}
		
		this.signs = new String[2];
		this.signs[0] = "X";
		this.signs[1] = "O";
		

		this.userId = new Random().nextInt(2);
		System.out.println(this.userId);
		if(this.userId == 0) {
			this.computerId = 1;
		}else{
			this.computerId = 0;
		}
		this.ai = new AI(difficulty, this.userId, this.computerId);
	}
	

	public String getUserSign() {
		return this.signs[this.userId];
	}
	

	public String getComputerSign() {
		return this.signs[this.computerId];
	}
	

	public int getWinnerInteractions() {
		int winnerId;
		switch(this.checkGameComplete()){
			case 0:
				winnerId = this.userId;
				break;
			case 1:
				winnerId = this.computerId;
				break;
			default:
				return 0;
		}
		int count = 0;
		for(int i=0; i < this.fields.length; i++) {
			if(this.fields[i] == winnerId) {
				count++;
			}
		}
		return count;
	}
	

	public void setUserField(int index) {
		this.fields[index] = this.userId;
	}
	

	public int randomStart() {
		if(this.userId == 0) {
			return -1;
		}
		return this.setComputerField();
	}
	

	protected ArrayList<Integer> findAvailableFields() {
		ArrayList<Integer> available = new ArrayList<Integer>();
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				available.add(i);
			}
		}
		return available;
	}
	

	public int setComputerField() {
		
		ArrayList<Integer> available = new ArrayList<Integer>();
		for(int i = 0; i < this.fields.length; i++) {
			if(this.fields[i] == -1) {
				available.add(i);
			}
		}
		
		int fieldIndex = this.ai.getTurn(this.fields, available);
		
		if(fieldIndex >= 0) {
			this.fields[fieldIndex] = this.computerId;
		}
		return fieldIndex;
	}
	
	public int checkGameComplete() {
		return TicTacToe.checkGameComplete(this.fields, this.userId);
	}
	

	public static int checkGameComplete(int[] fields, int playerId) {
		
		for(int i = 0; i < fields.length; i = i + 3) {
			if(fields[i] == fields[i+1] && fields[i] == fields[i+2]){
				if(fields[i] > -1) {
					if(fields[i] == playerId) {
						return 0;
					}else{
						return 1;
					}
				}
			}
		}
		
		for(int i = 0; i < fields.length / 3; i++) {
			if(fields[i] == fields[i+3] && fields[i] == fields[i+6]){
				if(fields[i] > -1) {
					if(fields[i] == playerId) {
						return 0;
					}else{
						return 1;
					}
				}
			}
		}
		
		if(fields[0] == fields[4] && fields[0] == fields[8]){
			if(fields[0] > -1) {
				if(fields[0] == playerId) {
					return 0;
				}else{
					return 1;
				}
			}
		}
		
		if(fields[6] == fields[4] && fields[6] == fields[2]){
			if(fields[6] > -1) {
				if(fields[6] == playerId) {
					return 0;
				}else{
					return 1;
				}
			}
		}
		
		for(int i = 0; i < fields.length; i++) {
			if(fields[i] == -1) {
				return -1;
			}
		}
		
		return 2;
	}
}
