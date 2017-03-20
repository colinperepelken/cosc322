package gameBoard;

import ai.Action;
import ai.State;
import ai.StateSpace;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import serverCommunications.ServerCommunicator;

public class BoardGUI extends Application {
	// Border Pane
	static BorderPane root = new BorderPane();
	// Border Pane -> Grid Pane
	static GridPane chessBoard = new GridPane();

	// Initializes the input counter.
	static int inputs = 0;

	// Loads the images
	static Image blkQueenImage = new Image("blkQueen.png", 64, 64, true, false);
	static Image whtQueenImage = new Image("whtQueen.png", 64, 64, true, false);
	static Image arrowImage = new Image("arrow.png", 64, 64, true, false);
	
	public static game game;
	
	
	public game getGame() {
		return this.game;
	}
	
	private class player {
		// Attributes
		private boolean whitePlayer;
		private boolean needsUserInput = true;
		private int[] bestMove = new int[3];
		private boolean moveCalcFinished = false;



		public void setWhitePlayer(boolean whitePlayer) {
			this.whitePlayer = whitePlayer;
		}

		public boolean getNeedsUserInput() {
			return needsUserInput;
		}

		public void setNeedsUserInput(boolean needsUserInput) {
			this.needsUserInput = needsUserInput;
		}

		// Constructors
		player(boolean whitePlayer, boolean isHuman) {
			setWhitePlayer(whitePlayer);
			setNeedsUserInput(isHuman);
			
		}

	}

	public class game {
		
		// Initializes the board data-structure
		private Board board;
		// Sets the board size
		final int boardSize;
		// initializes the turn counter
		private int turnCount;
		// Initializes the array that contains moves
		int[] moves;
		private player player1;
		private player player2;

		public player getPlayer1() {
			return player1;
		}

		public void setPlayer1(player player1) {
			this.player1 = player1;
		}

		public player getPlayer2() {
			return player2;
		}

		public void setPlayer2(player player2) {
			this.player2 = player2;
		}

		public int getTurnCount() {
			return turnCount;
		}

		public void setTurnCount(int turnCount) {
			this.turnCount = turnCount;
		}

		public Board getBoard() {
			return this.board;
		}

		public void setBoard(Board board) {
			this.board = board;
		}

		public player getCurrentPlayer() {
			if (turnCount % 2 == 0) {
				return player1;

			} else {
				return player2;
			}
		}

		// Constructors
		game(boolean playerOneHuman, boolean playerTwoHuman) {
			// Initializes the board data-structure
			board = new Board();
			// Sets the board size
			boardSize = 10;
			// initializes the turn counter
			turnCount = 0;
			// Initializes the array that contains moves
			moves = new int[3];
			// Launches the players
			player1 = new player(true, playerOneHuman);
			player2 = new player(false, playerTwoHuman);
		}

	}


	public void makeMove(int start, int end, int arrow, game currentGame) {

				// Gets the current player based on the turn number
				boolean whitePlayer = false;
				if (currentGame.getTurnCount() % 2 == 0) {
					whitePlayer = true;
				}

				boolean success = currentGame.getBoard().makeMove(whitePlayer, start, end, arrow);
				
				
				// If a successful move is made, increment the turn counter by
				// one
				if (success == true) {
					currentGame.setTurnCount(currentGame.getTurnCount() + 1);
					
					
					// AI turn
					//System.out.println("Computing AI move...");
					//ai.State s = new ai.State(currentGame.getBoard());
					//Action a = new StateSpace().searchForNextActionQuickly(s);
					
//					System.out.println(new StateSpace().searchForNextActionQuickly(new State(currentGame.getBoard())).toStringCoordinates());
					//makeMove(a.getQueenStartIndex(), a.getQueenEndIndex(), a.getArrowIndex(), currentGame);
				}

	

	}

