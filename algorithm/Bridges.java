package algorithm;

import graph.Graph;

import java.awt.Color;


/**
 * @author goovie
 * Finds bridges in undirected graphs, uses low function.
 * Complexity O(n^2).
 */

public class Bridges implements GraphAlgorithm
{
	public Bridges(Graph G)
	{
		this.G = G;
	}
	
	public void startAlgorithm()
	{
		if(G.isDirected()) return;
		n = G.capacity();
		dfs();
		for(int i = 0; i < n; i++)
		{
			if(low[i] == d[i] && p[i] != -1)
			{
				if(G.isEdge(i, p[i]))
					G.setEdgeColor(i, p[i], BRIDGE_COLOR);
			}
		}
	}
	
	private void dfs_visit(int v, int father)
	{
	    p[v] = father;
	    d[v] = time;
	    time++;
	    low[v] = d[v];
	    for(int i = 0; i < G.capacity(); i++)
	    {
	    	if(!G.isEdge(v, i)|| v == i) continue;
	        if(i != father)
	        if(d[i] == -1)
	        {
	            dfs_visit(i, v);
	            if(low[v] > low[i])
	                low[v] = low[i];
	        }
	        else
	        {
	            if(low[v] > d[i])
	                low[v] = d[i];
	        }
	    }
	}
	
	private void dfs()
	{
		//Initializing every structure
		p = new int[n+1];
		d = new int[n+1];
		low = new int[n+1];
		for(int i = 0; i <= n; i++) p[i] = d[i] = low[i] = -1;
		time = 1;
		
		for(int i = 0; i < n; i++)
		{
			if(d[i] == -1)
					dfs_visit(i, 0);
		}
	}
	
	public String getName()
	{
		return NAME;
	}
	
	private static Color BRIDGE_COLOR = Color.RED;
	private int n;
	private int time;
	private int[] p;
	private int[] d;
	private int[] low;
	private Graph G;
	public static final String NAME = "Bridges";
}
