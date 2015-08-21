package algorithm;

import graph.Graph2D;

import java.util.Random;

/**
 * @author goovie
 * Places graph on plane using ForceBased method
 * Taken and modified from site:
 * https://sites.google.com/site/indy256/algo/force_based_graph_drawing
 * 
 * Changes concerned few parameters to make algorithm be more eye-pleasing and faster,
 * also improved algorithm for disconnected and directed graphs.
 * 
 * Complexity O(n^3)
 */

public class ForceBasedDrawing implements GraphAlgorithm
{
	//xSize and ySize are sizes of plane the graph is being drawn onto.
	public ForceBasedDrawing(Graph2D G, int xSize, int ySize, boolean randomize)
	{
		this.G = G;
		this.xSize = xSize;
		this.ySize = ySize;
		this.randomize = randomize;
		this.steps = G.capacity()+100;
		
	}
	
	public void startAlgorithm()
	{
		makeConnected();
		int[] V = G.getAvaiableVertices();
		
		if(randomize)
		{
			Random rand = new Random();
			for(int i = 0; i < V.length; i++)
					G.moveVertex(V[i], rand.nextInt(xSize), rand.nextInt(ySize));
		}
		
		for(int i = 0; i < V.length; i++)
			G.moveVertexByVector(V[i], xSize*10, ySize*10);
		
		int n = G.capacity();
		
		double k1 = 1;
		double k2 = 300000;
		for (int step = 0; step < steps; step++)
		{
			double[] fx = new double[n];
			double[] fy = new double[n];
			for (int k = 0; k < V.length; k++)
			{
				int i = V[k];
				for (int l = 0; l < V.length; l++)
				{
					int j = V[l];
					if (i == j)
						continue;
					double dx = G.getVertex2D(j).x - G.getVertex2D(i).x;
					double dy = G.getVertex2D(j).y - G.getVertex2D(i).y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					dx /= dist;
					dy /= dist;

					double force = k2 / (dist * dist);

					if (G.isEdge(i, j) || G.isEdge(j, i) || conn[i][j])
						force += -k1 * (dist - 1);
					fx[j] += force * dx;
					fy[j] += force * dy;
				}
			}
			for (int k = 0; k < V.length; k++)
			{
				int i = V[k];
				G.getVertex2D(i).x += (fx[i] / 1000);
				G.getVertex2D(i).y += (fy[i] / 1000);
			}
		}
		
		middleSpace();
	}
	
	//moves middle of the graph to the middle of given space
	//middle of the graph is defined as a arithmetic mean
	//of x and y coordinates of all vertices of a graph
	//and so is defined middle of space (xSize/2, ySize/2)
	public void middleSpace()
	{
		//We're moving the middle of the graph to the middle of the plane
		int midX = 0;
		int midY = 0;
		
		int goalX = xSize/2;
		int goalY = ySize/2;
		
		int vecX = 0;
		int vecY = 0;
		
		int[] V = G.getAvaiableVertices();
		for(int v : V)
		{
			midX += G.getVertex2D(v).x;
			midY += G.getVertex2D(v).y;
		}
		
		if(V.length!=0)
		{
			midX /= (V.length);
			midY /= (V.length);
		}
		
		vecX = goalX-midX;
		vecY = goalY-midY;
		
		for(int v : V)
		{
			G.moveVertexByVector(v, vecX, vecY);
		}
	}
	
	private void makeConnected()
	{
		conn = new boolean[G.capacity()][G.capacity()];
		col = new int[G.capacity()];
		int cur = 1;
		for(int i = 0; i < G.capacity(); i++)
			if(col[i] == 0 && G.isVertex(i))
				dfs(i, cur++);
		int[] tab = G.getAvaiableVertices();
		for(int i = 1; i < tab.length; i++)
		{
			int cr = tab[i];
			int pr = tab[i-1];
			if(col[cr] != col[pr])
			{
				int c = col[pr];
				conn[pr][cr] = true;
				conn[cr][pr] = true;
				for(int j = 0; j < tab.length; j++)
				{
					if(col[tab[j]] == c)
						col[tab[j]] = col[cr];
				}
			}
		}
	}
	
	private void dfs(int v, int c)
	{
		col[v] = c;
		for(int i = 0; i < G.capacity(); i++)
			if(G.isEdge(v, i))
				if(col[i] == 0 && G.isVertex(i))
					dfs(i, c);
	}
	
	public int getSteps()
	{
		return steps;
	}
	
	public void setSteps(int steps)
	{
		this.steps = steps;
	}
	
	public boolean getRandomize()
	{
		return randomize;
	}
	
	public void setRandomize(boolean randomize)
	{
		this.randomize = randomize;
	}
	
	public String getName()
	{
		return NAME;
	}
	
	private int xSize;
	private int ySize;
	private Graph2D G;
	private boolean conn[][];
	private int col[];
	
	private boolean randomize; //if this is true, algorithm places vertices in random place at the very beginning
	private int steps; //amount of steps the algorithm performs
	
	public static final String NAME = "Force Based Drawing";
}


