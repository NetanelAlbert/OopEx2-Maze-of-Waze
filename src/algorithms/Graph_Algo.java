package algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

import dataStructure.DEdge;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms which should
 * be implemented as part of Ex2 - Do edit this class.
 * 
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms {
	private static final int NOT_VISITED = 0, VISITED = 1, FINISH = 2;
	public DGraph myGraph;

	public Graph_Algo() {
		this(new DGraph());
	}

	public Graph_Algo(DGraph g) {
		init(g);
	}

	@Override
	public void init(graph g) {
		myGraph = (DGraph) g;
	}

	@Override
	public void init(String file_name) {
		// TODO Auto-generated method stub
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(file_name)));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		if (content.length() > 0)
			myGraph = new DGraph(content);

	}

	@Override
	public void save(String file_name) {

		try {
			PrintWriter out = new PrintWriter(file_name);
			out.print(myGraph);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	// Kosaraju’s DFS (two DFS traversals) - O(V+E)
	@Override
	public boolean isConnected() {
		if (myGraph == null || myGraph.nodeSize() == 0 ||
				myGraph.nodeSize() > myGraph.edgeSize())
			return false;

		DGraph copy = (DGraph) copy();

		// check if can get from arbitrary node to each node
		for (Iterator<node_data> it = copy.getV().iterator(); it.hasNext();) {
			it.next().setTag(NOT_VISITED);
		}
		DNode arbitrary = (DNode) copy.getV().iterator().next();
		DFS(copy, arbitrary);
		for (Iterator<node_data> it = copy.getV().iterator(); it.hasNext();) {
			node_data node = it.next();
			if (node.getTag() != FINISH) {
				System.out.println("S, not finish: " + node.getKey() + " color: " + node.getTag());
				return false;
			}
		}

		// check if can get from each node to same arbitrary node
		DGraph reverse = copy.getReversCopy();
		for (Iterator<node_data> it = reverse.getV().iterator(); it.hasNext();) {
			it.next().setTag(NOT_VISITED);
		}
		arbitrary = (DNode) reverse.getNode(arbitrary.getKey());
		DFS(reverse, arbitrary);
		for (Iterator<node_data> it = reverse.getV().iterator(); it.hasNext();) {
			node_data node = it.next();
			if (node.getTag() != FINISH) {
				System.out.println("R, not finish: " + node.getKey() + " color: " + node.getTag());
				return false;
			}
		}

		return true;
	}

	private void DFS(DGraph g, DNode n) {
		n.setTag(VISITED);
		for (Iterator<Integer> it = n.keySet().iterator(); it.hasNext();) {
			DNode neighbor = (DNode) g.getNode(it.next());
			if (neighbor.getTag() == NOT_VISITED)
				DFS(g, neighbor);
		}
		n.setTag(FINISH);
	}

	// Dijkstra's Shortest Path First algorithm
	@Override
	public double shortestPathDist(int src, int dest) {
		DNode s = (DNode) myGraph.getNode(src);
		DNode d = (DNode) myGraph.getNode(dest);

		if (s == null || d == null)
			throw new RuntimeException("Source or Destenation Nodes dosn't exist (" + src + "," + dest + ")");

		// Mark all nodes unvisited and set weight 0
		for (Iterator<node_data> it = myGraph.getV().iterator(); it.hasNext();) {
			node_data n = it.next();
			n.setTag(NOT_VISITED);
			n.setWeight(Double.MAX_VALUE);
			((DNode) n).setFather(null);
		}

		s.setWeight(0);
		PriorityBlockingQueue<node_data> notVisited = new PriorityBlockingQueue<node_data>(myGraph.getV());

		while (!notVisited.isEmpty()) {
			node_data current = notVisited.remove();
			if (current.getWeight() == Double.MAX_VALUE || current.getKey() == dest)
				return current.getWeight();

			// change all current unvisited neighbours weight if found shorter path
			for (edge_data e : ((DNode) current).values()) {

				node_data neighbour = myGraph.getNode(e.getDest());
				double newWeight = current.getWeight() + e.getWeight();
				if (neighbour.getTag() == NOT_VISITED && newWeight < neighbour.getWeight()) {
					neighbour.setWeight(newWeight);
					notVisited.remove(neighbour);
					notVisited.add(neighbour);
					((DNode) neighbour).setFather(current);
				}

			}

			current.setTag(VISITED);

		}

		return Double.MAX_VALUE;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if (shortestPathDist(src, dest) < Double.MAX_VALUE) {
			ArrayList<node_data> ans = new ArrayList<node_data>();
			DNode cuurent = (DNode) myGraph.getNode(dest);
			do {
				ans.add(0, cuurent);
			} while ((cuurent = cuurent.getFather()) != null);
			return ans;
		}
		return null;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		if (targets.isEmpty() || !isConnected()) // TODO just check if sub graph is connected
			return null;
		
		List<Integer> targs = new ArrayList<Integer>(targets); // to allow remove() and removeAll()
		List<node_data> ans = new ArrayList<node_data>();
		
		Iterator<Integer> it = targs.iterator();
		int dest = it.next(), src = dest;
		while (it.hasNext() && !targs.isEmpty()) {
			src = dest;
			dest = it.next();
			
			if(!ans.isEmpty() && ans.get(ans.size()-1).getKey() == src)
				ans.remove(ans.size()-1);
			
			if (targs.contains(dest)) {
				List<node_data> tmp = shortestPath(src, dest);
				targs.removeAll(nodesToInts(tmp));
				ans.addAll(tmp);
			}
			
		}

		return ans;
	}

	private List<Integer> nodesToInts(List<node_data> list) {
		List<Integer> ans = new ArrayList<Integer>();
		for (node_data n : list) {
			ans.add(n.getKey());
		}
		return ans;
	}

	@Override
	public graph copy() {
		DGraph copy = new DGraph();
		Collection<node_data> nodes = myGraph.getV();
		// copy Nodes
		for (Iterator<node_data> iterator = nodes.iterator(); iterator.hasNext();) {
			copy.addNode(new DNode((DNode) iterator.next()));
		}

		// for each Node, copy Edges
		for (Iterator<node_data> itNodes = nodes.iterator(); itNodes.hasNext();) {
			DNode orgNode = (DNode) itNodes.next();
			DNode copyNode = (DNode) copy.getNode(orgNode.getKey());
			Collection<edge_data> orgEdges = orgNode.values(); // myGraph.getE(myNode.getKey());

			for (Iterator<edge_data> itEdges = orgEdges.iterator(); itEdges.hasNext();) {
				DEdge edge = (DEdge) itEdges.next();
				copyNode.put(edge.getDest(), new DEdge(edge));
			}

		}
		return copy;
	}

	public DGraph getGraph() {
		return myGraph;
	}

}
