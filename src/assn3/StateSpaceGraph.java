package assn3;

import java.util.LinkedList;

public class StateSpaceGraph {
	Node root;
	private final int DEPTH = 5;
	/**
	 * constructor for the FIRST PLAY
	 * @param col
	 * @param player
	 */
	public StateSpaceGraph(Node currentGameState, int currentPlayer) {
		this.root = currentGameState;
		this.root.getAndSetChildren(currentPlayer);
		for (int j = 0; j < DEPTH; j++) { // defines the depth of the search how many LEVELS!  THIS IS NOT RIGHT NEEDS ALOT OF HELP
			
			LinkedList<Node> childList = this.root.getChildrenList();
			int notPlayer = negatePlayer(currentPlayer);
			
			for (int i = 0; i < this.root.getChildrenList().size(); i++) {
				childList.get(i).getAndSetChildren(currentPlayer);
			}
		}
		
		
		
	}

	public void bestFirstSearch() {
	}
	
	
	
	private int negatePlayer(int player) {
		if (player == 1) {
			return 2;
		}else {
			return 1;
		}
	}
	
}
