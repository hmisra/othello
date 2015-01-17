import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class TimeSync {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		GameHistory gh=new GameHistory();
		gh.setNumberOfMovesInThisGame(16);
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("GameHistory.dat")); 
		out1.writeObject(gh);
		out1.close();
		
		
		
	}
}
