package assn3;

import java.util.LinkedList;

/**
 * This class should be where the game is controlled.
 * 
 */

import javax.swing.JOptionPane;


public class Game {
	//Attributes of the game class
	private boolean usersTurn = false;
	private UserInterface ui = null;
	//This is currently used for the UI but may be able to use it other places
	private Node[][] gameState;
	private LinkedList<Integer> colsFilled;
	
	
	
	/**
	 * Constructor for the game class
	 */
	public Game() {
		this.gameState = new Node[7][6];
		this.ui = new UserInterface();
		this.colsFilled = new LinkedList<Integer>();
		for(int i=0; i < 7; i++) {
			colsFilled.add(0);
		}
		fillStates();

	}
	
	/**
	 * This method starts the game
	 */
	public void startGame() {
		String usrInput = JOptionPane.showInputDialog("Do you want to move first y/n?");
		usrInput = usrInput.toLowerCase();
		if (usrInput.equals("y")) {
			usersTurn = true;
		} else if (usrInput.equals("n")){
			usersTurn = false;
		}
		
		getMove();
		
		
	}
	
	
	public void getMove() {
		int col;
		if (usersTurn) {
			col = getUserMove();
		} else {
			col = getBotMove();
		}
		int row = generateRow(col);
		
		
		if (validateMove(col)) {
			int playerNum = 0;
			if(usersTurn) {
				playerNum = 1;
			}
			this.ui.applyMove(row, col, playerNum);
		}
		
		this.usersTurn = !this.usersTurn;
		
		getMove();
		

	}
	
	
	public int getUserMove() {
		boolean goodInput = false;
		int column = 0;
		while (!goodInput) {
			String input = JOptionPane.showInputDialog("Enter your move 0 to 6");
			if(input.matches("[0-6]{1}")){
				goodInput = true;
				column = Integer.parseInt(input);
			}
			
		}
		
		
		return column;
	}
	
	public int getBotMove(){
		int randomNum =  (int)(Math.random() * 7); 
		return randomNum;
	}
	
	
	
	// Used for the UI but initializes the nodes in the matrix
	public void fillStates() {
		for(int i = 0; i < this.gameState.length; i++) {
			for (int j = 0; j < this.gameState[i].length; j++) {
				Node n = new Node(i, j);
				this.gameState[i][j] = n;
			}
		}
	}
	
	
	private boolean validateMove(int col) {
		int filled = this.colsFilled.get(col);
		if (filled < 7) {
			return true;
		}else {
			return false;
		}	
	}
	
	private int generateRow(int col) {
		int row = this.colsFilled.get(col);
		int newRow = row + 1;
		this.colsFilled.set(col, newRow);
		
		return row;
	}
	
	
	
	
	
	
	

}
