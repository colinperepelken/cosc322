package serverCommunications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.Action;
import ai.State;
import ai.StateSpace;
import gameBoard.Board;
import gameBoard.BoardGUI;
import gameBoard.BoardGUI.game;
import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
import ygraphs.ai.smart_fox.games.GameClient;
import ygraphs.ai.smart_fox.games.GamePlayer;

//Gameclient will handle login and sending messages, gameplayer handles receiving moves 
public class ServerCommunicator extends GamePlayer implements SendMoveCallback {
	private GameClient gameClient;
	public String userName = null;
	private boolean gameStarted = false;
	private BoardGUI boardGUI;
	private Action action;
	private State state;
	private StateSpace stateSpace = new StateSpace();
	public static boolean isWhite;
	
	
	// TODO: add object to communicate received game moves with (can be through
	// interface)

	public void connect() {
		GameClient gameClient = new GameClient("", "", this);
	}

	// member variables
	public ArrayList<Integer> queenCurrent;
	public ArrayList<Integer> queenNew;
	public ArrayList<Integer> arrow;

	// usernames for each colour
	public String blackPlayerUserName;
	public String whitePlayerUserName;

	public ServerCommunicator(String userName, String passwd, BoardGUI boardGUI) {
		this.userName = userName;
		this.boardGUI = boardGUI;
		connectToServer(userName, passwd);
	}

	private void connectToServer(String name, String passwd) {
		// create a client and use "this" class (a GamePlayer) as the delegate.
		// the client will take care of the communication with the server.
		gameClient = new GameClient(name, passwd, this);
	}

	@Override
	public String userName() {
		return userName;
	}

