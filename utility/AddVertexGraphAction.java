package utility;

import graph.Graph2D;
import graph.Vertex2D;

public class AddVertexGraphAction implements GraphAction
{

	public AddVertexGraphAction(Graph2D G, Vertex2D v)
	{
		this.v = v;
		this.G = G;
	}

	@Override
	public void perform()
	{
		G.addVertex(v.getName(), v.getX(), v.getY());
		G.setVertexColor(v.getName(), v.getColor());
	}

	@Override
	public void performReversed()
	{
		G.removeVertex(v.getName());
	}
	private Vertex2D v;
	private Graph2D G;
}
