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
	private Node currentGameState;
	private LinkedList<Integer> colsFilled;
	
	
	/**
	 * Constructor for the game class
	 * 
	 */
	public Game() {
		this.ui = new UserInterface();
		this.colsFilled = new LinkedList<Integer>();
		for(int i=0; i < 7; i++) {
			colsFilled.add(0);
		}

	}
	
	/**
	 * This method starts the game
	 */
	public void startGame() {
		for(;;) {
			String usrInput = JOptionPane.showInputDialog("Do you want to move first y/n?");
			usrInput = usrInput.toLowerCase();
			if (usrInput.equals("y")) {
				usersTurn = true;
				break;
			} else if (usrInput.equals("n")){
				usersTurn = false;
				break;
			} 
		}
		

		int col;
		if (usersTurn) {
			col = getUserMove();
		} else {
			col = 3; //Maybe some thing else for the first bot move????
		}
		int row = generateRow(col);

		int playerNum = 0;
		if (usersTurn) {
			playerNum = 1;
		}
		//apply move to the UI
		this.ui.applyMove(row, col, playerNum);
		this.currentGameState = new Node(col, getPlayerInt(this.usersTurn));
		this.usersTurn = !this.usersTurn;
		updateState(row, col, getPlayerInt(this.usersTurn));
		getAndApplyMove();
		
	}
		
		
		
	
	
	public void getAndApplyMove() {
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
			updateState(row, col, getPlayerInt(this.usersTurn));
		}
		
		this.usersTurn = !this.usersTurn;
		printState();
		getAndApplyMove();
	}
	
	/**
	 * Get an integer from the user, Nothing else
	 * 
	 * @return the integer from the user
	 */
	public int getUserMove() {
		boolean goodInput = false;
		int column = 0;
		while (!goodInput) {
			String input = JOptionPane.showInputDialog("Enter your move 0 to 6");
			boolean p1 = false;
			boolean p2 = false;
			if(input.matches("[0-6]{1}")) {
				p1 = true;
				column = Integer.parseInt(input);
			}
			if (validateMove(column)) {
				p2 = true;	
			}
			
			goodInput = (p1 && p2);
		}
		
		
		return column;
	}
	
	public int getBotMove(){
		boolean goodInput = false;
		int randomNum = 0;
		while (!goodInput) {
			randomNum =  (int)(Math.random() * 7);
			if (validateMove(randomNum)) {
				 goodInput = true;	
			}
		}
		
		//StateSpace graph = new StateSpace(this.currentGameState, getPlayerInt(this.usersTurn));
		
		
		
		
		
		
		
		
		
		
		return randomNum;
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
	
	private int getPlayerInt(boolean usersTurn) {
		if (usersTurn) {
			return 1;
		}else {
			return 2;
		}
	}
	
	private void updateState(int row, int col, int playerNum) {
		int[][] game = this.currentGameState.getGameState();
		
		game[row][col] = playerNum;
		this.currentGameState.setGameState(game);
	}
	
	
	public void printState() {
		int[][] game = this.currentGameState.getGameState();
		for (int i = game.length -1; i >=0 ; i--) {
			for (int j = 0; j < game[i].length; j++) {
				System.out.printf("%d  ", game[i][j]);
			}
			System.out.println("");
		}
	}
	
	
	
	

}
