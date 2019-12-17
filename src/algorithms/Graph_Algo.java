package algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import dataStructure.DEdge;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{
	public graph myGraph = new DGraph();
	@Override
	public void init(graph g) {
		// TODO Auto-generated method stub
		myGraph = g; // TODO copy?
	}

	@Override
	public void init(String file_name) {
		// TODO Auto-generated method stub
		String content = "";
	    try
	    {
	        content = new String(Files.readAllBytes( Paths.get(file_name)));
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    	throw new RuntimeException(e.getMessage());
	    }
	    //if(content.length() > 0)
	    	myGraph = new DGraph(content);
	    
	}

	@Override
	public void save(String file_name) {
		
		try {
			PrintWriter out = new PrintWriter(file_name);
			out.print(myGraph);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RuntimeException(e.getMessage());
		}
		
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public graph copy() {
		DGraph g = new DGraph();
		Collection<node_data> nodes = myGraph.getV();
		// copy Nodes
		for (Iterator<node_data> iterator = nodes.iterator(); iterator.hasNext();) {
			g.addNode(new DNode((DNode)iterator.next()));
		}
		
		// for each Node, copy Edges
		for (Iterator<node_data> itNodes = nodes.iterator(); itNodes.hasNext();) {
			DNode myNode = (DNode) itNodes.next();
			DNode gNode = (DNode) g.getNode(myNode.getKey());
			Collection<edge_data> edges = gNode.values(); //myGraph.getE(myNode.getKey());
			
			for (Iterator<edge_data> itEdges = edges.iterator(); itEdges.hasNext();) {
				DEdge edge = (DEdge) itEdges.next();
				gNode.put(edge.getDest(), new DEdge(edge));
			}
		
		}
		return g;
	}

}
