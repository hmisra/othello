import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class BlackMoves implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<PriorityQueue <Data>> list=new ArrayList<PriorityQueue<Data>>();
	public ArrayList<PriorityQueue<Data>> getList() {
		return list;
	}
	public void setList(PriorityQueue<Data> pq) {
		this.list.add(pq);
	}

}
