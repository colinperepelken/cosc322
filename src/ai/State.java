package ai;

import gameBoard.Board;

/**
 * Stores information about this state
 */
public class State {
	private Board board;
	
	/**
	 * Constructor
	 * @param board The game board associated with this state.
	 */
	public State(Board board) {
		// instantiates a new instance of a board and clones the inputed board's arrangement
		this.board = board;
	}
	
	// produce a successor board arrangement without mutating the one in this stack frame
	public State getSuccessorState(Action action) {
		char[] newArragment = this.board.getBoard().clone();
		Board newBoard = new Board();
		newBoard.setBoard(newArragment);
		return new State(newBoard);
	}
	
	public Board getBoard() {
		return this.board;
	}
}
