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
	
	public StateSpace() {
		
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
	
	
	
	
	
	
	/** determine if there is a winning state **/
	
	public int winningState(Node currentState) {
		int playerWin = 0;
		
		playerWin += horizontalWinState( currentState );
		
		if (playerWin > 0){
			return playerWin;
		}
		
		playerWin += verticalWinState( currentState );
		
		if (playerWin != 0){
			return playerWin;
		}
		
		playerWin += diagonal1WinState( currentState );
		
		if (playerWin != 0){
			return playerWin;
		}
		
		playerWin += diagonal2WinState( currentState );
		
		return playerWin;
	}

	
	/** Check for diagonal win (left to right, positive slope) **/
	
	private int diagonal1WinState(Node currentState) {
		
		
		int[][] gameState = currentState.getGameState();
		
		
			int player1Count = 0;
			int player2Count = 0;
			int a = 0;	// col
			int slope = 1;
			// checks along the y axis
			for(int b = gameState.length-1; b > 0; b--) {
					
					int x = diagonalCheck(gameState, b, a, player1Count, player2Count, slope);
					
					if (x != 0){
						return x;
					}
				
			}
			
			// checks along the x axis
			int b = 0; // row
			while (a < gameState[0].length){
				
				int x = diagonalCheck(gameState, b, a, player1Count, player2Count, slope);
				
				if (x != 0){
					return x;
				}
				
				a++;
			}

		return 0;
	}

	/** Check for diagonal win (right to left, negative slope)**/
	
	private int diagonal2WinState(Node currentState) {
		

		int[][] gameState = currentState.getGameState();
		
		
			int player1Count = 0;
			int player2Count = 0;
			int a = 6;	// col
			int slope = -1;
			// checks along the y axis
			for(int b = gameState.length-1; b > 0; b--) {
					
					int x = diagonalCheck(gameState, b, a, player1Count, player2Count, slope);
					
					
					if (x != 0){
						return x;
					}
				
			}
			
			// checks along the x axis
			int b = 0; // row
			while (a > 0){
				
				int x = diagonalCheck(gameState, b, a, player1Count, player2Count, slope);
				
				if (x != 0){
					return x;
				}
				
				a--;
			}

		return 0;

	}

	/** Check for vertical win **/
	
	private int verticalWinState(Node currentState) {
		
		int[][] gameState = currentState.getGameState();
		
		for(int a = 0; a < gameState.length+1; a++) {
			
			int player1Count = 0;
			int player2Count = 0;
			
			for(int b = 0; b < gameState.length; b++) {
				
				int player = gameState[b][a];
				
				int [] playerCounts = addPlayerCount(player, player1Count, player2Count);
				
				player1Count = playerCounts[0];
				player2Count = playerCounts[1];

				if (player1Count == 4){
					return 1;
				}
				else if (player2Count == 4){
					return 2;
				}
		
			}

		
		}
		return 0;

	}


	/** Check for horizontal win **/
	
	private int horizontalWinState(Node currentState) {
		
		int[][] gameState = currentState.getGameState();
		
		for(int a = 0; a < gameState.length; a++) {
			
			int player1Count = 0;
			int player2Count = 0;
			
			for(int b = 0; b < gameState[a].length; b++) {
				int player = gameState[a][b];
				
				
				int [] playerCounts = addPlayerCount(player, player1Count, player2Count);
				
				player1Count = playerCounts[0];
				player2Count = playerCounts[1];

				if (player1Count == 4){
					return 1;
				}
				else if (player2Count == 4){
					return 2;
				}
			}
			

		
		}
		return 0;

	}
	
	/** add number of counts to appropriate player **/
	
	private int[] addPlayerCount(int player, int player1Count, int player2Count){
		int [] playerCounts = new int [2];
		
		if (player == 2 ){
			player2Count++;
			playerCounts[0] = 0;
			playerCounts[1] = player2Count;
			
		}
		
		else if (player == 1){
			player1Count++;
			playerCounts[0] = player1Count;
			playerCounts[1] = 0;
			
		}
		else{
			playerCounts[0] = 0;
			playerCounts[1] = 0;
			
		}
		
		return playerCounts;
		
	}
	
	/** Check for diagonal win **/
	
	private int diagonalCheck(int [][] gameState, int row, int col, int player1Count, int player2Count, int slope){
		 
		
		while (row < gameState.length && col < gameState[0].length){
			
		
			
			try{
				
				int player = gameState[row][col];
				int [] playerCounts = addPlayerCount(player, player1Count, player2Count);
				player1Count = playerCounts[0];
				player2Count = playerCounts[1];
			
			}
			
			catch (ArrayIndexOutOfBoundsException e){
				// catch off board nodes
				return 0;
			}
	
			

			if (player1Count == 4){
				return 1;
			}
			else if (player2Count == 4){
				return 2;
			}
			
			if (slope == -1){
				row++;
			}
			else{
			row += slope;
			}
			col += slope;
		
		}
		
		return 0;
		
	}
}
