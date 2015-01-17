import java.io.Serializable;


public class GameHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	int numberOfGames;
//	int[] numberOfMoves=new int[100];//check if new is allowed in de-serialization if not working
	int numberOfMovesInThisGame;
//	int[] result=new int[100];
//	String[][] gameHistory=new String[100][100];
	public GameHistory() {
		
	}
//	public GameHistory(int numberOfGames, int numberOfMovesInThisGame) {
//		this.numberOfGames=numberOfGames;
//		this.numberOfMovesInThisGame=numberOfMovesInThisGame;
//	}
//	public void newCompetition()
//	{
//		this.numberOfGames=0;
//		for(int i=0;i<11;i++)
//		{
//			for(int j=0;j<100;j++)
//			{
//				gameHistory[i][j]=null;
//			}
//		}
//	}
//	public int getNumberOfGames() {
//		return numberOfGames;
//	}
//	public void setNumberOfGames(int numberOfGames) {
//		this.numberOfGames = numberOfGames;
//	}
//	public String getGameHistory(int i, int j) {
//		return gameHistory[i][j];
//	}
//	public void setGameHistory(int i, int j, String move) {
//		gameHistory[i][j]=move;
//	}
//	public int[] getNumberOfMoves() {
//		return numberOfMoves;
//	}
//	public void setNumberOfMoves(int[] numberOfMoves) {
//		this.numberOfMoves = numberOfMoves;
//	}
	public int getNumberOfMovesInThisGame() {
		return numberOfMovesInThisGame;
	}
	public void setNumberOfMovesInThisGame(int numberOfMovesInThisGame) {
		this.numberOfMovesInThisGame = numberOfMovesInThisGame;
	}
//	public String[][] getGameHistory() {
//		return gameHistory;
//	}
//	public void setGameHistory(String[][] gameHistory) {
//		this.gameHistory = gameHistory;
//	}

}
