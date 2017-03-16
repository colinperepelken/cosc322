package ai;

import java.util.ArrayList;

public class MinimaxSearch {
	
	private int depthLimit;
	
	/**
	 * Constructor
	 * @param depthLimit - how far down the tree are we searching?
	 */
	public MinimaxSearch(int depthLimit) {
		this.depthLimit = depthLimit;
	}
	
	public int minimaxDecision(State state) {
		int max = 0;
		
		Node node = new Node(state);
		ArrayList<Action> actions = ActionFactory.getActions(node); // generate all possible actions for this state
		for (Action action : actions) {
			int value = minValue(state.getSuccessorState(action, node.isWhite()), 0);
			
			if (value > max) {
				max = value;
			}
		}
		
		
		return max;
	}
	
	private int maxValue(State state, int currentDepth) {
		
		Node node = new Node(state); // get node from state
		
		if (currentDepth == depthLimit) return Heuristic.enemyMoveCounting(node); // base case
		
		int v = Integer.MIN_VALUE; // negative infinity
		
		ArrayList<Action> actions = ActionFactory.getActions(node); // generate all possible actions for this state
		for (Action action : actions) {
			int max = v;
			int min = minValue(state.getSuccessorState(action, node.isWhite()), ++currentDepth);
			
			if (max > min) {
				v = max;
			} else {
				v = min;
			}
		}
		System.out.println("Max returning v="+v);
		return v;
	}
	
	private int minValue(State state, int currentDepth) {
		Node node = new Node(state); // get node from state
		
		if (currentDepth == depthLimit) return Heuristic.enemyMoveCounting(node); // base case
		
		int v = Integer.MAX_VALUE; // negative infinity
		
		ArrayList<Action> actions = ActionFactory.getActions(node); // generate all possible actions for this state
		for (Action action : actions) {
			int min = v;
			int max = maxValue(state.getSuccessorState(action, !node.isBlack()), ++currentDepth);
			
			if (max > min) {
				v = min;
			} else {
				v = max;
			}
		}
		System.out.println("Min returning v="+v);
		return v;
	}
}
