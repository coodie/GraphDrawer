import graph.*;

import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

import utility.GraphInputAnalyser;

import GUI.GraphComponent;
import GUI.GraphComponentKeyShortcuts;
import GUI.MainFrame;
import GUI.StartFrame;
import algorithm.ForceBasedDrawing;
import algorithm.GraphAlgorithmClass;
import algorithm.GraphAlgorithm2DClass;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.SortedSet;

public class Main
{
	//public final static String[] allowedArgs = {"-0","-d","-w","-r"};
	
	/**
	 * @param args
	 * -0 is for removing 0 when drawing graph
	 * -d is for drawing directed graph
	 * -w is for drawing weighted graph
	 * -r is for randomizing graph. Max weight on weighted graphs is from 0 to 50.
	 * -a is for automatic usage
	 * 
	 * -g is when we don't want to use GUI, and simply skip to command line mode
	 */
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		boolean gui = true;
		for(String p : args)
		{
			if(p.equals("-g"))
				gui = false;
		}
		if(gui)
		{
			StartFrame guiframe = new StartFrame();
			guiframe.setVisible(true);
			return;
		}
		
		
		boolean random = false;
		boolean weighted = false;
		boolean zero = false;
		boolean directed = false;
		boolean auto = false;
		for(String p : args)
		{
			if(p.equals("-w"))
				weighted = true;
			else
			if(p.equals("-d"))
				directed = true;
			else
			if(p.equals("-0"))
				zero = true;
			else
				if(p.equals("-r"))
					random = true;
			else
				if(p.equals("-a"))
					auto = true;
		}
		
		Scanner in = new Scanner(System.in);
		
		Graph2D G = null;
		
		if(auto)
		{
			StringBuilder build = new StringBuilder();
			while(in.hasNextInt()) build.append(in.nextInt() + " ");
			GraphInputAnalyser analyser = new GraphInputAnalyser(build.toString());
			if(!analyser.analyse())
			{
				System.out.println("WRONG INPUT");
				System.exit(0);
			}
			G = analyser.getGraph();
			
		}
		else
		if(random)
		{
			int n = in.nextInt();
			int m = in.nextInt();
			Graph2DRandomizer rand = new Graph2DRandomizer(directed, weighted);
			G = rand.random(n,m);
		}
		else
		{
			int n = in.nextInt();
			int m = in.nextInt();
			int a, b, c;
			G = new Graph2D(n);
			G.setDirected(directed);
			G.setWeighted(weighted);
			for(int i = 0; i < m; i++)
				if(weighted)
				{
					a = in.nextInt();
					b = in.nextInt();
					c = in.nextInt();
					G.addEdge(a, b, c);
				}
				else
				{
					a = in.nextInt();
					b = in.nextInt();
					G.addEdge(a, b);
				}
		}
		if(zero)
			G.removeVertex(0);
		in.close();
		MainFrame frame = new MainFrame(G);
		frame.setVisible(true);
	}
}
