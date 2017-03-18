package com.de_coder.hamedpa.gamepack.connect4;

//Developed by HamedPa

public class ConnectFour {
	private Player player1;
	private Player player2;
	private Board field;
	

	public ConnectFour(Player player1, Player player2, int rows, int cols) {
		this.player1 = player1;
		this.player2 = player2;
		if ((rows > 3 && cols > 1) || (cols > 3 && rows > 1)) {
			this.field = new Board(rows, cols);
		}
		else {
			throw new IllegalArgumentException("Error! There's not enough room on this field ("
				+ rows + " high and " + cols + " wide) to play Connect Four.");
		}
	}
	

	public int put(int col, Player player) {
		assert (player.equals(player1) || player.equals(player2)) : ("Illegal player " + player);
		if (col < field.getCols() && col >= 0) {
			int freeSpot = -1;
			for (int i = 0; i < field.getRows(); i++) {
				freeSpot = field.get(i, col) == 0 ? i : freeSpot;
			}
			if (freeSpot == -1) {
				throw new IllegalArgumentException("Column " + (col + 1) + " is already full.");
			}
			else {
				int playerID = 0;
				if (player.equals(player1)) {
					playerID = 1;
				}
				else if (player.equals(player2)) {
					playerID = 2;
				}
				field.set(freeSpot, col, playerID);
				return checkWinFrom(freeSpot, col);
			}
		}
		else {
			throw new IllegalArgumentException("Column " + (col + 1) + " is not on the "
				+ "Board.");
		}
	}
	

	public void draw() {
		System.out.print("-");
		for (int j = 0; j < field.getCols(); j++) {
			System.out.print(" - -");
		}
		for (int i = 0; i < field.getRows(); i++) {
			System.out.println();
			System.out.print("|");
			for (int j = 0; j < field.getCols(); j++) {
				System.out.print(" " + getFieldAsChar(i, j) + " |");
			}
			System.out.println();
			System.out.print("-");
			for (int k = 0; k < field.getCols(); k++) {
				System.out.print(" - -");
			}
		}
		System.out.println();
	}
	

	public char getFieldAsChar(int row, int col) {
		int value = field.get(row, col);
		switch (value) {
			case -1:
			case 0:
				return ' ';
			case 1:
				return player1.getColor();
			case 2:
				return player2.getColor();
			default:
				throw new UnsupportedOperationException("Cannot assign board value '" + value 
					+ "' to a player");
		}
	}

	private int checkWinFrom(int row, int col) {
		int rowLength = 0;
		for (int i = -3; i < 4; i++) {
			if (field.get(row + i, col + i) == field.get(row, col)) {
				rowLength++;
				if (rowLength == 4) {
					return field.get(row, col);
				}
			}
			else {
				rowLength = 0;
			}
		}
		for (int i = -3; i < 4; i++) {
			if (field.get(row + i, col - i) == field.get(row, col)) {
				rowLength++;
				if (rowLength == 4) {
					return field.get(row, col);
				}
			}
			else {
				rowLength = 0;
			}
		}
		/* vertical check */
		for (int i = -3; i < 4; i++) {
			if (field.get(row + i, col) == field.get(row, col)) {
				rowLength++;
				if (rowLength == 4) {
					return field.get(row, col);
				}
			}
			else {
				rowLength = 0;
			}
		}
		/* horizontal check */
		for (int i = -3; i < 4; i++) {
			if (field.get(row, col + i) == field.get(row, col)) {
				rowLength++;
				if (rowLength == 4) {
					return field.get(row, col);
				}
			}
			else {
				rowLength = 0;
			}
		}
		return 0;
	}
}