public class Input {
	int playerColor;
	int cutOffDepth;
	int timeLeft;
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	int algorithm;
	int [][] initialBoard;
	
	public int getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}
	public int getPlayerColor() {
		return playerColor;
	}
	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}
	public int getCutOffDepth() {
		return cutOffDepth;
	}
	public void setCutOffDepth(int cutOffDepth) {
		this.cutOffDepth = cutOffDepth;
	}
	public int[][] getInitialBoard() {
		return initialBoard;
	}
	public void setInitialBoard(int[][] initialBoard) {
		this.initialBoard = initialBoard;
	}

	public void print()
	{
		System.out.println(playerColor);
		System.out.println(cutOffDepth);
		for(int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
			{
				System.out.print(initialBoard[i][j]+" ");
			}
			System.out.println();
		}
	}


}
