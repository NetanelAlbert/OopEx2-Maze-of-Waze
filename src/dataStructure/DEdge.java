package dataStructure;

public class DEdge implements edge_data{
	private int src;
	private int dest;
	private double weight;
	private String info = null;
	private int tag;
	

	public DEdge(int src, int dest, double weight, String info, int tag) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}

	public DEdge(int src, int dest, double weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
	}

	public DEdge(DEdge e) {
		this.src = e.src;
		this.dest = e.dest;
		this.weight = e.weight;
		this.info = e.info;
		this.tag = e.tag;
	}
	
	/**
	 * 
	 * @param s - String in toString() format
	 */
	public DEdge(String s) {
		String[] arr = s.split(", ");
		this.src = Integer.parseInt(arr[0]);
		this.dest = Integer.parseInt(arr[1]);
		this.weight = Double.parseDouble(arr[2]);
		this.info = arr[3];
		this.tag = Integer.parseInt(arr[4]);
	}

	@Override
	public int getSrc() {
		return src;
	}

	@Override
	public int getDest() {
		return dest;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		info = s;
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		tag = t;
	}
	
	
	@Override
	public String toString() {
		return src + ", " + dest + ", " + weight + ", " + info + ", " + tag;
	}

}
