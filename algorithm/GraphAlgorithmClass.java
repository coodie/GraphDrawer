package algorithm;

import graph.Graph;


/**
 * @author goovie
 * This class is able to perform few chosen algorithms,
 * usualy changes the colors of edges/vertices of a graph.
 */
public class GraphAlgorithmClass
{
	public GraphAlgorithmClass(Graph G)
	{
		this.G = G;
	}

	public void bridges()
	{
		Bridges alg = new Bridges(G);
		alg.startAlgorithm();
	}
	
	public void matching()
	{
		MaxMatchingEdmonds alg = new MaxMatchingEdmonds(G);
		alg.startAlgorithm();
	}
	
	public void BellmanFord(int s, int t)
	{
		BellmanFord alg = new BellmanFord(s,t,G);
		alg.startAlgorithm();
	}
	
	public void Tarjan()
	{
		Tarjan alg = new Tarjan(G);
		alg.startAlgorithm();
	}
	
	public void Kruskal()
	{
		Kruskal alg = new Kruskal(G);
		alg.startAlgorithm();
	}
	protected Graph G;
}