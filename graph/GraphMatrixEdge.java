package graph;

import java.awt.Color;


/**
 * @author goovie
 * This is class which represents edge in graph matrix,
 * Used in graph class implementation.
 * Can be colored and weighted.
 */
public class GraphMatrixEdge
{
	public GraphMatrixEdge(boolean exist, Color color)
	{
		this.exist = exist;
		this.color = color;
		this.weight = 1;
	}
	
	public GraphMatrixEdge(boolean exist, int weight)
	{
		this.exist = exist;
		this.color = DEFAULT_EDGE_COLOR;
		this.weight = weight;
		this.weighted = true;
	}
	
	public GraphMatrixEdge(boolean exist)
	{
		this.exist = exist;
		this.color = DEFAULT_EDGE_COLOR;
		this.weight = 1;	

	}
	
	public GraphMatrixEdge()
	{
		exist = false;
		color = DEFAULT_EDGE_COLOR;
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
		this.color = DEFAULT_EDGE_COLOR;
	}
	
	public boolean isExist()
	{
		return exist;
	}
	
	public void setExist(boolean exist)
	{
		this.exist = exist;
	}
	
	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public boolean isWeighted()
	{
		return weighted;
	}

	public void setWeighted(boolean weighted)
	{
		this.weighted = weighted;
	}

	private Color color;
	
	//says if edge exists
	private boolean exist;
	public static final Color DEFAULT_EDGE_COLOR = Color.BLACK;
	
	private int weight;
	private boolean weighted;
	
}
