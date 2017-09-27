package assn3;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		
		System.out.println("Hello AI");
		
		//spin up the UI
		UserInterface window = new UserInterface();
		
		//start the game
		Game game = new Game();
		game.startGame();
		
	}
}
