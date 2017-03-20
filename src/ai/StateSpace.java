package ai;

import java.util.ArrayList;

import serverCommunications.ServerCommunicator;

public class StateSpace {
	private Node maxUtilityNode = null;
	private int bestDepth = Integer.MAX_VALUE;
	private int alpha = Integer.MIN_VALUE;
	private int beta = Integer.MAX_VALUE;

	// accepts a state representing the current arrangement of the board
	// action available according to the evaluation function
	public void searchForNextAction(State seed, ServerCommunicator com) {
		// create a root node with a null state and action for backtracking
		// stopping condition
		Node root = new Node(seed);
		
		// recursively generate the state space
		boolean running = generateChildNodes(root, 0);
		
		// case that the search found a node with worthwhile utility
		if (this.maxUtilityNode == null) {
			// if no terminal nodes were found, just return the first movie
			// returned by getActions()
			generateChildNodesQuickly(root);

		} else {
			// backtrack to the successor node
			while (this.maxUtilityNode.getParent() != null) {
				System.out.println(maxUtilityNode);
				this.maxUtilityNode = this.maxUtilityNode.getParent();
				if(this.maxUtilityNode.getParent().getParent() == null) {
					break;
				}
			}

		}
		// set the nextMove that produced the maxUtilityNode branch
		Action nextMove = this.maxUtilityNode.getAction() == null ? this.maxUtilityNode.getAction() : searchForNextActionQuickly(seed, com);
		
		
		if(nextMove == null) {
			System.out.println(this.maxUtilityNode);
			System.out.println("shits null");
		}
		
		if(nextMove == null) {
			System.exit(0);
		}
		
		System.out.println(nextMove.toString());
		
		// return the nextMove decided on in this way
		com.boardGUI.makeMove(nextMove.getQueenStartIndex(), nextMove.getQueenEndIndex(), nextMove.getArrowIndex(), com.boardGUI.getGame());
		com.playerMove(nextMove.getQueenStartIndex(), nextMove.getQueenEndIndex(), nextMove.getArrowIndex());
	}
	
	/*
	 * Uses the generate nodes quickly method
	 */
	public Action searchForNextActionQuickly(State seed, ServerCommunicator com) {
		Node root = new Node(seed);
		Action nextMove;
		
		generateChildNodesQuickly(root);
		nextMove = maxUtilityNode.getAction();
		
		// return the nextMove decided on in this way
		com.boardGUI.makeMove(nextMove.getQueenStartIndex(), nextMove.getQueenEndIndex(), nextMove.getArrowIndex(), com.boardGUI.getGame());
		com.playerMove(nextMove.getQueenStartIndex(), nextMove.getQueenEndIndex(), nextMove.getArrowIndex());
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
			movesAvailable = Heuristic.moveCounting(child, child.isBlack());
			if (movesAvailable > maxMoves) {
				maxMoves = movesAvailable;
				this.maxUtilityNode = new Node(seed, action, child.getState());
			}

		}

	}

	// Recursively generate the state space rooted at the seed node
	public boolean generateChildNodes(Node seed, int currentDepth) {
		if(currentDepth >= 2) {
			return false;
		}
		
		++currentDepth;
		
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
				Node newSeed = new Node(seed, parentAction, childState);

				// currently just finding the node with the most actions
				// available below depth limit
				// as starting point for backtracking
				int numActions = Heuristic.enemyMoveCounting(newSeed);
				this.maxUtilityNode = newSeed;
				if(newSeed.isMax() && numActions > this.alpha) {
					this.maxUtilityNode = newSeed;
				}
				
				else if(!newSeed.isMax() && numActions < this.beta) {
					this.maxUtilityNode = newSeed;
				}
				
				generateChildNodes(newSeed, currentDepth);

//				if(newSeed.isMax() && numActions > this.alpha) {
//					this.alpha = numActions;
//					this.maxUtilityNode = newSeed;
 //				}
//				
//				else if (!newSeed.isMax() && numActions < this.beta) {
//					this.beta = numActions;
//					generateChildNodes(newSeed, currentDepth);
//				}
			}
			return true;
		} else {
			// case we have hit a terminal node - no actions possible
			// TODO set the max utility node for athis class if the eval is
			// better
			// currently just using depth as heuristic
			if (seed.isMax()) {
				// case the opponent cannot move
				this.maxUtilityNode = seed;
			}
			return false;
		}
	}

}
