import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Output2 {
	
	ArrayList<Log> log=new ArrayList<Log>();
	
	
	PriorityQueue<ResultState> queue =new PriorityQueue<ResultState>(20, new Comparator<ResultState>() {

		public int compare(ResultState o1, ResultState o2) {

			if(o1.getMaxvalue()<o2.getMaxvalue())
			{
				return -1;
			}
			else if(o1.getMaxvalue()>o2.getMaxvalue())
			{
				return 1;
			}
			else 
			{
				return 0;
			}

		}});
	public ResultState getQueue() {
		return queue.peek();
	}
	public void setQueue(ResultState queue) {
		this.queue.add(queue);
	}
	
	public ArrayList<Log> getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log.add(log);
	}
	
	
}
