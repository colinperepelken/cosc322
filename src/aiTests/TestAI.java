package aiTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ai.Action;
import ai.ActionFactory;
import ai.Heuristic;
import ai.Node;
import ai.State;
import gameBoard.Board;

/*
 * Unit tests for AI classes and functions.
 */
public class TestAI {

	
	/*
	 * assertEquals(expected, actual)
	 */
	
	
	/*
	 * Test heuristic mostActionsAvailable
	 */
	@Test
	public void testMostActionsAvailable() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		Node n = new Node(s);
		assertEquals(640, Heuristic.mostActionsAvailable(n)); // initial state should have 640 actions available to a player
		
		// move a player
		b.makeMove(true, 96, 16, 15);
		s = new State(b);
		n = new Node(s);
		assertEquals(628, Heuristic.mostActionsAvailable(n)); // # actions for black should decrease after this move
	}
	
	/*
	 * Currently just prints out all actions for a given state.
	 */
	@Test
	public void testGetActions() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		List<Action> actions = ActionFactory.getActions(s);
		
		
//		for (Action a : actions) {
//			System.out.println(a.toString());
//		}
		
		// for initial state
		assertEquals(39, actions.get(0).getQueenStartIndex());
		assertEquals(9, actions.get(0).getQueenEndIndex());
		assertEquals(0, actions.get(0).getArrowIndex());
		
		// could write tests to more thouroughly test getActions.....
	}
}
