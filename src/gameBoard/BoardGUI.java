package gameBoard;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	final int boardSize = 10;
	int turnCount = 0;
	Image blkQueenImage = new Image("blkQueen.png", 64, 64, true, false);
	Image whtQueenImage = new Image("whtQueen.png", 64, 64, true, false);
	Image arrowImage = new Image("arrow.png", 64, 64, true, false);

	private void drawBoard(Board gameBoard) {
		// Creates the empty game board
		for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
			for (int colIndex = 0; colIndex < boardSize; colIndex++) {

				// Border Pane -> Grid Pane -> Stack Pane
				StackPane squareContainer = new StackPane();

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

		// Initializes the board data-structure
		Board board = new Board();

		// Resets the board to a new game
		board.newGame();

		// Draws the initial board-state
		drawBoard(board);

		// Debugging thing
		System.out.println(chessBoard.getChildren().toString());

		// Adds the chessboard to main border pane
		root.setCenter(chessBoard);
		primaryStage.setTitle("COSC322 - Game of the Amazons");

		// Creates a button so the player can make a move
		Button makeMoveButton = new Button("Make a Move");
		makeMoveButton.getStyleClass().add("custom-button");

		// Creates the activePlayer label
		Label activePlayer = new Label("Player: White");

		// Initializes the sidebar and adds the children and sets alignment

		// Border Pane -> VBox
		VBox leftBar = new VBox();
		leftBar.setAlignment(Pos.TOP_CENTER);
		leftBar.getChildren().add(makeMoveButton);
		leftBar.getChildren().add(activePlayer);

		// Attaches the side-bar to the main window.
		root.setLeft(leftBar);

		Scene scene = new Scene(root, 800, 600);

		// Adds the Style sheet
		scene.getStylesheets().add("gameBoard/boardGUI.css");

		primaryStage.setScene(scene);
		primaryStage.show();

		// There is one problem with this implementation, the program will not
		// be closable without killing it untill the task is complete or
		// 30s have elapsed.
		makeMoveButton.setOnAction(event -> {
			// Creates an executor that can use a maximum of 4 threads
			ExecutorService executor = Executors.newFixedThreadPool(4);

			// Submits a future to the executor
			Future<?> future = executor.submit(new Runnable() {
				@Override
				// This is the code that the executor will run
				public void run() {
					// Creates a reader to get the users moves
					Scanner reader = new Scanner(System.in);

					// Gets the current player based on the turn number
					boolean whitePlayer = false;
					if (turnCount % 2 == 0) {
						whitePlayer = true;
					}

					// Asks the player for a start position
					System.out.print("Start Position: ");
					int startPos = reader.nextInt();

					// Asks the player for the goal position
					System.out.print("End Position: ");
					int endPos = reader.nextInt();

					// Asks the user where they want to put the arrow
					System.out.print("Arrow Position: ");
					int arrowPos = reader.nextInt();
					board.makeMove(whitePlayer, startPos, endPos, arrowPos);
					turnCount++;

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	public static void main(String[] args) {
		launch(args);
	}

}
