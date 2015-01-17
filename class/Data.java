import java.io.Serializable;


public class Data implements Serializable, Comparable<Data> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int value;
	String node;
	public Data(int value, String node) {
	
		this.value=value;
		this.node=node;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public int compareTo(Data o) {
		return o.getValue()-this.getValue();
	}
	

}
