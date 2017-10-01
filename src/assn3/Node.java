package assn3;


import java.util.Arrays;
import java.util.LinkedList;

public class Node implements Comparable<Object> {
	private Node parent; // a pointer to the parent node
	private boolean isRootNode = false;
	private int heuristicValue; //the heuristic value associated with this node
	private int playToNode; //the direct play to get to the this node 0-6
	private int playerNumber; // the player who this node and gamestate
	private int[][] gameState; // the current game state represented as a 2D matrix
	private LinkedList<Node> childrenList; //a list of all the children of this node
	private LinkedList<Integer> currentIndex; //a list of current indices for the the rows
	private LinkedList<Integer> pathToNode; //a list of indices that correspond to a path of children 
	
	
	/**
	 * The constructor for the state space START ONLY
	 * @param startingGameState
	 */
	public Node(int col, int player) {
		this.isRootNode = true;
		this.childrenList = new LinkedList<Node>();
		this.currentIndex = new LinkedList<Integer>();
		this.parent = null;
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
		
		this.gameState[0][col] = player; // set the first play of the game
		this.currentIndex.set(col, 1); // set the row index for the next play for that column
		this.pathToNode = new LinkedList<Integer>();
		this.playToNode = col;
		this.playerNumber = player; //set who played it
		
	}
	
	/*
	 * Constructor for generating children based on the parent node
	 */
	public Node(Node parent) {
		this.currentIndex = new LinkedList<Integer>();
		this.childrenList = new LinkedList<Node>();
		this.pathToNode = new LinkedList<Integer>();
		this.currentIndex = new LinkedList<Integer>(); // setting the parent list of indexes to the child list of indexes
		this.currentIndex = new LinkedList<Integer>();
		this.currentIndex.addAll(parent.currentIndex);
		this.pathToNode.addAll(parent.pathToNode);
		this.playerNumber = negatePlayer(parent.playerNumber); // set the levels so that the children's plays are played by the opposite of the parent
	}
	public Node() {
		this.isRootNode = true;
		this.childrenList = new LinkedList<Node>();
		this.currentIndex = new LinkedList<Integer>();
		this.parent = null;
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
		this.pathToNode = new LinkedList<Integer>();
		
	}
	

	/**
	 * Generates the children on the given node
	 * @param currentPlayer / the opposite of the parent node player.  The current player for generated child nodes
	 * @return
	 */
	public void getAndSetChildren() {
		LinkedList<Node> childList = new LinkedList<Node>();
		for(int i = 0; i < 7; i++) {
			Node tempNode = new Node(this);
			if (validateMove(i)) { // if the proposed move [1-6] creates a possible game state add it to the list
				tempNode.gameState = createState(i, tempNode.playerNumber);
				int row = tempNode.currentIndex.get(i);
				int newRow = row + 1;
				tempNode.currentIndex.set(i, newRow);
				tempNode.pathToNode.add(childList.size());
				tempNode.playToNode = i;
				tempNode.parent = this;
				childList.add(tempNode);
			}
			
		}

		this.childrenList = childList;
	}
	
	/**  pre-validation method to validate the player 
	 * BEFORE generating the row and incrementing currentIndex
	 * 
	 * @param col
	 * @return
	 */
	public boolean validateMove(int col) {
		int filled = this.currentIndex.get(col);
		if (filled < 6) {
			return true;
		}else {
			return false;
		}	
	}
	
	public boolean applyMove(int col, int playerNum) {
		if (validateMove(col)) {
			int row = this.currentIndex.get(col);
			this.currentIndex.set(col, row+1); //set the next index
			this.gameState[row][col] = playerNum;
			return true;
		}else {
			return false;
		}
	}
	
	public int getUIRow(int col) {
		return this.currentIndex.get(col);
	}
	
	
	
	private int negatePlayer(int player) {
		if (player == 1) {
			return 2;
		} else {
			return 1;
		}
	}
	
	private int[][] createState(int col, int player) {
		int[][]	state = new int[6][7];
		for(int i = 0; i < this.gameState.length; i++) {
		    state[i] = this.gameState[i].clone();
		}
		int row = this.currentIndex.get(col);
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
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public LinkedList<Integer> getCurrentIndex() {
		return currentIndex;
	}

	public LinkedList<Integer> getPathToNode() {
		return pathToNode;
	}

	public int getPlayToNode() {
		return playToNode;
	}

	public void setPlayToNode(int playToNode) {
		this.playToNode = playToNode;
	}

	public void setPathToNode(LinkedList<Integer> pathToNode) {
		this.pathToNode = pathToNode;
	}

	public void setCurrentIndex(LinkedList<Integer> currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	@Override
	public boolean equals(Object obj) {
		boolean testVal = false;
		Node node = ((Node)obj);
		// int [][] gameState = node.getGameState();
		if(Arrays.deepEquals(node.getGameState(), this.gameState)) {
			testVal = true;
		} else {
			testVal = false;
		}
		return testVal;
	}

	/**
	 * Compares a node passed in as a parameter with the current node.
	 * 
	 * @param Object - the node to compare to the current node
	 * @return 1 if the current node's heuristic is less than the node's that is passed into this method. 
	 * -1 if the current node's heuristic is greater than the node's that is passed into this method.
	 * 0 if the heuristics of the two nodes are equal.
	 */
	@Override
	public int compareTo(Object obj) {
		Node node = ((Node)obj);
		if (this.heuristicValue < node.getHeuristicValue()) {
			return 1;
		}else if (this.heuristicValue > node.getHeuristicValue()) {
			return -1;
		}else {
			return 0;
		}
		
	}
	
	
}