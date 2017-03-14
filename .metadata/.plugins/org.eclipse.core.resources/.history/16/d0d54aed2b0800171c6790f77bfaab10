package ai;

/**
 * Valid moves, generally associated with a state.
 */
public class Action {
	
	private int queenStartIndex;
	private int queenEndIndex;
	private int arrowIndex;
	
	/**
	 * Constructor
	 * @param queenStartIndex Where the queen started on board before action
	 * @param queenEndIndex Where the queen moved to on board after action
	 * @param arrowIndex Where the queen shot the arrow
	 */
	public Action(int queenStartIndex, int queenEndIndex, int arrowIndex) {
		this.queenStartIndex = queenStartIndex;
		this.queenEndIndex = queenEndIndex;
		this.arrowIndex = arrowIndex;
	}
	
	/**
	 * Convert action object to string
	 */
	public String toString() {
		return String.format("Queen Start: %d\nQueen End: %d\nArrow: %d", 
				queenStartIndex, queenEndIndex, arrowIndex);
	}
	
	//////// getters
	
	public int getQueenStartIndex() {
		return queenStartIndex;
	}
	
	public int getQueenEndIndex() {
		return queenEndIndex;
	}
	
	public int getArrowIndex() {
		return arrowIndex;
	}
}
