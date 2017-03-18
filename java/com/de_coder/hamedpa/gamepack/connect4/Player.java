package com.de_coder.hamedpa.gamepack.connect4;

//Developed by HamedPa

public class Player {
	private char color;
	private String name;
	

	public Player(String name, char color) {
		this.name = name;
		this.color = color;
	}
	public void setColor(char color) {
		this.color = color;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getColor() {
		return this.color;
	}
	public String getName() {
		return this.name;
	}
	public boolean equals(Player player) {
		if (player.name == this.name && player.color == this.color) {
			return true;
		}
		else {
			return false;
		}
	}
}