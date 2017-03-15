package ai;

import java.util.ArrayList;

import gameBoard.Board;

public class ActionFactory {

	/**
	 * Generates valid actions given a board state
	 * 
	 * @param state
	 * @return a list of valid actions
	 */
	public static ArrayList<Action> getActions(State state) { // should also
																// depend on
																// which player?

		ArrayList<Action> actions = new ArrayList<Action>();
		Board board = state.getBoard(); // get the board object of the state

		int[] queens; // array of queens

		boolean isBlack = true;
		if (isBlack) { // assume AI is black...

			queens = board.getBlackQueens();

		} else { // else if AI is white

			queens = board.getWhiteQueens();

		}

//		for (int i = 0; i < queens.length; i++) {
//			int pos = queens[i];

//			// horizontals
//			for (int j = pos / 10; j < pos / 10 + 9; j++) {
//				addValidArrowMoves(pos, j, board, actions);
//			}
//
//			// verticals
//			for (int j = pos; j < 99; j += 10) {
//				addValidArrowMoves(pos, j, board, actions);
//			}
//
//			for (int j = pos; j > 9; j -= 10) {
//				addValidArrowMoves(pos, j, board, actions);
//			}

//			// diagonals
//			for (int j = pos; j < 89; j += 11) {
//				addValidArrowMoves(pos, j, board, actions);
//			}
//
//			for (int j = pos; j > 11; j -= 11) {
//				addValidArrowMoves(pos, j, board, actions);
//			}
//
//			for (int j = pos; j < 91; j += 9) {
//				addValidArrowMoves(pos, j, board, actions);
//			}
//
//			for (int j = pos; j > 8; j -= 9) {
//				addValidArrowMoves(pos, j, board, actions);
//			}
			
			
			for (int i = 0; i < queens.length; i++) { // for every queen of a player
				int index = queens[i];
				while (index < 90) {
					index += 10;
					if (board.isEmpty(index)) {
						addValidArrowMoves(queens[i], index, board, actions);
					} else {
						break;
					}
				}
				index = queens[i];
				while (index > 10) {
					index -= 10;
					if (board.isEmpty(index)) {
						addValidArrowMoves(queens[i], index, board, actions);
					} else {
						break;
					}
				}
				index = queens[i];
				int max = Integer.parseInt(Board.getRow(queens[i])+"9");
				while (index < max) {
					index++;
					if (board.isEmpty(index)) {
						addValidArrowMoves(queens[i], index, board, actions);
					} else {
						break;
					}
				}
				index = queens[i];
				int min = Integer.parseInt(Board.getRow(queens[i]) + "0");
				while (index > min) {
					index--;
					if (board.isEmpty(index)) {
						addValidArrowMoves(queens[i], index, board, actions);
					} else {
						break;
					}
				}
			
			
			index = queens[i];
			int startRow = Board.getRow(index);
			int startCol = Board.getColumn(index);
			int r = startRow;
			int c = startCol;
			while (r > 0 && c < 9) { // going up and right
				r--;
				c++; // ayy C++
				if (board.isEmpty(Board.getIndex(r, c))) {
					addValidArrowMoves(queens[i], Board.getIndex(r, c), board, actions);
				} else {
					break;
				}
			}
			r = startRow;
			c = startCol;
			while (r > 0 && c > 0) { // going up and left
				r--;
				c--; 
				if (board.isEmpty(Board.getIndex(r, c))) {
					addValidArrowMoves(queens[i], Board.getIndex(r, c), board, actions);
				} else {
					break;
				}
			}
			r = startRow;
			c = startCol;
			while (r < 9 && c > 0) { // going down and left
				r++;
				c--; 
				if (board.isEmpty(Board.getIndex(r, c))) {
					addValidArrowMoves(queens[i], Board.getIndex(r, c), board, actions);
				} else {
					break;
				}
			}
			r = startRow;
			c = startCol;
			while (r < 9 && c < 9) { // going down and right
				r++;
				c++; 
				if (board.isEmpty(Board.getIndex(r, c))) {
					addValidArrowMoves(queens[i], Board.getIndex(r, c), board, actions);
				} else {
					break;
				}
			}
			

		}

		return actions; // return the list of possible actions for the state
	}

