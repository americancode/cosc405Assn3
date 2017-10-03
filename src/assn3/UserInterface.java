package assn3;

import java.awt.*;
import javax.swing.*;

public class UserInterface extends JFrame {

	private static final int ROWS = 6;
	private static final int COLS = 7;
	
	private Circle[][] circlePanels = new Circle[ROWS][COLS];
	private JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS));

	public UserInterface() {
		initCircles();
		JFrame f = new JFrame();
		f.setTitle("Connect Four");
		f.add(gridPanel);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private void initCircles() {
		for (int i = circlePanels.length -1; i >= 0; i--) {
			for (int j = 0; j < circlePanels[i].length; j++) {
				Circle circle = new Circle();
				circlePanels[i][j] = circle;
				gridPanel.add(circle);
			}
		}
	}

	class Circle extends JPanel {
		private boolean draw = false;
		private Color color = Color.BLUE;

		public Circle() {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		}

		public boolean isDraw() {
			return draw;
		}

		public void setDraw(boolean draw) {
			this.draw = draw;
			repaint();
		}

		public void setColor(Color color) {
			this.color = color;
			repaint();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(75, 75);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (draw) {
				g.setColor(color);
				g.fillOval(0, 0, getWidth(), getHeight());
			}
		}
		
	}
	
	public void applyMove(int row, int column, int player) {
		if (player == 1) {
			this.circlePanels[row][column].setColor(Color.RED);
		}else {
			this.circlePanels[row][column].setColor(Color.BLUE);
		}
		this.circlePanels[row][column].setDraw(true);
	}
	
	public void resetBoard() {
		for(int i = 0; i < this.circlePanels.length; i++) {
			for (int j = 0; j < this.circlePanels[i].length; j++) {
				this.circlePanels[i][j].setDraw(false);
			}
		}
	}
	
}