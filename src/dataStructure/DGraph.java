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
		if(nodes.containsKey(n.getKey()))
			throw new RuntimeException("The graph already contains Node with key "+n.getKey());
		
		nodes.put(n.getKey(), n);
		//notifyAll();
	}

	@Override
	public void connect(int src, int dest, double w) {
		DEdge e = new DEdge(src, dest, w);
		connect(e);
	}
	
	private void connect(DEdge e) {
		DNode n = (DNode) nodes.get(e.getSrc());			// TODO check if can do without cast
		if(n != null && nodes.get(e.getDest()) != null) {
			n.put(e.getDest(), e);
			edges++;
			//notifyAll();
		} else {
			throw new RuntimeException("Cant connect unexist vertics ("+e.getSrc()+","+e.getDest()+")");
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
			//notifyAll();
		}
		return del;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		DNode sr = (DNode) nodes.get(src);
		edge_data e = sr  != null ? sr.remove(dest) : null;
		if(e != null) {
			//notifyAll();
			edges--;
		}
		return e;
	}
	
	public DGraph getReversCopy() {
		DGraph copy = new DGraph();
		// copy Nodes
		for (Iterator<node_data> iterator = getV().iterator(); iterator.hasNext();) {
			DNode n = new DNode((DNode)iterator.next());
			n.clear();
			copy.addNode(n);
		}
				
		for (Iterator<node_data> itNodes = getV().iterator(); itNodes.hasNext();) {
			DNode n = (DNode)itNodes.next();
			for (Iterator<edge_data> itEdges = getE(n.getKey()).iterator(); itEdges.hasNext();) {
				DEdge e = (DEdge) itEdges.next();
				copy.connect(e.getReversEdge());
			}
		}
		return copy;
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
