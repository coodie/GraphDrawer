package GUI;

import java.awt.event.WindowListener;

import graph.Graph;
import graph.Graph2D;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import algorithm.GraphAlgorithm2DClass;

public class AlgorithmPanel
{
	
	public static final void display(GraphComponent comp)
	{
		String s = (String)JOptionPane.showInputDialog(
                comp,
                "Choose algorithm",
                "", JOptionPane.PLAIN_MESSAGE,
                null, algList,
                "Bellman Ford");
		if(s == null)
			return;
		GraphAlgorithm2DClass alg = new GraphAlgorithm2DClass(comp.getGraph());
		if(s.equals(algList[0]))
		{
			String source = JOptionPane.showInputDialog(comp,"Set starting vertex");
			if(source == null) return;
			String target = JOptionPane.showInputDialog(comp, "Set final vertex");
			if(target == null) return;
			alg.BellmanFord(Integer.parseInt(source), Integer.parseInt(target));
		}
		if(s.equals(algList[1]))
			alg.bridges();
		if(s.equals(algList[2]))
			alg.ForceBasedDrawing(comp.getWidth(), comp.getHeight(),true);
		if(s.equals(algList[3]))
			alg.Kruskal();
		if(s.equals(algList[4]))
			alg.matching();
		if(s.equals(algList[5]))
			alg.Tarjan();
	}
	
	public static final String[] algList =
		{"Bellman Ford",
		"Bridges",
		"Force Based Graph Drawing",
		"Minimal Spanning Forest",
		"Max Matching",
		"Strongly Connected Components"
		};
}
