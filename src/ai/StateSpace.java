package ai;

import java.util.ArrayList;

public class StateSpace {
	private Node maxUtilityNode;	
	private int bestDepth = Integer.MAX_VALUE;
	private int alpha = 0;
	private int beta = 0;
	public static int turnCounter = 0;

	// accepts a state representing the current arrangement of the board
	// action available according to the evaluation function
	public Action searchForNextAction(State seed) {
		// create a root node with a null state and action for backtracking
		// stopping condition
		Node root = new Node(seed);
		Action nextMove;

		// recursively generate the state space
		generateChildNodes(root, 0);

		// case that the search found a node with worthwhile utility
		if (this.maxUtilityNode != null) {

			// backtrack to the successor node
			while (this.maxUtilityNode.getParent().getParent() != null) {
				this.maxUtilityNode = this.maxUtilityNode.getParent();
			}

		} else {
			// if no terminal nodes were found, just return the first movie
			// returned by getActions()
			generateChildNodesQuickly(root);
		}

		// set the nextMove that produced the maxUtilityNode branch
		nextMove = maxUtilityNode.getAction();

		// return the nextMove decided on in this way
		return nextMove;
	}
	
	/*
	 * Uses the generate nodes quickly method
	 */
	public Action searchForNextActionQuickly(State seed) {
		this.maxUtilityNode = null;
		Node root = new Node(seed);
		Action nextMove;
		
		generateChildNodesQuickly(root);
		nextMove = maxUtilityNode == null ? null : maxUtilityNode.getAction();
		
		turnCounter++;
		return nextMove;
	}

	public void generateDefaultNode(Node seed) {
		ArrayList<Action> parentActions = ActionFactory.getActions(seed);
		Action action = parentActions.get(0);
		this.maxUtilityNode = new Node(seed, action, seed.getState().getSuccessorState(action));
	}

	public void generateChildNodesQuickly(Node seed) {
		ArrayList<Action> parentActions = ActionFactory.getActions(seed);
		State parentState = seed.getState();
		Node child;

		int movesAvailable;
		int maxMoves = Integer.MIN_VALUE;

		// add all of the seeds successor nodes to the state space
		for (Action action : parentActions) {
			// get the successor state for that action
			child = new Node(parentState.getSuccessorState(action, !new Node(parentState).isBlack()));
			movesAvailable = Heuristic.enemyMoveCounting(child) + Heuristic.moveCounting(child, child.isBlack())/2;
			//movesAvailable = Heuristic.moveCounting(child, child.isBlack()) + Heuristic.enemyMoveCounting(child);
			if (movesAvailable > maxMoves) {
				System.out.println("new max: " + movesAvailable);
				System.out.println("isBlack == " + child.isBlack());
				maxMoves = movesAvailable;
				this.maxUtilityNode = new Node(seed, action, child.getState());
			}

		}

	}

	// Recursively generate the state space rooted at the seed node
	public void generateChildNodes(Node seed, int currentDepth) {
		State parentState = seed.getState();
		int depthBound = (turnCounter < 35) ? 3 : 3;
		
		if (currentDepth > depthBound) {
			return;
		}
		
		ArrayList<Action> parentActions = ActionFactory.getActions(seed);
		Node child;
		int movesAvailable = Integer.MIN_VALUE;
		int maxMoves = Integer.MIN_VALUE;

		if (parentActions.size() > 0) {
			// add all of the seeds successor nodes to the state space
			for (Action parentAction : parentActions) {
				// get the successor state for that action
				child = new Node(parentState.getSuccessorState(parentAction, !new Node(parentState).isBlack()));
				movesAvailable = Heuristic.enemyMoveCounting(child) + Heuristic.moveCounting(child, child.isBlack())/2;
				//movesAvailable = Heuristic.moveCounting(child, child.isBlack()) + Heuristic.enemyMoveCounting(child);
				if (movesAvailable > maxMoves) {
					System.out.println("new max: " + movesAvailable);
					System.out.println("isBlack == " + child.isBlack());
					maxMoves = movesAvailable;
					this.maxUtilityNode = new Node(seed, parentAction, child.getState());
					generateChildNodes(child, ++currentDepth);
				}
			}
		} 
	}

}
