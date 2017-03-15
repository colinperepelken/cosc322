package aiTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ai.Action;
import ai.ActionFactory;
import ai.Heuristic;
import ai.Node;
import ai.State;
import ai.StateSpace;
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
		assertEquals(2048, Heuristic.mostActionsAvailable(n)); // initial state should have 640 actions available to a player
		
		// move a player
		b.makeMove(true, 96, 16, 15);
		s = new State(b);
		n = new Node(s);
		assertEquals(1580, Heuristic.mostActionsAvailable(n)); // # actions for black should decrease after this move
	}
	
	/*
	 * Currently just prints out all actions for a given state.
	 */
	@Test
	public void testGetActions() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		Node n = new Node(s);
		List<Action> actions = ActionFactory.getActions(n);
		
		
//		for (Action a : actions) {
//			System.out.println(a.toString());
//		}
		
		// for initial state
		assertEquals(3, actions.get(0).getQueenStartIndex());
		assertEquals(0, actions.get(0).getQueenEndIndex());
		assertEquals(1, actions.get(0).getArrowIndex());
		
		// could write tests to more thoroughly test getActions.....
	}
	
//	@Test
//	public void testGenerateChildNodesQuickly() {
//		Board b = new Board();
//		b.newGame();
//		State s = new State(b);
//		Node n = new Node(s);
//		new StateSpace().generateChildNodesQuickly(n);
//	}
	
	@Test
	public void testSearchForNextAction() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		System.out.println(new StateSpace().searchForNextAction(s).toString());
	}
	
//	@Test
//	public void testGenerateChildNodes() {
//		Board b = new Board();
//		b.newGame();
//		State s = new State(b);
//		Node n = new Node(s);
//		new StateSpace().generateChildNodes(n, 0);
//	}
}