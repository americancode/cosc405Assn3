package assn3;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JDialog;

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
	private LinkedList<Integer> currentIndex;
	public static final boolean TESTING = false; // global var for testing console printing
	
	
	/**
	 * Constructor for the game class
	 * 
	 */
	public Game() {
		this.ui = new UserInterface();
		this.currentIndex = new LinkedList<Integer>();
		for(int i=0; i < 7; i++) {
			currentIndex.add(0);
		}

	}
	
	/**
	 * This method starts the game
	 */
	public void startGame() {
		for(;;) {
			// Create JOption Pane
			JOptionPane optionPane = new JOptionPane("Do you want to move first? y/n", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			optionPane.setWantsInput(true);
			JDialog dialog = optionPane.createDialog(null, "Wecome!");
			dialog.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			    	System.exit(0);
			    }
			});
			dialog.setLocation(dialog.getLocation());
			dialog.setLocation(100, 220);
			dialog.setVisible(true);
			String usrInput = (String) optionPane.getInputValue();			
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
			col = 3; // Maybe some thing else for the first bot move????
		}
		int row = generateRow(col);

		int playerNum = 0;
		if (usersTurn) {
			playerNum = 1;
		}
		//apply move to the UI
		this.ui.applyMove(row, col, playerNum);
		this.currentGameState = new Node(col, getPlayerInt(this.usersTurn));
		updateState(row, col, getPlayerInt(this.usersTurn));
		this.usersTurn = !this.usersTurn;
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

		int playerNum = 0;
		if (usersTurn) {
			playerNum = 1;
		}
		this.ui.applyMove(row, col, playerNum);
		updateState(row, col, getPlayerInt(this.usersTurn));

		this.usersTurn = !this.usersTurn;
		if (TESTING) {
			printState();
		}

		StateSpace blankSpace = new StateSpace();
		int win = blankSpace.winningState(this.currentGameState);
		
		//Check for a win
		if (win == 0) {
			getAndApplyMove();
		} else {
			if (win == 1) {
				JOptionPane.showMessageDialog(null, "Player 1  WON!!");
			}else {
				JOptionPane.showMessageDialog(null, "Player 2  WON!!");
			}
			playAgain();
		}
		
		
		
	}
	
	
	
	private void playAgain() {
		for(;;) {
			// Create JOption Pane
			JOptionPane optionPane = new JOptionPane("Do you want to play again? y/n", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			optionPane.setWantsInput(true);
			JDialog dialog = optionPane.createDialog(null, "Play Again?");
			dialog.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			    	System.exit(0);
			    }
			});
			dialog.setLocation(dialog.getLocation());
			dialog.setLocation(100, 220);
			dialog.setVisible(true);
			String usrInput = (String) optionPane.getInputValue();			
			usrInput = usrInput.toLowerCase();
			
			if (usrInput.equals("y")) {
				Game g = new Game();
				g.startGame();
				break;
			} else if (usrInput.equals("n")){
		    	System.exit(0);
				break;
			} 
		}
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
			
			// Create JOptionPane
			JOptionPane optionPane = new JOptionPane("Enter your move 0 to 6", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			optionPane.setWantsInput(true);
			JDialog dialog = optionPane.createDialog(null, "Your move!");
			dialog.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			    	System.exit(0);
			    }
			});
			dialog.setLocation(100, 220);
			dialog.setVisible(true);
			String input = (String) optionPane.getInputValue();			

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
		int play = 0;
		StateSpace graph = new StateSpace(this.currentGameState, getPlayerInt(this.usersTurn));
		play = graph.getBotMove();
		
		if (validateMove(play)) {
			return play;
		}else {
			
			while (!goodInput) {
				play = (int) (Math.random() * 7);
				if (validateMove(play)) {
					goodInput = true;
				}
			}
			
			if (TESTING) {
				System.out.println("AI gave BAD PLAY. a random on was generated");
			}

			
		}

		return play;
	}

	/** a pre-validation method to validate the player BEFORE generate row is called
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
	
	private int generateRow(int col) {
		int row = this.currentIndex.get(col);
		int newRow = row + 1;
		this.currentIndex.set(col, newRow); //set the next index
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
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("_____________________________________________ACTUAL GAME STATE AFTER PLAY_______________________________________");
		System.out.println("________________________________________________________________________________________________________________");

		for (int i = game.length -1; i >=0 ; i--) {
			for (int j = 0; j < game[i].length; j++) {
				System.out.printf("%d  ", game[i][j]);
			}
			System.out.println("");
		}
		System.out.println("\n\n");
	}
	
	
	
	

}
