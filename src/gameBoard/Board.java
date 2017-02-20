package gameBoard;

public class Board {

	/*
	 * Attributes 10x10 board = 100 squares. index(0) = (1,10) index(99) =
	 * (10,1) 
	 * 
	 * The array indexs map to these board positions
	 * 00|01|02|03|04|05|06|07|08|09| 
	 * 10|11|12|13|14|15|16|17|18|19|
	 * 20|21|22|23|24|25|26|27|28|29| 
	 * 30|31|32|33|34|35|36|37|38|39|
	 * 40|41|42|43|44|45|46|47|48|49| 
	 * 50|51|52|53|54|55|56|57|58|59|
	 * 60|61|62|63|64|65|66|67|68|69| 
	 * 70|71|72|73|74|75|76|77|78|79|
	 * 80|81|82|83|84|85|86|87|88|89| 
	 * 90|91|92|93|94|95|96|97|98|99|
	 * 
	 */

	// W = White Queen, B = Black Queen, + = Arrow, _ = Empty
	private char[] board = new char[100];

	// Methods
	public char getSquare(int squareIndex) {

		return (board[squareIndex]);

	}

	public void newGame() {
		for (int i = 0; i < board.length; i++) {
			// Fills the entire board with "emptys"
			board[i] = '_';

			// Adds the Black amazons to the game.
			addAmazon(false, indexify('a', 7));
			addAmazon(false, indexify('d', 10));
			addAmazon(false, indexify('g', 10));
			addAmazon(false, indexify('j', 7));
			// Adds the White amazons to the game
			addAmazon(true, indexify('a', 4));
			addAmazon(true, indexify('d', 1));
			addAmazon(true, indexify('g', 1));
			addAmazon(true, indexify('j', 4));

		}
	}
	//Adds an Amazon to the board at a specific postions, true means the player is white, false = black.
	private void addAmazon(boolean playerWhite, int squareIndex) {
		if (playerWhite) {
			board[squareIndex] = 'W';
		} else {
			board[squareIndex] = 'B';
		}

	}

	// public void makeMove(boolean player, startSquare, endSquare, arrowSquare)
	// {

	// }

	// Creates a printable version of the board (10 row paragraph string)
	public String toString() {
		StringBuilder boardState = new StringBuilder();
		for (int i = 1; i <= board.length; i++) {
			boardState.append(board[i-1] + "|");
			if (i % 10 == 0) {
				boardState.append("\n");
			}

		}
		return boardState.toString();
	}

	public int indexify(int xPos, int yPos) {
		return (((yPos - 1) * 10) + (xPos - 1));

	}

	public int indexify(char xPos, int yPos) {
		// Converts the char to an int (unicode conversion) A = 10, B = 11
		// ...etc
		int xVal = Character.getNumericValue(Character.toUpperCase(xPos));
		// Converts the char value and y position into an index in the array.
		return (((10 - yPos) * 10) + (xVal - 10));

	}

	public boolean validateMove(boolean player, int startIndex, int endIndex, int arrowIndex) {
		return false;

	}

}
