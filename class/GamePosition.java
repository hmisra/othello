import java.io.Serializable;


public class GamePosition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int i;
	int j;
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	
}
