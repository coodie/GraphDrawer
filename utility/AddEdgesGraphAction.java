package utility;

import graph.*;

public class AddEdgesGraphAction implements GraphAction
{

	public AddEdgesGraphAction(Graph G, Edge[] edges)
	{
		this.edges = edges;
		this.G = G;
	}
	@Override
	public void perform()
	{
		// TODO Auto-generated method stub
		for(Edge e : edges)
			G.addEdge(e);
			
	}

	@Override
	public void performReversed()
	{
		// TODO Auto-generated method stub
		for(Edge e : edges)
			G.removeEdge(e.getA().getName(), e.getB().getName());
		
	}
	private Edge[] edges;
	private Graph G;
}
