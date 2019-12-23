package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.node_data;

public class GraphGui extends JFrame implements ActionListener, MenuListener {
	private DGraph graph;
	private Graph_Algo algo;
	private java.util.List<node_data> path;
	private JMenu load;

	public GraphGui() {
		this(new DGraph());
	}

	public GraphGui(DGraph graph) {
		this.graph = graph;
		this.algo = new Graph_Algo(graph);
		setTitle("Maze of Waze");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setSize(700, 500);
		setMenuBar();
		setButtons();

		setVisible(true);

	}

	private void setButtons() {
		distCalc.setBounds(150, 10, 120, 30);
		distCalc.addActionListener(this);
		pathCalc.setBounds(150, 10, 90, 30);
		pathCalc.addActionListener(this);
		close.setBounds(0, 10, 50, 30);
		close.setBackground(Color.RED);
		close.addActionListener(this);
	}

	private void setMenuBar() {
		JMenu file = new JMenu("File");
		file.addMenuListener(this);
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(this);
		file.add(save);

		load = new JMenu("Load");
		File[] files = new File("saves").listFiles();
		for (File f : files) {
			String s = f.toString();
			// cut the "saves\" from start and the ".txt" from end
			JMenuItem loadItem = new JMenuItem(s.substring(6, s.length() - 4));
			loadItem.addActionListener(this);
			load.add(loadItem);
		}
		file.add(load);

		JMenu algorithms = new JMenu("Algorithms");
		algorithms.addMenuListener(this);

		JMenuItem isConnected = new JMenuItem("Is connected?");
		isConnected.addActionListener(this);
		algorithms.add(isConnected);

		JMenuItem dist = new JMenuItem("Distance (a->b)");
		dist.addActionListener(this);
		algorithms.add(dist);

		JMenuItem path = new JMenuItem("Shortest path (a->b)");
		path.addActionListener(this);
		algorithms.add(path);

		JMenuItem tsp = new JMenuItem("TSP");
		tsp.addActionListener(this);
		algorithms.add(tsp);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(algorithms);

		setJMenuBar(menuBar);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("Courier", Font.PLAIN, 20));
		for (node_data n : graph.getV()) {
			g.setColor(Color.black);
			g.fillOval(n.getLocation().ix() - 5, n.getLocation().iy() - 5, 10, 10);
			g.drawString("" + n.getKey(), n.getLocation().ix(), n.getLocation().iy() + 15);

			for (edge_data e : graph.getE(n.getKey())) {
				g.setColor(Color.RED);
				drawEdge(g, e);
			}
		}

