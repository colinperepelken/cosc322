package ai;

import java.util.ArrayList;

import gameBoard.Board;

/**
 * Class to generate actions given a state
 */
public class ActionFactory {

	public static ArrayList<Action> getActions(Node node, boolean isBlack) {
		State state = node.getState();
		ArrayList<Action> actions = new ArrayList<Action>();
		Board board = state.getBoard(); // get the board object of the state
		
		int[] queens; // array of queens
		
		if (isBlack) { // assume AI is white rn...
			
			queens = board.getBlackQueens();
			
		} else { // else if AI is white
			
			queens = board.getWhiteQueens();
			
		}
		
			
		for (int i = 0; i < queens.length; i++) { // for every queen of a player
			for (int j = 0; j < board.getBoard().length; j++) { // for every valid queen move
				for (int k = 0; k < board.getBoard().length; k++) { // for every valid arrow shot
					if (board.validateMove(!isBlack, queens[i], j, k)) { // check if valid
						actions.add(new Action(queens[i], j, k)); // add an action
					}
				}
			}
		}
			

		return actions; // return the list of possible actions for the state
	}	
	
	public static ArrayList<Action> getActions(Node node) {
		State state = node.getState();
		ArrayList<Action> actions = new ArrayList<Action>();
		Board board = state.getBoard(); // get the board object of the state
		
		int[] queens; // array of queens
		
		boolean isBlack = node.isBlack();
		if (isBlack) { // assume AI is white rn...
			
			queens = board.getBlackQueens();
			
		} else { // else if AI is white
			
			queens = board.getWhiteQueens();
			
		}
		
			
		for (int i = 0; i < queens.length; i++) { // for every queen of a player
			for (int j = 0; j < board.getBoard().length; j++) { // for every valid queen move
				for (int k = 0; k < board.getBoard().length; k++) { // for every valid arrow shot
					if (board.validateMove(!isBlack, queens[i], j, k)) { // check if valid
						actions.add(new Action(queens[i], j, k)); // add an action
					}
				}
			}
		}
			

		return actions; // return the list of possible actions for the state
	}	
		
	
	/**
	 * Generates valid actions given a board state
	 * 
	 * @param state
	 * @return a list of valid actions
	 */
	public static ArrayList<Action> getActionsQuickly(Node node) { 
		State state = node.getState();
		
		ArrayList<Action> actions = new ArrayList<Action>();
		Board board = state.getBoard(); // get the board object of the state

		int[] queens; // array of queens

		// assume black nodes are min nodes
		boolean isBlack = node.isBlack();
		
		if (isBlack) { // assume AI is black...

			queens = board.getBlackQueens();

		} else { // else if AI is white

			queens = board.getWhiteQueens();

		}
		
		for (int i = 0; i < queens.length; i++) {
			int pos = queens[i];

			// horizontals
			for (int j = pos / 10; j < pos / 10 + 9; j++) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

			// verticals
			for (int j = pos; j < 99; j += 10) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

			for (int j = pos; j > 9; j -= 10) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

			// diagonals
			for (int j = pos; j < 89; j += 11) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

			for (int j = pos; j > 11; j -= 11) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

			for (int j = pos; j < 91; j += 9) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

			for (int j = pos; j > 8; j -= 9) {
				addValidArrowMoves(node.isWhite(), pos, j, board, actions);
			}

		}
		return actions; // return the list of possible actions for the state
	}

	public static void addValidArrowMoves(boolean isWhite, int pos, int j, Board board, ArrayList<Action> actions) {
		for (int k = j / 10; k < j / 10 + 9; k += 1) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}

		for (int k = j; k < 90; k += 10) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}

		for (int k = j; k > 9; k -= 10) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}

		for (int k = j; k < 89; k += 11) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}
		for (int k = j; k > 10; k -= 11) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}
		for (int k = j; k < 91; k += 9) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}
		for (int k = j; k > 8; k -= 9) {
			if (board.validateMove(isWhite, pos, j, k)) { // check if valid
				actions.add(new Action(pos, j, k)); // add an action
			}
		}
	}
}
