package dataStructure;

public class DEdge implements edge_data{
	private int src;
	private int dest;
	private double weight;
	private String info = "";
	private int tag;
	

	public DEdge(int src, int dest, double weight, String info, int tag) {
		if(weight < 0)
			throw new RuntimeException("Can't set negativ waight ("+weight+")");
		
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}

	public DEdge(int src, int dest, double weight) {
		this(src, dest, weight, "", 0);
	}

	public DEdge(DEdge e) {
		this(e.src, e.dest, e.weight, e.info, e.tag);
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
	
	public DEdge getReversEdge() {
		DEdge e = new DEdge(this);
		int tmp = e.dest;
		e.dest = e.src;
		e.src = tmp;
		return e;
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
