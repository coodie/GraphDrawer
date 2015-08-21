package GUI;

import graph.Graph2D;

import javax.swing.JFrame;

import algorithm.ForceBasedDrawing;
/*
 * Frame which contains GraphComponent,
 * on which the graph is drawn.
 * 
 * Simply give it a graph object and make it visible.
 */
public class MainFrame extends JFrame
{
	public MainFrame(Graph2D G)
	{
		this.G = G;
		GraphComponent comp = new GraphComponent(G);
		ForceBasedDrawing alg = new ForceBasedDrawing(G, DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
		alg.setSteps(5000);
		alg.startAlgorithm();
		GraphComponentKeyShortcuts shortcut = new GraphComponentKeyShortcuts(comp);
		comp.addKeyListener(shortcut);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		add(comp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private Graph2D G;
	public static final int DEFAULT_HEIGHT = 500;
	public static final int DEFAULT_WIDTH = 500;
}
