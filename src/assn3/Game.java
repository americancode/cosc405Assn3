package assn3;

import javax.swing.JOptionPane;

public class Game {
	boolean userStartingFirst = false;
	
	public void startGame() {
		String usrInput = JOptionPane.showInputDialog("Do you want to move first y/n?");
		usrInput = usrInput.toLowerCase();
		if (usrInput.equals("y")) {
			userStartingFirst = true;
		} else if (usrInput.equals("n")){
			userStartingFirst = false;
		}
		
		System.out.println(userStartingFirst); //testing input
	}
	
	
	

}
