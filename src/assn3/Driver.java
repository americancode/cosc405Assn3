package assn3;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		
		System.out.println("Hello AI");

		UserInterface window = new UserInterface();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(700,500);
		window.setVisible(true);
		
	}

}
