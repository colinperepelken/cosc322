package main;

import gameBoard.Board;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.newGame();
		System.out.println(board.toString());
		board.makeMove(true, board.indexify('D', 1), board.indexify('D', 4), board.indexify('D', 7));
		System.out.println(board.toString());
	}

}