	private void drawBoard(game currentGame) {
		Board gameBoard = currentGame.getBoard();
		// Creates the empty game board
		for (int rowIndex = 0; rowIndex < currentGame.boardSize; rowIndex++) {
			for (int colIndex = 0; colIndex < currentGame.boardSize; colIndex++) {

				// Border Pane -> Grid Pane -> Stack Pane
				StackPane squareContainer = new StackPane();
				squareContainer.setId(String.valueOf(rowIndex) + String.valueOf(colIndex));

				// Adds a event handler to each square that will pass the moves
				// into an array as they are clicked on
				// It also gives the containers a new style based on what will
				// occur.

				if (currentGame.getCurrentPlayer().getNeedsUserInput()) {
					squareContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							if (inputs < 3) {
								if (inputs == 0) {
									squareContainer.getChildren().get(0).getStyleClass().add("clicked-start");
								} else {
									squareContainer.getChildren().get(0).getStyleClass().add("clicked-end");
								}
								squareContainer.getId();
								currentGame.moves[inputs] = Integer.valueOf(squareContainer.getId());
								System.out.println(squareContainer.getId());
								inputs++;

							}
							if (inputs >= 3) {

								makeMove(currentGame.moves[0], currentGame.moves[1], currentGame.moves[2], currentGame);
								inputs = 0;

							}

						}

					});
				}

				// Border Pane -> Grid Pane -> Stack Pane -> Square
				Region square = new Region();

				// Reads the Board datastructure and creates the UI based on
				// that.
				if ((rowIndex + colIndex) % 2 == 0) {
					square.getStyleClass().add("charcoal-square");
					squareContainer.getChildren().add(square);

				} else {
					square.getStyleClass().add("ivory-square");
					squareContainer.getChildren().add(square);

				}
				if (gameBoard.getSquare(Integer.valueOf(String.valueOf(rowIndex) + String.valueOf(colIndex))) == '+') {
					ImageView arrow = new ImageView(arrowImage);
					squareContainer.getChildren().add(arrow);
				} else if (gameBoard
						.getSquare(Integer.valueOf(String.valueOf(rowIndex) + String.valueOf(colIndex))) == 'B') {
					// Border Pane -> Grid Pane -> Stack Pane -> (Square +
					// blkQueen)
					ImageView blkQueen = new ImageView(blkQueenImage);
					squareContainer.getChildren().add(blkQueen);

				} else if (gameBoard
						.getSquare(Integer.valueOf(String.valueOf(rowIndex) + String.valueOf(colIndex))) == 'W') {
					// Border Pane -> Grid Pane -> Stack Pane -> (Square +
					// whtQueen)
					ImageView whtQueen = new ImageView(whtQueenImage);
					squareContainer.getChildren().add(whtQueen);

				}
				chessBoard.add(squareContainer, colIndex, rowIndex);
				

				// The game board will only resize until it hits this
				// preference so
				// make it huuuge so it will always resize.
				square.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

			}
		}

	}

	// This method basically replaces the main method
	public void start(Stage primaryStage) {
		
		//TODO: Wrap the server connection in a thread?
		// gets the connection to the server
		//ServerCom connection = new ServerCom("jaredhw", "hunter2");  
		
				
		// Resets the board to a new game (Can be used to set players as AI or not)
		game currentGame = new game(true, true);
		// Resets the internal board game datastructure to a new game
		currentGame.getBoard().newGame();
		this.game = currentGame;
		// Draws the initial board-state
		drawBoard(currentGame);

		// Adds the chessboard to main border pane
		root.setCenter(chessBoard);
		primaryStage.setTitle("COSC322 - Game of the Amazons");

		// Initializes the sidebar and adds the children and sets alignment

		// Border Pane -> VBox
		VBox leftBar = new VBox();
		leftBar.setAlignment(Pos.TOP_CENTER);


		
		
		// menu bar stuff
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		menuBar.getMenus().addAll(menuFile);
		
		// menu item
		MenuItem ai = new MenuItem("AI Compute");
		ai.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Computing AI move...");
				State s = new State(currentGame.getBoard());
				Action a = new StateSpace().searchForNextActionQuickly(s);
				
				//System.out.println(new StateSpace().searchForNextActionQuickly(new State(currentGame.getBoard())).toStringCoordinates());
				makeMove(a.getQueenStartIndex(), a.getQueenEndIndex(), a.getArrowIndex(), currentGame);
			}
		});
		menuFile.getItems().addAll(ai);
		root.setTop(menuBar); // add menu bar to screen
		
		// Attaches the side-bar to the main window.
		root.setLeft(leftBar);

		// Default window size and the root display attached to the window
		Scene scene = new Scene(root, 1000, 800);

		// Adds the Style sheet
		scene.getStylesheets().add("gameBoard/boardGUI.css");
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		ServerCommunicator communicator = new ServerCommunicator("cmoney", "test", this);
	}

	public static void main(String[] args) {
		launch(args);
	}


}
