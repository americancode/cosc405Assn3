package assn3;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * This class controls the primary game like controlling turns and processing plays
 */
public class Game {
	//Attributes of the game class
	private boolean usersTurn = false;
	private UserInterface ui = null;
	//This is currently used for the UI but may be able to use it other places
	private Node currentGameState;
	public static final boolean TESTING = false; // global variable for testing console printing
	
	/**
	 * Constructor for the game class
	 */
	public Game() {
		this.ui = new UserInterface();
		this.currentGameState = new Node();
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
		int row = this.currentGameState.getUIRow(col);

		int playerNum = 0;
		if (usersTurn) {
			playerNum = 1;
		}
		//apply move to the UI
		this.ui.applyMove(row, col, playerNum);
		this.currentGameState.applyMove(col, getPlayerInt(this.usersTurn));
		this.usersTurn = !this.usersTurn;
		getAndApplyMove();
	}
		
	/**
	 * If it is the users turn, call the getUserMove method otherwise, call the getBotMove method.
	 * Once it has the next move, it applies it.
	 */
	public void getAndApplyMove() {
		int col;
		if (usersTurn) {
			col = getUserMove();
		} else {
			col = getBotMove();
		}
		int row = this.currentGameState.getUIRow(col);

		int playerNum = 0;
		if (usersTurn) {
			playerNum = 1;
		}
		this.ui.applyMove(row, col, playerNum);
		this.currentGameState.applyMove(col, getPlayerInt(this.usersTurn));

		this.usersTurn = !this.usersTurn;
		if (TESTING) {
			printState();
		}

		StateSpace blankSpace = new StateSpace();
		int win = blankSpace.winningState(this.currentGameState);

		//Check for a win
		if (win == 0) {
			getAndApplyMove();
		} else if (win == 1) {
			JOptionPane.showMessageDialog(null, "Player 1  WON!!");
			if (this.currentGameState.checkTie()) {
				JOptionPane.showMessageDialog(null, "Tie! Nobody won!");
				playAgain();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Player 2  WON!!");
			if (this.currentGameState.checkTie()) {
				JOptionPane.showMessageDialog(null, "Tie! Nobody won!");
				playAgain();
			}
		}		
		playAgain();
		
	}


	/**
	 * Asks user if they want to play again. If so, it creates a new game.
	 */
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
				this.currentGameState = new Node();
				this.ui.resetBoard();
				startGame();
				break;
			} else if (usrInput.equals("n")) {
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
			if (this.currentGameState.validateMove(column)) {
				p2 = true;	
			}	
			goodInput = (p1 && p2);
		}
		return column;
	}
	
	/**
	 * Get the bots next move.
	 * @return The bots next move.
	 */
	public int getBotMove(){
		boolean goodInput = false;
		int play = 0;
		StateSpace graph = new StateSpace(this.currentGameState);
		play = graph.getBotMove();
		
		if (this.currentGameState.validateMove(play)) {
			return play;
		} else {
			while (!goodInput) {
				play = (int) (Math.random() * 7);
				if (this.currentGameState.validateMove(play)) {
					goodInput = true;
				}
			}
			
			if (TESTING) {
				System.out.println("AI gave BAD PLAY. a random on was generated");
			}
		}
		return play;
	}

	/**
	 * Return the players number based on the usersTurn boolean.
	 * @param usersTurn
	 * @return 1 if usersTurn is false or 2 if usersTurn is true
	 */
	private int getPlayerInt(boolean usersTurn) {
		if (usersTurn) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * This method prints the current game state.
	 */
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