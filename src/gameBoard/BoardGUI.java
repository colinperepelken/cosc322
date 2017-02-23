package gameBoard;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class BoardGUI extends Application {
	// Border Pane
	BorderPane root = new BorderPane();
	// Border Pane -> Grid Pane
	GridPane chessBoard = new GridPane();
	// Initializes the board data-structure
	Board board = new Board();
	final int boardSize = 10;
	int turnCount = 0;
	int[] moves = new int[3];
	int inputs = 0;
	Image blkQueenImage = new Image("blkQueen.png", 64, 64, true, false);
	Image whtQueenImage = new Image("whtQueen.png", 64, 64, true, false);
	Image arrowImage = new Image("arrow.png", 64, 64, true, false);
	// Creates the activePlayer label
	Label activePlayer = new Label("Player: White");

	private void makeMoveAI() {
		// There is one problem with this implementation, the program will not
		// be closable without killing it until the task is complete or
		// 30s have elapsed.

		// Creates an executor that can use a maximum of 4 threads
		ExecutorService executor = Executors.newFixedThreadPool(4);

		// Submits a future to the executor
		Future<?> future = executor.submit(new Runnable() {
			@Override
			// This is the code that the executor will run
			public void run() {

				// ============================================================
				// PUT THE CODE THAT FINDS THE AI MOVE HERE
				// THEN PASS WHAT IT RETURNS INTO THE MAKEAMOVE FUNCTION
				// THAT IS LOCATED RIGHT BELOW THIS
				//
				// ============================================================
				// 30s to execute or it literally shuts down and skips the turn
				// ============================================================

				// Gets the current player based on the turn number
				boolean whitePlayer = false;
				if (turnCount % 2 == 0) {
					whitePlayer = true;
				}

				// boolean success = board.makeMove(whitePlayer, /* START
				// POSITION */, /* END POSITION */, /*ARROW POSITION */);
				// if(success) {
				// turnCount++;
				// }

				// Sends an message to the UI thread saying "When you get a
				// chance please do this"
				// This is what updates the UI, Very Critical
				Platform.runLater(() -> {
					chessBoard.getChildren().clear();
					drawBoard(board);
					if ((turnCount % 2) == 0) {
						activePlayer.setText("Player: White");
					} else {
						activePlayer.setText("Player: Black");
					}
				});
			}
		});
		// This rejects any other commands from being sent to the executor
		executor.shutdown();

		try {
			// Sets the amount of time the task has to execute (30s in this
			// case)
			future.get(30, TimeUnit.SECONDS);
			// Handles job Interruption
		} catch (InterruptedException e) {
			System.out.println("job was interrupted");

			// Unknown Exception Handling
		} catch (ExecutionException e) {
			System.out.println("caught exception: " + e.getCause());

			// This is what happens when 30s has elapsed, cancels the task
			// and prints a timeout to the console
		} catch (TimeoutException e) {
			future.cancel(true);
			System.out.println("30s have elapsed, turn timeout");
			turnCount++;
		}

		// Allows 2s for the task to timeout.
		try {
			if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
				// force them to quit by interrupting
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	private void makeMove(int start, int end, int arrow) {

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				// Gets the current player based on the turn number
				boolean whitePlayer = false;
				if (turnCount % 2 == 0) {
					whitePlayer = true;
				}

				boolean success = board.makeMove(whitePlayer, start, end, arrow);
				if (success == true) {
					turnCount++;
				}

				return null;
			}
		};

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chessBoard.getChildren().clear();
				if ((turnCount % 2) == 0) {
					activePlayer.setText("Player: White");
				} else {
					activePlayer.setText("Player: Black");
				}
				drawBoard(board);
			}
		});

		new Thread(task).start();

	}

	private void drawBoard(Board gameBoard) {

		// Creates the empty game board
		for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
			for (int colIndex = 0; colIndex < boardSize; colIndex++) {

				// Border Pane -> Grid Pane -> Stack Pane
				StackPane squareContainer = new StackPane();
				squareContainer.setId(String.valueOf(rowIndex) + String.valueOf(colIndex));
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
							moves[inputs] = Integer.valueOf(squareContainer.getId());
							System.out.println(squareContainer.getId());
							inputs++;

						}
						if (inputs >= 3) {

							makeMove(moves[0], moves[1], moves[2]);

							inputs = 0;
							drawBoard(board);

						}

					}

				});

				// Border Pane -> Grid Pane -> Stack Pane -> Square
				Region square = new Region();

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

				// The game board will only resize untill it hits this
				// preference so
				// make it huuuge so it will always resize.
				square.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

			}
		}

	}

	// This method basically replaces the main method
	public void start(Stage primaryStage) {

		// Resets the board to a new game
		board.newGame();

		// Draws the initial board-state
		drawBoard(board);

		// Debugging thing
		System.out.println(chessBoard.getChildren().toString());

		// Adds the chessboard to main border pane
		root.setCenter(chessBoard);
		primaryStage.setTitle("COSC322 - Game of the Amazons");

		// Initializes the sidebar and adds the children and sets alignment

		// Border Pane -> VBox
		VBox leftBar = new VBox();
		leftBar.setAlignment(Pos.TOP_CENTER);
		leftBar.getChildren().add(activePlayer);

		// Attaches the side-bar to the main window.
		root.setLeft(leftBar);

		Scene scene = new Scene(root, 1000, 800);

		// Adds the Style sheet
		scene.getStylesheets().add("gameBoard/boardGUI.css");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
