package ai;

import java.util.List;

import serverCommunications.ServerCommunicator;

/**
 * A node in the state space.
 * 
 */
public class Node {
	
	private static enum Types {
		MAX_NODE_TYPE, MIN_NODE_TYPE;
	}
	
	private Node parent; 
	private List<Node> children; 
	private State state; 
	private Action action; 
	public boolean isMax;
	
	/**
	 * Constructor
	 * @param parent The parent node.
	 * @param action The action that was applied to the parent node to generate this.
	 * @param state The state contained in this node.
	 */
	public Node(Node parent, Action action, State state) {
		this.parent = parent;
		this.action = action;
		this.state = state;
		this.isMax = !parent.isMax;
	}
	
	/**
	 * Constructor
	 * @param state The state contained in this node.
	 */
	public Node(State state) {
		this.state = state;
		this.isMax = true;
	}
	
	/* GETTERS AND SETTERS */
	
	public Node getParent() {
		return parent;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public State getState() {
		return state;
	}
	
	public Action getAction() {
		return action;
	}
	
	public boolean isMax() {
		return this.isMax;
	}
	
	public boolean isBlack() {
		return ServerCommunicator.isWhite == false;
	}
	
	public boolean isWhite() {
		return  ServerCommunicator.isWhite == true;
	}
}
