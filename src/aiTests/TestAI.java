package aiTests;

import static org.junit.Assert.fail;

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

	@Test
	public void testEvaluationFunction() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testHeuristicFunction() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testMinimaxSearch() {
		fail("Not yet implemented");
	}
	
	/*
	 * Test heuristic mostActionsAvailable
	 * Currently just prints out, does not assert.
	 */
	@Test
	public void testMostActionsAvailable() {
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		Node n = new Node(s);
		System.out.println(Heuristic.mostActionsAvailable(n));
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
		
		
		for (Action a : actions) {
			System.out.println(a.toString());
		}
	}
}
