package algorithm;

import graph.Graph2D;

/**
 * @author goovie
 * Performs graphs algorithms which needs extra info about
 * vertices and edges locations on plane. 
 */
public class GraphAlgorithm2DClass extends GraphAlgorithmClass
{
	
	public GraphAlgorithm2DClass(Graph2D G)
	{
		super(G);
	}
	
	public void ForceBasedDrawing(int xSize, int ySize, boolean randomize)
	{
		ForceBasedDrawing alg = new ForceBasedDrawing((Graph2D)G, xSize,ySize,randomize);
		alg.startAlgorithm();
	}
}
