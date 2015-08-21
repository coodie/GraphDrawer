package utility;

import graph.Graph2D;

import java.util.Scanner;

public class GraphInputAnalyser
{
	public GraphInputAnalyser(String str)
	{
		this.str = str;
		//analyse();
	}
	
	/*
	 * setting up any of parameters to 0 means that it's up to
	 * algorithm to recognise if graph is weighted/directed/has node numbered 0
	 * 
	 */
	public boolean analyse(int directed, int weighted, int zero)
	{
		Scanner scan = new Scanner(str);
		int n,m;
		//Checking if graph is weighted
		if(scan.hasNextInt())
			n = scan.nextInt();
		else 
			return false;
		if(scan.hasNextInt())
			m = scan.nextInt();
		else 
			return false;
		int tmp = 0;
		G = new Graph2D(n);
		int am = 0;
		if(weighted == 0)
		{
			while(scan.hasNextInt())
			{
				tmp = scan.nextInt();
				am++;
			}
			if(am/3 == m && am%3 == 0)
			{
				G.setWeighted(true);
			}
			else
			//couldn't analyse (wrong amount of edges)
			if(am/2 != m || am%2 != 0)
			{
				return false;
			}
		}
		else
		{
			if(weighted == -1)
			{
				G.setWeighted(false);
				while(scan.hasNextInt())
				{
					tmp = scan.nextInt();
					am++;
				}
				if(am/2 != m || am%2 != 0)
				{
					return false;
				}
			}
			else
			if(weighted == 1)
			{
				G.setWeighted(true);
				while(scan.hasNextInt())
				{
					tmp = scan.nextInt();
					am++;
				}
				if(am/3 != m || am%3 != 0)
				{
					return false;
				}
			}
		}
		
		//Checking if graph might be directed
		//The only time when we can be sure that graph is directed
		//is when there is edge from a to b and edge from b to a in input
		if(directed == 0)
		{
			scan = new Scanner(str);
			n = scan.nextInt();
			m = scan.nextInt();
			boolean[][] tab = new boolean[n+1][n+1];
			boolean dir = false;
			if(G.isWeighted())
			{
				while(scan.hasNextInt())
				{
					int a = scan.nextInt();
					int b = scan.nextInt();
					int c = scan.nextInt();
					if(a < 0 || b < 0 || a > n || b > n) return false;
					if(tab[b][a] == true) dir = true;
					tab[a][b] = true;
				}
			}
			else
			{
				while(scan.hasNextInt())
				{
					int a = scan.nextInt();
					int b = scan.nextInt();
					if(a < 0 || b < 0 || a > n || b > n) return false;
					if(tab[a][b] == true || tab[b][a] == true) dir = true;
					tab[a][b] = true;
					tab[b][a] = true;
				}
			}
			if(dir) G.setDirected(true);
		}
		else
		{
			if(directed == 1)
				G.setDirected(true);
			else
			if(directed == -1)
				G.setDirected(false);
		}
			
		
		if(zero == 0)
		{
			scan = new Scanner(str);
			n = scan.nextInt();
			m = scan.nextInt();
			boolean zer = true;
			if(G.isWeighted())
			{
				while(scan.hasNextInt())
				{
					int a = scan.nextInt();
					int b = scan.nextInt();
					int c = scan.nextInt();
					if(a == 0 || b == 0) zer = false;
				}
			}
			else
			{
				while(scan.hasNextInt())
				{
					int a = scan.nextInt();
					int b = scan.nextInt();
					if(a == 0 || b == 0) zer = false;
				}
			}
			if(zer) G.removeVertex(0);
		}
		else
		{
			if(zero == -1)
				G.removeVertex(0);
		}
			
		//Everything went OK so we can parse input
		scan = new Scanner(str);
		n = scan.nextInt();
		m = scan.nextInt();
		
		if(G.isWeighted())
		{
			while(scan.hasNextInt())
			{
				int a = scan.nextInt();
				int b = scan.nextInt();
				int c = scan.nextInt();
				G.addEdge(a, b, c);
			}
		}
		else
		{
			while(scan.hasNextInt())
			{
				int a = scan.nextInt();
				int b = scan.nextInt();
				G.addEdge(a, b);
			}
		}
		return true;
	}
	
	public boolean analyse()
	{
		return analyse(0,0,0);
	}
	
	public Graph2D getGraph()
	{
		return G;
	}
	private String str;
	private Graph2D G;
}
