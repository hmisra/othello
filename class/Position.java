import java.util.ArrayList;


public class Position {
	int i;
	int j;
	ArrayList<Position> parentPosition=new ArrayList<Position>();
	ArrayList<String> direction=new ArrayList<String>();
	
	public ArrayList<String> getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction.add(direction);
	}



	public Position(int i,int j) {
		this.i=i;
		this.j=j;
	}
	
	
	
	public ArrayList<Position> getParentPosition() {
		return parentPosition;
	}
	public void setParentPosition(Position parentPosition) {
		this.parentPosition.add(parentPosition);
	}
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
