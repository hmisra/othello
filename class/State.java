import java.util.ArrayList;



public class State {
	int [][] board;
	int evaluation;
	ArrayList<Position> whitePosition=new ArrayList<Position>();
	ArrayList<Position> blackPosition=new ArrayList<Position>();
	Position newPosition=new Position(10, 10);
	
	public State(int[][] initialBoard, int player) {
		this.board=initialBoard;
		this.evaluation=setEvaluation(player);
	}
	public int[][] getBoard() {
		return board;
	}
	public Position getNewPosition() {
		return newPosition;
	}
	public void setNewPosition(Position newPosition) {
		this.newPosition = newPosition;
	}
	public void setBoard(int[][] board) {
		this.board = board;
	}
	public int getEvaluation() {
		return evaluation;
	}
	public int setEvaluation(int player) {
		int[][] evalweight=new int[][]{
				{99,-8,8,6,6,8,-8,99},
				{-8,-24,-4,-3,-3,-4,-24,-8},
				{8,-4,7,4,4,7,-4,8},
				{6,-3,4,0,0,4,-3,6},
				{6,-3,4,0,0,4,-3,6},
				{8,-4,7,4,4,7,-4,8},
				{-8,-24,-4,-3,-3,-4,-24,-8},
				{99,-8,8,6,6,8,-8,99}};
		int weightwhite=0;
		int weightblack=0;
		for(int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
			{
				if(board[i][j]==1)
				{
					weightblack=weightblack+(evalweight[i][j]);
				}
				else if(board[i][j]==2)
				{
					weightwhite=weightwhite+(evalweight[i][j]);
				}
			}
		}
		if (player==1)
		{
			return weightblack-weightwhite;
		}
		else
		{
			return weightwhite-weightblack;
		}
		
	}
	public ArrayList<Position> getWhitePosition() {
		return whitePosition;
	}
	
	public void setWhitePosition(ArrayList<Position> whitePosition) {
		this.whitePosition = whitePosition;
	}
	public void setBlackPosition(ArrayList<Position> blackPosition) {
		this.blackPosition = blackPosition;
	}
	public ArrayList<Position> getBlackPosition() {
		return blackPosition;
	}
	
	public void getPositions()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(board[i][j]==1)
				{
					blackPosition.add(new Position(i,j));
				}
				else if(board[i][j]==2)
				{
					whitePosition.add(new Position(i,j));
				}
			}
		}
	}
	
	public void print()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}
	

}