		if (path != null) { // color the edges saved in path in blue
			for (int i = 0; i < path.size() - 1; i++) {
				DNode n = (DNode) path.get(i);
				int next = path.get(i + 1).getKey();
				g.setColor(Color.BLUE);
				drawEdge(g, n.get(next));
			}
		}
	}

	private void drawEdge(Graphics g, edge_data e) {
		node_data src = graph.getNode(e.getSrc());
		node_data dest = graph.getNode(e.getDest());
		int x1 = src.getLocation().ix();
		int y1 = src.getLocation().iy();
		int x2 = dest.getLocation().ix();
		int y2 = dest.getLocation().iy();

		g.drawLine(x1, y1, x2, y2);
		g.drawString("" + e.getWeight(), (x1 + 4 * x2) / 5, (y1 + 4 * y2) / 5);
		
		g.setColor(Color.yellow);
		g.fillRect(((x1 + 7 * x2) / 8 - 4), ((y1 + 7 * y2) / 8 - 4), 8, 8);
		
	}

	private JSpinner src;
	private JSpinner dest;
	private JButton distCalc = new JButton("Calc distance");
	private JButton pathCalc = new JButton("Calc path");
	private JButton close = new JButton("X");

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(e.getActionCommand());

		switch (e.getActionCommand()) {
		case "Is connected?":
			String massage;
			if (algo.isConnected())
				massage = "The graph is strongly connected";
			else
				massage = "The graph isn't strongly connected";

			showMassage(e.getActionCommand(), massage);
			break;

		case "Distance (a->b)":
			remove(pathCalc);
			add(distCalc);
			setSpinners();

			break;

		case "Calc distance":
			int from = (Integer) src.getValue();
			int to = (Integer) dest.getValue();
			double ans = algo.shortestPathDist(from, to);
			String distance = (ans < Double.MAX_VALUE) ? ans + "" : "Infinity";
			showMassage("Distance (" + from + "->" + to + ")",
					"The distance from " + from + " to " + to + " is: " + distance);

			break;

		case "Shortest path (a->b)":
			remove(distCalc);
			add(pathCalc);
			setSpinners();

			break;

		case "Calc path":
			from = (Integer) src.getValue();
			to = (Integer) dest.getValue();
			path = algo.shortestPath(from, to);
			String pathDesc = "There is no path from " + from + " to " + to;
			if (path != null) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < path.size(); i++) {
					sb.append(path.get(i).getKey());
					if (i != path.size() - 1)
						sb.append("->");
				}
				pathDesc = sb.toString();
			}
			showMassage("shortest path (" + from + "->" + to + ")", pathDesc);
			repaint();

			break;

		case "TSP":

			break;

		case "X": // close buttons
			if (src != null)
				remove(src);
			if (dest != null)
				remove(dest);
			remove(distCalc);
			remove(pathCalc);
			remove(close);
			setVisible(false);
			setVisible(true);

			break;

		case "Save":
			String fileName = JOptionPane.showInputDialog(null, "Insert name file", "Graph");
			if (fileName != null && fileName.length() > 0) {
				algo.save("saves\\" + fileName + ".txt");
				JMenuItem loadItem = new JMenuItem(fileName);
				loadItem.addActionListener(this);
				load.add(loadItem);
			} else
				showMassage("Error!", "Can't save with an empty name");

			break;

		default: // this is a file name we want to load
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
					"If you load without saving you'll lose the currnt graph" + "\nDo you want to load?")) {
				algo.init("saves\\" + e.getActionCommand() + ".txt");
				graph = algo.getGraph();
				repaint();
			}
			break;
		}
	}

	/**
	 * set the 2 spinners to the current Nodes list and show them
	 */
	private void setSpinners() {
		if (src != null)
			remove(src);
		if (dest != null)
			remove(dest);

		ArrayList<Integer> vertexes = new ArrayList<Integer>(graph.getVnums());
		src = new JSpinner(new SpinnerListModel(vertexes));
		src.setFont(new Font("Courier", Font.BOLD, 20));
		src.setBounds(50, 10, 50, 30);
		add(src);

		dest = new JSpinner(new SpinnerListModel(vertexes));
		dest.setFont(new Font("Courier", Font.BOLD, 20));
		dest.setBounds(100, 10, 50, 30);
		add(dest);

		add(close);

		setVisible(false);
		setVisible(true);

	}

	/**
	 * Show a small window with the massage
	 * 
	 * @param title   - the title of the window
	 * @param massage - the text inside the window
	 */
	private void showMassage(String title, String massage) {
		new Massage(title, massage, new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (path != null) {
					path = null;
					repaint();
				}
			}
		});
	}

	@Override
	public void menuSelected(MenuEvent e) {
		// do nothing
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		setVisible(false);
		setVisible(true); // to reshow the graph where the menu hide it
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// do nothing
	}
	
	private class Massage extends JFrame {
		final String massage;

		public Massage(String title, String massage, WindowAdapter listenr) {
			setTitle(title);
			setSize(350, 100);
			setLocation(150, 100);
			this.massage = massage;
			addWindowListener(listenr);
			setVisible(true);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setFont(new Font("Courier", Font.BOLD, 17));
			g.drawString(massage, 20, 70);
		}

	}

}
