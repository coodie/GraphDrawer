package graph;

import java.awt.Color;

/**
 * @author goovie
 * Represents graph's edge.
 * Can be weighted, directed and colored.
 */
public class Edge
{
	public Edge(Vertex a, Vertex b)
	{
		this.a = a;
		this.b = b;
		this.directed = false;
		color = DEFAULT_EDGE_COLOR;
	}
	
	public Edge(Vertex a, Vertex b, boolean directed)
	{
		this.a = a;
		this.b = b;
		color = DEFAULT_EDGE_COLOR;
		this.directed = directed;
	}
	
	public Edge(Vertex a, Vertex b, GraphMatrixEdge e, boolean directed)
	{
		this.a = a;
		this.b = b;
		this.color = e.getColor();
		this.directed = directed;
		this.weight = e.getWeight();
		this.weighted = e.isWeighted();
	}

	public Vertex getA()
	{
		return a;
	}
	
	public Vertex getB()
	{
		return b;
	}

	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}
	
	public boolean isDirected()
	{
		return directed;
	}

	public void setDirected(boolean directed)
	{
		this.directed = directed;
	}
	
	public static final Color DEFAULT_EDGE_COLOR = Color.BLACK;

	protected boolean weighted;
	protected int weight;
	protected Color color;
	

	protected boolean directed;
	private Vertex a, b;
}
