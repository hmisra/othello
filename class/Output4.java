import java.util.Comparator;
import java.util.PriorityQueue;


public class Output4 {
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

	public PriorityQueue<ResultState> getQueue() {
		return queue;
	}

	public void setQueue(ResultState queue) {
		this.queue.add(queue);
	}
	

}
