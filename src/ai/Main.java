package ai;

import gameBoard.Board;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		//new StateSpace().searchForNextAction(s);
	}

}
