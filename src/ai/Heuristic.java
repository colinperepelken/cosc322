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
		
		boolean isBlack = n.isBlack();
		if(isBlack) { // should come up with something better... check if node is black player
			queens = board.getBlackQueens();
			
		} else {
			queens = board.getWhiteQueens();
		}
		
		// iterate over all queens
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
		return ActionFactory.getActions(n).size();
	}
	
	/**
	 * Return is negative so that highest heuristic value represents state in which enemy has the least moves
	 * @param n Node
	 * @return a negative integer which represents the number of moves an enemy has
	 */
	public static int enemyMoveCounting(Node n) {
		return -moveCounting(n);
	}
	
	/**
	 * Heuristic based on http://www.yorgalily.org/amazons/heuristics.html "Move Counting"
	 * @param n Node
	 * @return value h(n) - # of squares the queens can move to
	 */
	public static int moveCounting(Node n) {
		
		State state = n.getState();
		Board board = state.getBoard(); // get the board object of the state
		
		int[] queens; // array of queens
		
		boolean isBlack = true;
		if (isBlack) { // assume AI is black...
			
			queens = board.getBlackQueens();
			
		} else { // else if AI is white
			
			queens = board.getWhiteQueens();
			
		}
		
		int h = 0;
		for (int i = 0; i < queens.length; i++) { // for every queen of a player
			int index = queens[i];
			while (index < 90) {
				index += 10;
				if (board.isEmpty(index)) {
					h++;
				} else {
					break;
				}
			}
			index = queens[i];
			while (index > 10) {
				index -= 10;
				if (board.isEmpty(index)) {
					h++;
				} else {
					break;
				}
			}
			index = queens[i];
			int max = Integer.parseInt(Board.getRow(queens[i])+"9");
			while (index < max) {
				index++;
				if (board.isEmpty(index)) {
					h++;
				} else {
					break;
				}
			}
			index = queens[i];
			int min = Integer.parseInt(Board.getRow(queens[i]) + "0");
			while (index > min) {
				index--;
				if (board.isEmpty(index)) {
					h++;
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
					h++;
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
					h++;
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
					h++;
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
					h++;
				} else {
					break;
				}
			}
			
		}
		return h;	
	}
}
