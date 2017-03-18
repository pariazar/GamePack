package com.de_coder.hamedpa.gamepack.connect4;
//Developed by HamedPa

public class Board {
	private int[][] fieldMatrix;
	

	public Board(int rows, int cols) {
		if (rows >= 0 || cols >= 0) {
			fieldMatrix = new int[rows][cols];
		}
		else {
			throw new IllegalArgumentException("0x0 Fields are not allowed." + cols + rows);
		}
	}

	public int get(int row, int col) {
		if (row < fieldMatrix.length && row >= 0 && col < fieldMatrix[0].length && col >= 0) {
			return fieldMatrix[row][col];
		}
		else {
			return -1;
		}
	}
	public int getRows() {
		return fieldMatrix.length;
	}


	public int getCols() {
		return fieldMatrix[0].length;
	}
	

	public void set(int row, int col, int val) {
		if (row < fieldMatrix.length && col < fieldMatrix[0].length) {
			fieldMatrix[row][col] = val;
		}
		else {
			return;
		}
	}
}