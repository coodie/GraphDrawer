package utility;

import graph.*;

public class RemoveVertexGraphAction implements GraphAction
{

	public RemoveVertexGraphAction(Graph2D G, Vertex2D v, Edge[] edges)
	{
		this.v = v;
		this.G = G;
		this.edges = edges;
	}

	@Override
	public void perform()
	{
		// TODO Auto-generated method stub
		G.removeVertex(v.getName());
	}

	@Override
	public void performReversed()
	{
		// TODO Auto-generated method stub
		G.addVertex(v.getName(), v.getX(), v.getY());
		G.setVertexColor(v.getName(), v.getColor());
		for(Edge e : edges)
		{
			G.addEdge(e);
		}
	}
	private Edge[] edges;
	private Vertex2D v;
	private Graph2D G;
}
