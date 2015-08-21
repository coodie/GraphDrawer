package algorithm;

import java.awt.Color;
import java.util.PriorityQueue;

import graph.Graph;

public class Kruskal implements GraphAlgorithm
{
	public Kruskal(Graph G)
	{
		this.G = G;
	}
	public void startAlgorithm()
	{
		if(G.isDirected()) return;
		PriorityQueue<Edge> Q = new PriorityQueue<Edge>();
		int[] V = G.getAvaiableVertices();
		for(int i = 0; i < V.length; i++)
		{
			int v = V[i];
			for(int j = 0; j < V.length; j++)
			{
				if(i == j) continue;
				int w = V[j];
				if(G.isEdge(v, w))
					Q.add(new Edge(G.getEdgeWeight(v, w),v,w));
			}
		}
		UnionFind uf = new UnionFind(G.capacity()+1);
		while(!Q.isEmpty())
		{
			Edge t = Q.poll();
			int a = t.a;
			int b = t.b;
			if(uf.Union(a, b))
				G.setEdgeColor(a, b, EDGE_COLOR);
		}
	}
	public String getName()
	{
		return NAME;
	}
	private Graph G;
	public static Color EDGE_COLOR = Color.RED;
	public static final String NAME = "Minimal Spanning Tree";
}

class Edge implements Comparable
{
	public Edge(int w, int a, int b)
	{
		this.w = w;
		this.a = a;
		this.b = b;
	}
	
	@Override
	public int compareTo(Object o)
	{
		Edge val = (Edge)o;
		return this.w - val.w;
	}
	public int w, a, b;
}

class UnionFind
{
    
    public UnionFind(int n)
    {
        rank = new int[n];
        parent = new int[n];
        for(int i = 0; i < n; i++)
        {
            rank[i] = 0;
            parent[i] = i;
        }
    }
    public int Find(int v)
    {
        if(parent[v] == v)
            return v;
        parent[v] = Find(parent[v]);
        return parent[v];
    }
    public boolean Union(int x, int y)
    {
        int xRoot = Find(x);
        int yRoot = Find(y);
        if(xRoot == yRoot) return false;
        if(rank[xRoot] >= rank[yRoot])
        {
        	parent[yRoot] = xRoot;
        	rank[xRoot] += rank[yRoot];
        }
        else
        {
        	parent[xRoot] = yRoot;
        	rank[yRoot] += rank[xRoot];
        }
            
        return true;
    }
    private int[] rank;
    private int[] parent;
}
