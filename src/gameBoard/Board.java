package gameBoard;

public class Board {

	/*
	 * Attributes 10x10 board = 100 squares. index(0) = (1,10) index(99) =
	 * (10,1)
	 * 
	 * The array indexs map to these board positions
	 */
	// 00|01|02|03|04|05|06|07|08|09|
	// 10|11|12|13|14|15|16|17|18|19|
	// 20|21|22|23|24|25|26|27|28|29|
	// 30|31|32|33|34|35|36|37|38|39|
	// 40|41|42|43|44|45|46|47|48|49|
	// 50|51|52|53|54|55|56|57|58|59|
	// 60|61|62|63|64|65|66|67|68|69|
	// 70|71|72|73|74|75|76|77|78|79|
	// 80|81|82|83|84|85|86|87|88|89|
	// 90|91|92|93|94|95|96|97|98|99|

	// W = White Queen, B = Black Queen, + = Arrow, _ = Empty
	private char[] board = new char[100];

	// Create final references to be used throughout, can easily be changed
	// here.
	final private char EMPTY = '_';
	final private char BLACKQUEEN = 'B';
	final private char WHITEQUEEN = 'W';
	final private char ARROW = '+';

	// Methods
	public char getSquare(int squareIndex) {

		return (board[squareIndex]);

	}

	public void newGame() {
		// Fills the entire board with EMPTY
		for (int i = 0; i < board.length; i++) {
			board[i] = EMPTY;
		}

		// Adds the Black amazons to the game.
		addAmazon(false, chessNotationToIndex('a', 7));
		addAmazon(false, chessNotationToIndex('d', 10));
		addAmazon(false, chessNotationToIndex('g', 10));
		addAmazon(false, chessNotationToIndex('j', 7));
		// Adds the White amazons to the game
		addAmazon(true, chessNotationToIndex('a', 4));
		addAmazon(true, chessNotationToIndex('d', 1));
		addAmazon(true, chessNotationToIndex('g', 1));
		addAmazon(true, chessNotationToIndex('j', 4));
	}

	// Adds an Amazon to the board at a specific postions, true means the player
	// is white, false = black.
	private void addAmazon(boolean playerWhite, int squareIndex) {
		if (board[squareIndex] == EMPTY) {
			if (playerWhite) {
				board[squareIndex] = WHITEQUEEN;
			} else {
				board[squareIndex] = BLACKQUEEN;
			}
		} else {
			System.out.println("This square is not empty, an amazon cannot be placed here");
		}

	}


	public void makeMove(boolean whitePlayer, int startSquare, int endSquare, int arrowSquare) {
		if (validateMove(whitePlayer, startSquare, endSquare, arrowSquare) == true) {
			// Adds a new amazon to the final position if the space is empty
			addAmazon(whitePlayer, endSquare);
			// removes the amazon from the start square
			board[startSquare] = EMPTY;
			//Adds the arrow to the board
			board[arrowSquare] = ARROW;

		}
		else {
			System.out.println("Your move was not valid");
		}

	}

	// Creates a printable version of the board (10 row paragraph string)
	public String toString() {
		StringBuilder boardState = new StringBuilder();
		for (int i = 1; i <= board.length; i++) {
			boardState.append(board[i - 1] + "|");
			if (i % 10 == 0) {
				boardState.append("\n");
			}

		}
		return boardState.toString();
	}

	public int cartesianToIndex(int xPos, int yPos) {
		return (((yPos - 1) * 10) + (xPos - 1));

	}

	public int chessNotationToIndex(char xPos, int yPos) {
		// Converts the char to an int (unicode conversion) A = 10, B = 11
		// ...etc
		int xVal = Character.getNumericValue(Character.toUpperCase(xPos));
		// Converts the char value and y position into an index in the array.
		return (((10 - yPos) * 10) + (xVal - 10));

	}

	public boolean isEmpty(int squareIndex) {
		if (board[squareIndex] == EMPTY) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidQueenMove(int startIndex, int endIndex) {
		// Set to true for testing, to be completed later
		return true;
	}

	private boolean validateMove(boolean whitePlayer, int startIndex, int endIndex, int arrowIndex) {
		boolean hasQueen = false;
		boolean moveSpaceEmpty = false;
		boolean moveValid = false;
		boolean arrowEmpty = false;
		boolean arrowValid = false;

		// Checks to see if there is a queen in the starting position
		if (whitePlayer == true) {
			if (board[startIndex] == WHITEQUEEN) {
				hasQueen = true;
			}
		} else {
			if (board[startIndex] == BLACKQUEEN) {
				hasQueen = true;
			}
		}

		// Checks to see if the target position is a empty
		if (isEmpty(endIndex)) {
			moveSpaceEmpty = true;
		}

		// Validates that the move is possible (follows queenmove rules)
		// TEMPORARILY SET TO TRUE FOR TESTING
		if (isValidQueenMove(startIndex, endIndex)) {
			moveValid = true;
		}

		// Checks if the arrow position is empty
		if (isEmpty(arrowIndex)) {
			arrowEmpty = true;
		}

		// Checks to see if the arrow move is valid (starts at the new position
		// and finishes at the arrow position
		if (isValidQueenMove(endIndex, arrowIndex)) {
			arrowValid = true;
		}

		// Move is only valid if all conditions are met.
		if (hasQueen == true && moveSpaceEmpty == true && moveValid == true && arrowEmpty == true
				&& arrowValid == true) {
			return true;
		} else {
			return false;
		}

	}
}
