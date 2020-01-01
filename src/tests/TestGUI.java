package tests;

import algorithms.Graph_Algo;
import gui.GraphGui;

public class TestGUI {

	/**
	 * A simple run of some graph in GUI
	 */
	public static void main(String[] args) {
		Graph_Algo ga = new Graph_Algo();
		ga.init("saves\\House.txt");
		new GraphGui(ga.getGraph());
	}

}
