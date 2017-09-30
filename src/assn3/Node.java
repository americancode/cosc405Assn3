package assn3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Node implements Comparable{
	private int[][] gameState;
	private Node parent;
	private LinkedList<Node> childrenList;
	private boolean isRootNode = false;
	private int heuristicValue;
	private LinkedList<Integer> currentIndex;
	private ArrayList<Integer> pathToNode; //a list of indices that correspond to a path of children 
	private int playToNode;
	
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
		
		this.gameState[0][col] = player; // the first play of the game
		this.currentIndex.set(col, 1); // set the row index for the next play for that column
		this.pathToNode = new ArrayList<Integer>();
		this.playToNode = col;
		
	}
	
	public Node(Node parent) {
		this.currentIndex = new LinkedList<Integer>();
		this.childrenList = new LinkedList<Node>();

		for(int i = 0; i < 7; i ++) {
			this.currentIndex.add(0);
		}
		this.parent = parent;
		this.currentIndex = parent.getCurrentIndex(); // setting the parent list of indexes to the child list of indexes
		this.pathToNode = parent.getPathToNode();
	}
	

	/**
	 * Generates the children on the given node
	 * @param currentPlayer / the opposite of the parent node player.  The current player for generated child nodes
	 * @return
	 */
	public void getAndSetChildren(Node parent, int currentPlayer) {
		LinkedList<Node> childList = new LinkedList<Node>();
		for(int i = 0; i < 7; i++) {
			Node tempNode = new Node(parent);
			tempNode.gameState = getState(i, currentPlayer);
			if (validateMove(i)) { // if the proposed move [1-6] creates a possible game state add it to the list
				int row = tempNode.currentIndex.get(i);
				int newRow = row + 1;
				tempNode.currentIndex.set(i, newRow);
				tempNode.pathToNode.add(childList.size());
				tempNode.playToNode = i;
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
	private boolean validateMove(int col) {
		int filled = this.currentIndex.get(col);
		if (filled < 6) {
			return true;
		}else {
			return false;
		}	
	}
	
	

	
	
	private int[][] getState(int col, int player) {
		int[][]	state = new int[this.gameState.length][];;
		
		for(int i = 0; i < this.gameState.length; i++) {
		    state[i] = this.gameState[i].clone();
		}
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
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public LinkedList<Integer> getCurrentIndex() {
		return currentIndex;
	}

	public ArrayList<Integer> getPathToNode() {
		return pathToNode;
	}

	public int getPlayToNode() {
		return playToNode;
	}

	public void setPlayToNode(int playToNode) {
		this.playToNode = playToNode;
	}

	public void setPathToNode(ArrayList<Integer> pathToNode) {
		this.pathToNode = pathToNode;
	}

	public void setCurrentIndex(LinkedList<Integer> currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean testVal = false;
		Node node = ((Node)obj);
		int [][] gameState = node.getGameState();
		if(Arrays.deepEquals(node.getGameState(), this.gameState)) {
			testVal = true;
		} else {
			testVal = false;
		}
		return testVal;
	}

	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		Node node = ((Node)obj);
		//this < obj => -1
		
		if (this.heuristicValue < node.getHeuristicValue()) {
			return -1;
		}else if (this.heuristicValue < node.getHeuristicValue()) {
			return 1;
		}else {
			return 0;
		}
		
	}

	
	
	
}
