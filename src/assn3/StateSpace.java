package assn3;

import java.util.LinkedList;

public class StateSpace {
	private Node root;
	private int currentPlayer;
	private final int DEPTH = 5;
	
	/**
	 * constructor for the FIRST PLAY
	 * @param col
	 * @param player
	 */
	public StateSpace(Node currentGameState, int currentPlayer) {
		this.root = currentGameState;
		this.currentPlayer = currentPlayer;
	}

	public void bestFirstSearch() {
		LinkedList<Node> openList = new LinkedList<Node>();
		LinkedList<Node> closedList = new LinkedList<Node>();
		
		while (openList.size() > 0) {
			Node X = openList.get(0);
		}
		
		
		
	}
	
	
	
	private int negatePlayer(int player) {
		if (player == 1) {
			return 2;
		}else {
			return 1;
		}
	}
	
	public int winningState(Node currentState) {
		boolean winnerIsP1 = false;
		boolean winnerIsP2 = false;
		Node state = currentState;
		int[][] gameState = state.getGameState();
		
		for(int a = 0; a < gameState.length; a++) {
			for(int b = 0; b < gameState[a].length; b++) {
				System.out.println(gameState[a][b]);
			}
		}

		
		
		return DEPTH;
	}
	
}
