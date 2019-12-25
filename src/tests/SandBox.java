package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import gui.GraphGui;
import utils.Point3D;

public class SandBox {
	public static void main(String[] args) {
		DGraph g = new DGraph();
		g.addNode(new DNode(1, new Point3D(50, 150)));
		g.addNode(new DNode(2, new Point3D(110, 110)));
		g.addNode(new DNode(3, new Point3D(180, 130)));
		g.addNode(new DNode(4, new Point3D(150, 240)));
		g.addNode(new DNode(5, new Point3D(360, 270)));
		
		

		g.connect(1, 2, 3.4);
		g.connect(2, 3, 1.1);
		g.connect(3, 4, 1.5);
		g.connect(4, 5, 2.7);
		
		g.connect(5, 1, 2.8);
		
		
		
		Graph_Algo ga = new Graph_Algo(g);
		
		
		//System.out.println("Connected: "+ ga.isConnected());
		
		
		//System.out.println("\ng: \n"+g);
		//System.out.println("\ncopy: \n"+ga.copy());
		//System.out.println("\n\nrevers g: \n"+g.getReversCopy());
		
		//ga.save("bla.txt");
		
		//Graph_Algo gb = new Graph_Algo();
		//gb.init("bla.txt");
		
		//System.out.println("\n\n"+gb.myGraph);
		
		//System.out.println("Dest: " + ga.shortestPathDist(1, 1));
		
		//GraphGui gui = new GraphGui(g);
		Integer[] arr = {4,2,5};
		System.out.println(ga.TSP(Arrays.asList(arr)));
	}
}
