package assn3;

public class Node {
	private int column;
	private int row;
	private int heuristicValue;
	private int playSwitch;
	
	public Node(int row, int column) {
		this.row = row;
		this.column = column;
		this.playSwitch = 0;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getHeuristicValue() {
		return heuristicValue;
	}
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}
	public int getPlaySwitch() {
		return playSwitch;
	}
	public void setPlaySwitch(int playSwitch) {
		this.playSwitch = playSwitch;
	}
	
	
	
}
