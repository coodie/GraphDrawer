package graph;

import java.util.Random;

public class Graph2DRandomizer
{
	public Graph2DRandomizer(boolean directed, boolean weighted)
	{
		this.directed = directed;
		this.weighted = weighted;
	}
	
	public Graph2D random(int n, int m)
	{
		Graph2D G = new Graph2D(n);
		G.setDirected(directed);
		G.setWeighted(weighted);
		Random rand = new Random();
		int a,b;
		while(m>0)
		{
			a = rand.nextInt(n+1);
			b = rand.nextInt(n+1);
			if(!G.isEdge(a, b))
			{
				if(weighted)
				{
					int c = rand.nextInt(maxWeight);
					G.addEdge(a, b, c);
				}
				else
				{
					G.addEdge(a, b);
				}
			}
			m--;
		}
		return G;
	}
	
	public int getMaxWeight()
	{
		return maxWeight;
	}
	
	public void setMaxWeight(int maxWeight)
	{
		this.maxWeight = maxWeight;
	}
	
	private int maxWeight = 50;
	private boolean directed;
	private boolean weighted;
}
