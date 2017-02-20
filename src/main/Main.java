package main;

import gameBoard.Board;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.newGame();
		System.out.println(board.toString());
		board.makeMove(true, board.chessNotationToIndex('D', 1), board.chessNotationToIndex('D', 4), board.chessNotationToIndex('D', 7));
		System.out.println(board.toString());
	}

}
