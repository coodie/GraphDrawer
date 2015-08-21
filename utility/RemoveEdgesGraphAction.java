package utility;

import graph.Edge;
import graph.Graph;

public class RemoveEdgesGraphAction implements GraphAction
{
	public RemoveEdgesGraphAction(Graph G, Edge[] edges)
	{
		this.edges = edges;
		this.G = G;
	}
	
	@Override
	public void perform()
	{
		for(Edge e : edges)
			G.removeEdge(e.getA().getName(), e.getB().getName());
	}

	@Override
	public void performReversed()
	{
		for(Edge e : edges)
		{
			G.addEdge(e);
			System.out.println(e.getA().getName() + " " + e.getB().getName() + " " + e.getWeight());
		}
			
	}
	private Edge[] edges;
	private Graph G;
}
