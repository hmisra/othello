

public class ResultState {
	int maxvalue;
	int [][] board=new int[8][8];
	String newMove;
	public String getNewMove() {
		return newMove;
	}
	public void setNewMove(String newMove) {
		this.newMove = newMove;
	}
	public int[][] getBoard() {
		return board;
	}
	public void setBoard(int[][] board) {
		this.board = board;
	}
	public int getMaxvalue() {
		return maxvalue;
	}
	public void setMaxvalue(int maxvalue) {
		this.maxvalue = maxvalue;
	}
	

}