	public static void addValidArrowMoves(int pos, int j, Board board, ArrayList<Action> actions) {
		int index = j;
		while (index < 90) {
			index += 10;
			if (board.isEmpty(index)) {
				if (board.validateMove(false, pos, j, index)) {
					actions.add(new Action(pos, j, index)); // add an action
				}
			} else {
				break;
			}
		}
		index = j;
		while (index > 10) {
			index -= 10;
			if (board.isEmpty(index)) {
				if (board.validateMove(false, pos, j, index)) {
					actions.add(new Action(pos, j, index)); // add an action
				}
			} else {
				break;
			}
		}
		index = j;
		int max = Integer.parseInt(Board.getRow(pos)+"9");
		while (index < max) {
			index++;
			if (board.isEmpty(index)) {
				if (board.validateMove(false, pos, j, index)) {
					actions.add(new Action(pos, j, index)); // add an action
				}
			} else {
				break;
			}
		}
		index = j;
		int min = Integer.parseInt(Board.getRow(pos) + "0");
		while (index > min) {
			index--;
			if (board.isEmpty(index)) {
				if (board.validateMove(false, pos, j, index)) {
					actions.add(new Action(pos, j, index)); // add an action
				}
			} else {
				break;
			}
		}

//		for (int k = j; k < 89; k += 11) {
//			if (board.validateMove(false, pos, j, k)) { // check if valid
//				actions.add(new Action(pos, j, k)); // add an action
//			}
//		}
//		for (int k = j; k > 10; k -= 11) {
//			if (board.validateMove(false, pos, j, k)) { // check if valid
//				actions.add(new Action(pos, j, k)); // add an action
//			}
//		}
//		for (int k = j; k < 91; k += 9) {
//			if (board.validateMove(false, pos, j, k)) { // check if valid
//				actions.add(new Action(pos, j, k)); // add an action
//			}
//		}
//		for (int k = j; k > 8; k -= 9) {
//			if (board.validateMove(false, pos, j, k)) { // check if valid
//				actions.add(new Action(pos, j, k)); // add an action
//			}
//		}
		index = j;
		int startRow = Board.getRow(index);
		int startCol = Board.getColumn(index);
		int r = startRow;
		int c = startCol;
		while (r > 0 && c < 9) { // going up and right
			r--;
			c++; // ayy C++
			if (board.isEmpty(Board.getIndex(r, c))) {
				if (board.validateMove(false, pos, j, Board.getIndex(r, c))) {
					actions.add(new Action(pos, j, Board.getIndex(r, c))); // add an action
				}
			} else {
				break;
			}
		}
		r = startRow;
		c = startCol;
		while (r > 0 && c > 0) { // going up and left
			r--;
			c--; 
			if (board.isEmpty(Board.getIndex(r, c))) {
				if (board.validateMove(false, pos, j, Board.getIndex(r, c))) {
					actions.add(new Action(pos, j, Board.getIndex(r, c))); // add an action
				}
			} else {
				break;
			}
		}
		r = startRow;
		c = startCol;
		while (r < 9 && c > 0) { // going down and left
			r++;
			c--; 
			if (board.isEmpty(Board.getIndex(r, c))) {
				if (board.validateMove(false, pos, j, Board.getIndex(r, c))) {
					actions.add(new Action(pos, j, Board.getIndex(r, c))); // add an action
				}
			} else {
				break;
			}
		}
		r = startRow;
		c = startCol;
		while (r < 9 && c < 9) { // going down and right
			r++;
			c++; 
			if (board.isEmpty(Board.getIndex(r, c))) {
				if (board.validateMove(false, pos, j, Board.getIndex(r, c))) {
					actions.add(new Action(pos, j, Board.getIndex(r, c))); // add an action
				}
			} else {
				break;
			}
		}
	}
}