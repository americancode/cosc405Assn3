package assn3;

import java.util.LinkedList;

public class Node {
	private int[][] gameState;
	private LinkedList<Node> childrenList;
	private boolean isRootNode = false;
	private int heuristicValue;
	private LinkedList<Integer> currentIndex;
	
	/**
	 * The constructor for the state space START ONLY
	 * @param startingGameState
	 */
	public Node(int col, int player) {
		this.isRootNode = true;
		this.childrenList = new LinkedList<Node>();
		this.currentIndex = new LinkedList<Integer>();
		
		for(int i = 0; i < 7; i ++) {
			this.currentIndex.add(0);
		}
		
		this.gameState = new int[6][7];
		//initialize with an empty game state
		for(int a = 0; a < this.gameState.length; a++) {
			for(int b = 0; b < this.gameState[a].length; b++) {
				this.gameState[a][b] = 0;
			}
		}
		
		this.gameState[0][col] = player; // the first play of the game
		this.currentIndex.set(col, 1);
		
	}
	
	public Node() {
		for(int i = 0; i < 7; i ++) {
			this.currentIndex.add(0);
		}
	}
	
	/**
	 * Generates the children on the given node
	 * @param currentPlayer / the opposite of the parent node player.  The current player for generated child nodes
	 * @return
	 */
	public void getAndSetChildren(int currentPlayer) {
		LinkedList<Node> childList = new LinkedList<Node>();
		for(int i = 0; i < 7; i++) {
			Node tempNode = new Node();
			tempNode.gameState = getState(i, currentPlayer);
			tempNode.currentIndex = this.currentIndex;// setting the parent list of indexes to the child list of indexes
			if (validateMove(i)) { // if the proposed move [1-6] creates a possible game state add it to the list
				int row = tempNode.currentIndex.get(i);
				int newRow = row + 1;
				tempNode.currentIndex.set(i, newRow);
				childList.add(tempNode);
			}
			
		}

		this.childrenList = childList;
	}
	
	/** a pre-validation method to validate the player BEFORE generating the row and incrementing currentIndex
	 * 
	 * @param col
	 * @return
	 */
	private boolean validateMove(int col) {
		int filled = this.currentIndex.get(col);
		if (filled < 6) {
			return true;
		}else {
			return false;
		}	
	}
	
	
	private int[][] getState(int col, int player) {
		int[][]	state = this.gameState;	
		int row = this.currentIndex.get(col);
		int newRow = row + 1;
		this.currentIndex.set(col, newRow);
		state[row][col] = player;
		return state;
	}

	public int[][] getGameState() {
		return gameState;
	}

	public void setGameState(int[][] gameState) {
		this.gameState = gameState;
	}

	public LinkedList<Node> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(LinkedList<Node> childrenList) {
		this.childrenList = childrenList;
	}

	public boolean isRootNode() {
		return isRootNode;
	}

	public void setRootNode(boolean isRootNode) {
		this.isRootNode = isRootNode;
	}

	public int getHeuristicValue() {
		return heuristicValue;
	}

	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

	public LinkedList<Integer> getcurrentIndex() {
		return currentIndex;
	}

	public void setcurrentIndex(LinkedList<Integer> currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
	
}
