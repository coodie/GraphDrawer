package algorithm;

import java.awt.Color;

import graph.Graph;
/**
 * @author goovie
 * Bellman-Ford Algorithm.
 * Might loop for graphs with negative cycles, so be careful.
 * 
 * Complexity O(n^3)
 */
public class BellmanFord implements GraphAlgorithm
{
	public BellmanFord(int s, int t, Graph G)
	{
		this.s = s;
		this.t = t;
		this.G = G;
	}
	public void startAlgorithm()
	{
		int vert[] = G.getAvaiableVertices();
		d = new int[G.capacity()];
		prev = new int[G.capacity()];
		for(int i = 0; i < G.capacity(); i++) {d[i] = inf; prev[i] = -1;}
		d[s] = 0;
		for(int it = 0; it < vert.length; it++)
			for(int i = 0; i < vert.length; i++)
			{
				int u = vert[i];
				if(d[u] == inf) continue;
				for(int j = 0; j < vert.length; j++)
				{
					int v = vert[j];
					if(G.isEdge(u, v))
					{
						if(d[v] > d[u] + G.getEdgeWeight(u, v))
						{
							d[v] = d[u] + G.getEdgeWeight(u, v);
							d[v] = Math.max(d[v], -inf);
							prev[v] = u;
						}
					}
				}
			}
		int r = t;
		while(r != s && r != -1 && prev[r] != -1)
		{
			G.setEdgeColor(prev[r], r, EDGE_COLOR);
			r = prev[r];
		}
		if(r != s)
			G.recolor();
	}
	
	public String getName()
	{
		return NAME;
	}
	
	public static Color EDGE_COLOR = Color.RED;
	private int[] prev;
	private int[] d;
	private Graph G;
	private int t;
	private int s;
	public static final String NAME = "Bellman Ford";
	private static final int inf = 1000000000;
}
