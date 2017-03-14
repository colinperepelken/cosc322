package ai;

import java.util.List;

/**
 * A node in the state space.
 * 
 */
public class Node {
	private Node parent; 
	private List<Node> children; 
	private State state; 
	private Action action; 
	
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
	}
	
	/**
	 * Constructor
	 * @param state The state contained in this node.
	 */
	public Node(State state) {
		this.state = state;
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
}
