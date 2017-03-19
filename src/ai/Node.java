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
	private Types type;
	
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
		this.type = (parent.type == Node.Types.MAX_NODE_TYPE) ? Node.Types.MIN_NODE_TYPE :  Node.Types.MAX_NODE_TYPE; 
	}
	
	/**
	 * Constructor
	 * @param state The state contained in this node.
	 */
	public Node(State state) {
		this.state = state;
		this.type = Node.Types.MAX_NODE_TYPE;
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
	
	public Types getType() {
		return this.type;
	}
	
	public boolean isBlack() {
		return ServerCommunicator.isWhite == false;
	}
	
	public boolean isWhite() {
		return  ServerCommunicator.isWhite == true;
	}
}
