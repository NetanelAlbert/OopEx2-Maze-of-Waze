package dataStructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import utils.Point3D;

public class DNode extends HashMap<Integer, edge_data> implements node_data , Comparable<node_data>{
	private int key;
	private Point3D location = null;
	private double weight;
	private String info = "";
	private int tag;

	public DNode(int key, Point3D location, double weight, String info, int tag) {
		this.key = key;
		this.location = new Point3D(location);
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}

	public DNode(int key, Point3D location, double weight) {
		this.key = key;
		this.location = new Point3D(location);
		;
		this.weight = weight;
	}

	public DNode(DNode n) {
		this.key = n.key;
		this.location = new Point3D(n.location);
		;
		this.weight = n.weight;
		this.info = n.info;
		this.tag = n.tag;
	}

	/**
	 * 
	 * @param s - String in toString() format
	 */
	public DNode(String s) {
		String[] parts = s.split(" @ ");
		String[] params = parts[0].split(", ");
		this.key = Integer.parseInt(params[0]);
		this.location = new Point3D(params[1]);
		this.weight = Double.parseDouble(params[2]);
		this.info = params[3];
		this.tag = Integer.parseInt(params[4]);

		if (parts.length > 1) {
			String[] edges = parts[1].split("#");
			for (String string : edges) {
				DEdge e = new DEdge(string);
				if (e.getSrc() == getKey())
					put(e.getDest(), e);
				else
					throw new RuntimeException("Edge src is " + e.getSrc() + " but source node key is " + getKey());
			}
		}
	}

	@Override
	public int getKey() {
		return key;
	}

	@Override
	public Point3D getLocation() {
		return location;
	}

	@Override
	public void setLocation(Point3D p) {
		location = new Point3D(p);
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double w) {
		weight = w;
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

		StringBuilder sb = new StringBuilder(key + ", " + location + ", "
		+ weight + ", " + info + ", " + tag + " @ ");
		for (Iterator<edge_data> it = values().iterator(); it.hasNext();) {
			sb.append(it.next());
			sb.append("#");
		}
		if (size() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@Override
	public int compareTo(node_data o) {
		Double comp = getWeight();
		return comp.compareTo(o.getWeight());
	}

}
