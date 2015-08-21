package algorithm;

import java.awt.Color;
import java.util.Random;
import java.util.Stack;

import graph.Graph;

/**
 * @author goovie
 * Tarjan's Algorithm for finding strongly connected components of graph.
 * Vertices which are in the same component are colored the same color.
 * Colors are chosen randomly.
 * 
 * Complexity O(n^2)
 */
public class Tarjan implements GraphAlgorithm
{
	public Tarjan(Graph G)
	{
		this.G = G;
	}
	public void startAlgorithm()
	{
		V = G.getAvaiableVertices();
		index = new int[G.capacity()];
		lowlink = new int[G.capacity()];
		SSC = new int[G.capacity()];
		inS = new boolean[G.capacity()];
		for(int i = 0; i < index.length; i++)
			{index[i] = lowlink[i] = SSC[i] = -1;}
		id = 0;
		cur = 0;
		S = new Stack<Integer>();
		for(int i = 0; i < V.length; i++)
		{
			int v = V[i];
			if(index[v] == -1)
			{
				strongconnect(v);
			}
		}
		Color[] cols = new Color[cur+2];
		Random rand = new Random();
		for(int i = 0; i < cols.length; i++)
		{
			cols[i] = new Color(rand.nextInt(155)+100, rand.nextInt(155)+100, rand.nextInt(155)+100);
		}
		for(int i = 0; i < V.length; i++)
		{
			int v = V[i];
			if(SSC[v] != -1)
				G.setVertexColor(v, cols[SSC[v]]);
		}
	}
	
	private void strongconnect(int v)
	{
		index[v] = id;
		lowlink[v] = id;
		id++;
		S.push(v);
		inS[v] = true;
		for(int i = 0; i < V.length; i++)
		{
			int w = V[i];
			if(G.isEdge(v, w))
			{
				if(index[w] == -1)
				{
					strongconnect(w);
					lowlink[v] = Math.min(lowlink[v], lowlink[w]);
				}
				else if(inS[w])
				{
					lowlink[v] = Math.min(lowlink[v], lowlink[w]);
				}
			}
		}
		if(lowlink[v] == index[v])
		{
			cur++;
			int w;
			do
			{
				w = S.pop();
				inS[w] = false;
				SSC[w] = cur;
				
			}while(w != v);
		}
	}
	
	public String getName()
	{
		return NAME;
	}
	
	private int cur;
	private Stack<Integer> S;
	private boolean[] inS;
	private int id = 0;
	private int[] lowlink;
	private int[] V;
	private int[] index;
	private int[] SSC;
	private Graph G;
	public static final String NAME = "Strong Components";
}
