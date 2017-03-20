package serverCommunications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
import ygraphs.ai.smart_fox.games.GameClient;
import ygraphs.ai.smart_fox.games.GamePlayer;

public class ServerCom extends GamePlayer {
	private GameClient gameClient;
	public String userName = null;
	private boolean gameStarted = false;

	public ServerCom(String userName, String passwd) {
		this.userName = userName;
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
		listRooms();
		// once logged in, the gameClient will have the names of available game
		// rooms
		ArrayList<String> rooms = gameClient.getRoomList();

		// TODO: Replace the 6 with a method that gets the room number from
		// somewhere
		this.gameClient.joinRoom(rooms.get(4));
	}

	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {

		if (messageType.equals(GameMessage.GAME_ACTION_START)) {
			// TODO: Find a way to pass which player is which into the boardGUI
			// Class
			if (((String) msgDetails.get("player-black")).equals(this.userName())) {
				System.out.println("Game State: " + msgDetails.get("player-black"));
			}

		} else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
			handleOpponentMove(msgDetails);
		}

		return true;
	}

	private void handleOpponentMove(Map<String, Object> msgDetails) {
		// TODO: Update this so it effects the players boardstate.
		System.out.println("OpponentMove(): " + msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
		ArrayList<Integer> qcurr = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
		ArrayList<Integer> qnew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
		ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		System.out.println("QCurr: " + qcurr);
		System.out.println("QNew: " + qnew);
		System.out.println("Arrow: " + arrow);

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
	public void playerMove(int startPos, int endPos, int arrowPos) {

		int[] qf = new int[2];

		qf[0] = (10 - Integer.valueOf(String.valueOf(startPos).charAt(0)));
		qf[1] = (Integer.valueOf(String.valueOf(startPos).charAt(1)) - 1);

		int[] qn = new int[2];
		qn[0] = (10 - Integer.valueOf(String.valueOf(endPos).charAt(0)));
		qn[1] = (Integer.valueOf(String.valueOf(arrowPos).charAt(1)) - 1);

		int[] ar = new int[2];
		ar[0] = (10 - Integer.valueOf(String.valueOf(arrowPos).charAt(0)));
		ar[1] = (Integer.valueOf(String.valueOf(arrowPos).charAt(1)) - 1);

		// To send a move message, call this method with the required data
		gameClient.sendMoveMessage(qf, qn, ar);

	}

	public boolean handleMessage(String msg) {
		System.out.println("Time Out ------ " + msg);
		return true;
	}

	public void listRooms() {
		List<String> rooms = gameClient.getRoomList();
		for (String room : rooms) {
			System.out.println(room);
		}
	}

}
