package aiTests;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import ai.Action;
import ai.ActionFactory;
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
	public void testActionFactory() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testMinimaxSearch() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetActions() {
		ActionFactory af = new ActionFactory();
		Board b = new Board();
		b.newGame();
		State s = new State(b);
		List<Action> actions = af.getActions(s);
		
		
		for (Action a : actions) {
			System.out.println(a.toString());
		}
	}
}
