package tests;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import utils.Point3D;

public class SandBox {
	public static void main(String[] args) {
		DGraph g = new DGraph();
		g.addNode(new DNode(1, new Point3D(1, 0), 4));
		g.addNode(new DNode(2, new Point3D(0, 1), 3));
		g.addNode(new DNode(3, new Point3D(-1, 0), 2));
		g.addNode(new DNode(4, new Point3D(0, -1), 1));
		g.connect(2, 3, 3.4);
		g.connect(3, 1, 1.1);
		g.connect(1, 2, 1.5);
		
		System.out.println(g);
		
		Graph_Algo ga = new Graph_Algo();
		ga.init(g);
		ga.save("bla.txt");
		
		Graph_Algo gb = new Graph_Algo();
		gb.init("bla.txt");
		
		System.out.println("\n\n"+gb.myGraph);

	}
}
