package graph;

import java.awt.Color;

/**
 * @author goovie
 * Represents graph Vertex.
 * Can be colored.
 * Vertices are labeled (named) by integers.
 */
public class Vertex
{
	public Vertex(int name)
	{
		this.name = name;
	}
	
	public int getName()
	{
		return name;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setDefaultColor()
	{
		this.color = DEFAULT_VERTEX_COLOR;
	}
	public static final Color DEFAULT_VERTEX_COLOR = Color.GRAY;
	protected Color color = DEFAULT_VERTEX_COLOR;
	protected int name;
	
}
