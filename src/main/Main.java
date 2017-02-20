package main;

import gameBoard.Board;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.newGame();
		System.out.println(board.toString());
	}

}
