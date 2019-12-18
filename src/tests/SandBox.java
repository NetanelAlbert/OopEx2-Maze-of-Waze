package tests;

import java.util.concurrent.PriorityBlockingQueue;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.node_data;
import utils.Point3D;

public class SandBox {
	public static void main(String[] args) {
		DGraph g = new DGraph();
		g.addNode(new DNode(1, new Point3D(1, 0), 1.4));
		g.addNode(new DNode(2, new Point3D(0, 1), 3.2));
		g.addNode(new DNode(3, new Point3D(-1, 0), 3.3));
		g.addNode(new DNode(4, new Point3D(0, -1), 1.5));
		g.connect(1, 2, 3.4);
		g.connect(2, 3, 1.1);
		g.connect(3, 4, 1.5);
		g.connect(4, 1, 0.2);
		g.connect(2, 4, 0.2);
		
		Graph_Algo ga = new Graph_Algo();
		ga.init(g);

		//System.out.println("Connected: "+ ga.isConnected());
		
		
		//System.out.println("\ng: \n"+g);
		//System.out.println("\ncopy: \n"+ga.copy());
		//System.out.println("\n\nrevers g: \n"+g.getReversCopy());
		
		//ga.save("bla.txt");
		
		//Graph_Algo gb = new Graph_Algo();
		//gb.init("bla.txt");
		
		//System.out.println("\n\n"+gb.myGraph);
		double ans = ga.shortestPathDist(1, 4);
		
		System.out.println("Dest: " + ga.shortestPathDist(1, 4));
		

	}
}
