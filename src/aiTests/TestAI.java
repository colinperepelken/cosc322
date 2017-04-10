package aiTests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ai.Action;
import ai.ActionFactory;
import ai.Heuristic;
import ai.MinimaxSearch;
import ai.Node;
import ai.State;
import ai.StateSpace;
import gameBoard.Board;

/*
 * Unit tests for AI classes and functions.
 */
public class TestAI {
	
	@Test
	public void testMinimax() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		MinimaxSearch alg = new MinimaxSearch(0);
		System.out.println("DECISION: " + alg.minimaxDecision(s));
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
	
		
		// for initial state
		assertEquals(3, actions.get(0).getQueenStartIndex());
		assertEquals(0, actions.get(0).getQueenEndIndex());
		assertEquals(1, actions.get(0).getArrowIndex());
		
	}
	
	@Test
	public void testGenerateChildNodesQuickly() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		Node n = new Node(s);
		new StateSpace().generateChildNodesQuickly(n);
	}
	
	@Test
	public void testSearchForNextAction() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		System.out.println(new StateSpace().searchForNextAction(s).toString());
	}
	
	@Test
	public void testGenerateChildNodes() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		Node n = new Node(s);
		new StateSpace().generateChildNodes(n, 0);
	}
}