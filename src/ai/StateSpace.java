package ai;

import java.util.ArrayList;

public class StateSpace {
	private Node maxUtilityNode;
	private int bestDepth = Integer.MAX_VALUE;
	private int alpha = 0;
	private int beta = 0;

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
			child = new Node(parentState.getSuccessorState(action));

			// keep a pointer to the
			movesAvailable = Heuristic.mostActionsAvailable(child);
			if (movesAvailable > maxMoves) {
				maxMoves = movesAvailable;
				this.maxUtilityNode = new Node(seed, action, child.getState());
			}

		}

	}

	// Recursively generate the state space rooted at the seed node
	public void generateChildNodes(Node seed, int currentDepth) {
		State parentState = seed.getState();

		ArrayList<Action> parentActions = ActionFactory.getActions(seed);
		State childState;

		if (parentActions.size() > 0) {
			// add all of the seeds successor nodes to the state space
			for (Action parentAction : parentActions) {
				// get the successor state for that action
				childState = parentState.getSuccessorState(parentAction);

				/*
				 * add a new node to the state space containing of the outcome
				 * of this action on the arrangement
				 */
				// currently using the depth as a evaluation function for
				// pruning
				if (currentDepth < this.bestDepth && currentDepth < 12) {
					Node newSeed = new Node(seed, parentAction, childState);

					// currently just finding the node with the most actions
					// available below depth limit
					// as starting point for backtracking
					int numActions = Heuristic.mostActionsAvailable(newSeed);
					if (newSeed.isWhite() && numActions > this.alpha) {
						this.alpha = numActions;
						this.maxUtilityNode = newSeed;
					}
					
					generateChildNodes(newSeed, ++currentDepth);
				}
			}
		} else {
			// case we have hit a terminal node - no actions possible
			// TODO set the max utility node for athis class if the eval is
			// better
			// currently just using depth as heuristic
			if (currentDepth < bestDepth && seed.isBlack()) {
				// case the opponent cannot move
				this.bestDepth = currentDepth;
				this.maxUtilityNode = seed;
			}
		}
	}

}
