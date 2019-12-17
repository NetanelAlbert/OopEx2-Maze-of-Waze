package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DGraph implements graph{
	private HashMap<Integer, node_data> nodes = new HashMap<Integer, node_data>();
	private int edges = 0;
	
	public DGraph() {}
	/**
	 * 
	 * @param s - String in toString() format
	 */
	public DGraph(String s) {
		String[] parts = s.split(" : ");
		this.edges = Integer.parseInt(parts[0]);
		String[] edges = parts[1].split("\n");
		for (String string : edges) {
			DNode n = new DNode(string);
			nodes.put(n.getKey(), n);
		}
	}
	

	@Override
	public node_data getNode(int key) {
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		DNode n = (DNode) nodes.get(src);  			// TODO check if can do without cast
		return  n != null ? n.get(dest) : null;
	}

	@Override
	public void addNode(node_data n) {
		nodes.put(n.getKey(), n);
	}

	@Override
	public void connect(int src, int dest, double w) {
		DNode n = (DNode) nodes.get(src);			// TODO check if can do without cast
		if(n != null && nodes.get(dest) != null) {
			DEdge e = new DEdge(src, dest, w);
			n.put(dest, e);
			edges++;
		} else {
			throw new RuntimeException("Cant connect unexist vertics ("+src+","+dest+")");
		}
	}

	@Override
	public Collection<node_data> getV() {
		return nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		DNode n = (DNode) nodes.get(node_id);
		return n != null ? n.values() : null;
	}

	@Override
	public node_data removeNode(int key) {
		node_data del = nodes.remove(key);
		if(del != null) {
			edges -= ((DNode)del).size();
			
			for (Iterator<Integer> it = nodes.keySet().iterator(); it.hasNext();) {
				removeEdge(it.next(), key);
			}
			
			
			
			for (Iterator<node_data> it = nodes.values().iterator(); it.hasNext();) {
				DNode node = (DNode) it.next();
				if(node.remove(key) != null)
					edges--;
			}
		}
		return del;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		DNode sr = (DNode) nodes.get(src);
		edge_data e = sr  != null ? sr.remove(dest) : null;
		if(e != null)
			edges--;
		return e;
	}

	@Override
	public int nodeSize() {
		return nodes.size();
	}

	@Override
	public int edgeSize() {
		return edges;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(edges);
		sb.append(" : ");
		for (Iterator<node_data> it = nodes.values().iterator(); it.hasNext();) {
			sb.append(it.next() + "\n");
		}
		if(nodes.size() > 0)
			sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}

}
