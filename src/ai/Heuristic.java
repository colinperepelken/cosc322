package ai;

import gameBoard.Board;

public class Heuristic {
	
	/**
	 * This heuristic determines if a
	 * move leads to a state in which our player owns more of the positions 
	 * than the adversary.
	 * @param node The state to examine.
	 * @return value h(n)
	 */
	public static int mostOwned(Node n) {
		
		State state = n.getState();
		Board board = state.getBoard();
		int h = 0; // heuristic value to be returned
		int[] queens;
		
		boolean isBlack = true;
		if(isBlack) { // should come up with something better... check if node is black player
			queens = board.getBlackQueens();
			
		} else {
			queens = board.getWhiteQueens();
		}
		
		// iterate over all black queens and find one with o how many owned
		for(int q: queens) {
			// need to expand the space as far down as possible and find the most owned branch
			
			
		}
		
		// TODO: IMPLEMENTATION HERE 
		
		return h;
	}
	
	/**
	 * Heuristic based on number of actions available to a state
	 * @param n Node
	 * @return value h(n) - number of actions available
	 */
	public static int mostActionsAvailable(Node n) {
		return ActionFactory.getActions(n.getState()).size();
	}
}
