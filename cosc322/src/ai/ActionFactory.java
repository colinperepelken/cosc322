package ai;

import java.util.ArrayList;
import java.util.List;

import gameBoard.Board;

/**
 * Class to generate actions given a state
 */
public class ActionFactory {
	
	/**
	 * Generates valid actions given a board state
	 * @param state
	 * @return a list of valid actions
	 */
	public static ArrayList<Action> getActions(State state) { // should also depend on which player?
		
		ArrayList<Action> actions = new ArrayList<Action>();
		Board board = state.getBoard(); // get the board object of the state
		
		int[] queens; // array of queens
		
		boolean isBlack = true;
		if (isBlack) { // assume AI is black...
			
			queens = board.getBlackQueens();
			
		} else { // else if AI is white
			
			queens = board.getWhiteQueens();
			
		}
		
			
		for (int i = 0; i < queens.length; i++) { // for every queen of a player
			for (int j = 0; j < board.getBoard().length; j++) { // for every valid queen move
				for (int k = 0; k < board.getBoard().length; k++) { // for every valid arrow shot
					if (board.validateMove(false, queens[i], j, k)) { // check if valid
						actions.add(new Action(queens[i], j, k)); // add an action
					}
				}
			}
		}
			

		return actions; // return the list of possible actions for the state
	}
}