	@Override
	/**
	 * Implements the abstract method defined in GamePlayer. Will be invoked by
	 * the GameClient when the server says the login is successful
	 */
	public void onLogin() {
		// listRooms();
		// once logged in, the gameClient will have the names of available game
		// rooms
		ArrayList<String> rooms = (ArrayList<String>) listRooms();
		
		// TODO: Replace the 6 with a method that gets the room number from
		// somewhere
		this.gameClient.joinRoom(rooms.get(8)); // CHANGE ROOM HERE <<<<<<<<<<<<<<<<<<<<--------------------------------------------------------------------------
		System.out.println("logged in");
	}

	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		System.out.println("OnHandleGameMessage");
		if (messageType.equals(GameMessage.GAME_ACTION_START)) { // GAME IS STARTING
			// TODO: Find a way to pass which player is which into the boardGUI
			// Class
			blackPlayerUserName = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
			whitePlayerUserName = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
			System.out.println(whitePlayerUserName);
			System.out.println(blackPlayerUserName);
			// TODO: if chosen to be first moving player (check color), make
			// move
			if (this.userName.equals(blackPlayerUserName)) {
				// then we are black
				this.isWhite = false;
				System.out.println("WE ARE LACKKK");
			} else {
				// we are white
				this.isWhite = true;
				System.out.println("WE ARE WHITE");
				// make the first move
				ai.State s = new ai.State(this.boardGUI.getGame().getBoard());
				Action a = new StateSpace().searchForNextActionQuickly(s);
				this.boardGUI.makeMove(a.getQueenStartIndex(), a.getQueenEndIndex(), a.getArrowIndex(), this.boardGUI.getGame());
				playerMove(a.getQueenStartIndex(), a.getQueenEndIndex(), a.getArrowIndex());
			}

			System.out.println("Game State: " + msgDetails.get(AmazonsGameMessage.GAME_STATE));

		} else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) { // RECEIVED A MOVE
			System.out.println("OnHandleGameMessage.GAME_ACTION_MOVE");
			handleOpponentMove(msgDetails);
		} else if (messageType.equals(GameMessage.GAME_STATE_PLAYER_LOST)) { // GAME OVER
			this.logout();
			System.exit(0);
		}

		return true;
	}

	private void handleOpponentMove(Map<String, Object> msgDetails) {
		// TODO: Update this so it effects the players boardstate.
		System.out.println("OpponentMove(): " + msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
		queenCurrent = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
		queenNew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
		arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		
		int startQueenPosition = ((10 - queenCurrent.get(0)) * 10) + (queenCurrent.get(1) - 1);
		int endQueenPosition = ((10 - queenNew.get(0)) * 10) + (queenNew.get(1)-1);
		int arrowPosition = ((10 -arrow.get(0)) * 10)+ (arrow.get(1)-1);
		
		System.out.println("QCurr: " + startQueenPosition);
		System.out.println("QNew: " + endQueenPosition);
		System.out.println("Arrow: " + arrowPosition);
		
		this.boardGUI.makeMove(startQueenPosition, endQueenPosition, arrowPosition, this.boardGUI.getGame());
		
		this.action = this.stateSpace.searchForNextActionQuickly(new State(this.boardGUI.game.getBoard()));
		
		if(this.action == null) {
			System.exit(0);
			logout();
		}
		
		System.out.println(this.action.toString());
		
		startQueenPosition = this.action.getQueenStartIndex();
		endQueenPosition = this.action.getQueenEndIndex();
		arrowPosition = this.action.getArrowIndex();
		
		this.boardGUI.makeMove(startQueenPosition, endQueenPosition, arrowPosition, this.boardGUI.getGame());

		playerMove(this.action.getQueenStartIndex(), this.action.getQueenEndIndex(), this.action.getArrowIndex());
		
		// TODO: use object to communicate received game moves with (can be
		// through interface)
	}

	/**
	 * handle a move made by this player --- send the info to the server.
	 * 
	 * @param x
	 *            queen row index
	 * @param y
	 *            queen col index
	 * @param arow
	 *            arrow row index
	 * @param acol
	 *            arrow col index
	 * @param qfr
	 *            queen original row
	 * @param qfc
	 *            queen original col
	 */
	@Override
	public void playerMove(int startPos, int endPos, int arrowPos) {
		System.out.println(startPos + " " + endPos + " " + arrowPos);
		int[] qf = new int[2];
		
		String start = (startPos < 10) ?  "0" +  startPos : "" + startPos;
		String end = (endPos < 10) ?  "0" + endPos : "" + endPos;
		String arrow = (arrowPos < 10) ?  "0" + arrowPos : "" + arrowPos;
		
		System.out.println(start + " " + end + " " + arrow);
		
		qf[0] = (10 - Integer.parseInt(start.substring(0, 1)));
		qf[1] = (Integer.parseInt(start.substring(1, 2)) + 1);

		int[] qn = new int[2];
		qn[0] = (10 - Integer.parseInt(end.substring(0, 1)));
		qn[1] = (Integer.parseInt(end.substring(1, 2)) + 1);

		int[] ar = new int[2];
		ar[0] = (10 - Integer.parseInt(arrow.substring(0, 1)));
		ar[1] = (Integer.parseInt(arrow.substring(1, 2)) + 1);
		
		System.out.println(qf[0] + "," + qf[1] +  " " + qn[0] + "," + qn[1] + " " + ar[0] + "," + ar[1]);
		
		// To send a move message, call this method with the required data
		
		sendMove(qf, qn, ar);
	}

	public boolean handleMessage(String msg) {
		System.out.println("Time Out ------ " + msg);
		return true;
	}

	public List<String> listRooms() {
		List<String> rooms = gameClient.getRoomList();
		/*for (String room : rooms) {
			System.out.println(room);
		}*/

		return rooms;
	}
private boolean moveHasAlreadyBeenMade(int[] start, int[] end, int[] arrow) {
		if (oldMove[0] == start[0] && oldMove[1] == start[1] && oldMove[2] == end[0] && oldMove[3] == end[1]
				&& oldMove[4] == arrow[0] && oldMove[5] == arrow[1]) {
			return true;
		} else {
			oldMove[0] = start[0];
			oldMove[1] = start[1];
			oldMove[2] = end[0];
			oldMove[3] = end[1];
			oldMove[4] = arrow[0];
			oldMove[5] = arrow[1];
			return false;
		}

	}
	// TODO: will have to be called when a move is made (through callback)
	public void sendMove(int[] queenPosCurrent, int[] queenPosNew, int[] arrowPos) {
		if (moveHasAlreadyBeenMade(queenPosCurrent, queenPosNew, arrowPos) == false) {
			gameClient.sendMoveMessage(queenPosCurrent, queenPosNew, arrowPos);
		}
	}

	public void logout() {
		System.out.println("Logging out...");
		gameClient.logout();
	}
}
