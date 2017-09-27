package assn3;

/**
 * This class should be where the game is controlled.
 * 
 */

import javax.swing.JOptionPane;


public class Game {
	boolean userStartingFirst = false;
	
	public void startGame() {
		String usrInput = JOptionPane.showInputDialog("Do you want to move first y/n?");
		usrInput = usrInput.toLowerCase();
		if (usrInput.equals("y")) {
			userStartingFirst = true;
			getUserMove();
			
			
		} else if (usrInput.equals("n")){
			userStartingFirst = false;
		}
		
		System.out.println(userStartingFirst); //testing input
	}
	
	
	public void getUserMove() {
		int column = Integer.parseInt(JOptionPane.showInputDialog("Enter your move 0 to 6")); //TODO add some validation
		
		
		System.out.println(column);
	}
	
	
	

}
