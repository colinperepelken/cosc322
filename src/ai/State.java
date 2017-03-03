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
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
}
