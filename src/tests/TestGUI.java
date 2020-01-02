package tests;

import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.graph;
import gui.GraphGui;
import utils.Point3D;

public class TestGUI {

	/**
	 * A simple run of some graph in GUI
	 */
	public static void main(String[] args) {
		graph g = new DGraph();
		new GraphGui(g);
		
		g.addNode(new DNode(1,new Point3D(100,150)));
		g.addNode(new DNode(2,new Point3D(500,150)));
		g.addNode(new DNode(3,new Point3D(100,350)));
		g.addNode(new DNode(4,new Point3D(500,350)));
		g.addNode(new DNode(5,new Point3D(300, 60)));
		g.addNode(new DNode(6,new Point3D(300,440)));
		

		g.connect(1, 4, 1.45);
		g.connect(3, 1, 0.7);
		g.connect(4, 2, 3.5);
		g.connect(2, 3, 1.72);
		g.connect(2, 5, 4.1);
		g.connect(5, 1, 2.2);
		g.connect(4, 6, 1.6);
		g.connect(6, 3, 1);
		
	}

}
