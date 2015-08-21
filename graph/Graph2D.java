package graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author goovie
 * Representation of graph on 2D plane.
 * Contains extra info about coordinates of each vertex.
 */
public class Graph2D extends Graph
{
	public Graph2D(int n)
	{
		super(n);
		for(int i = 0; i <= n; i++)
		{
			V.put(i,new Vertex2D(i));
		}
	}

	public void addVertex(int v, int x, int y)
	{
		addVertex(v);
		V.remove(v);
		V.put(v, new Vertex2D(v, x, y));
	}
	
	@Override
	public void addVertex(int v)
	{
		if(G.size() <= v)
		{
			for(int i = 0; i < G.size(); i++)
			{
				while(G.get(i).size() <= v)
					G.get(i).add(new GraphMatrixEdge(false));
			}
			while(G.size() <= v)
			{
				ArrayList<GraphMatrixEdge> tmp = new ArrayList<GraphMatrixEdge>(v+1); 
				for(int j = 0; j <= v; j++)
					tmp.add(new GraphMatrixEdge());
				G.add(tmp);
			}
		}
		V.put(v, new Vertex2D(v));
	}
	
	//Removes all edges which are crossing with given line
	public Edge2D[] removeAllCrossingEdges(int x1, int y1, int x2, int y2)
	{
		class IntPair
		{
			public IntPair(int first, int second)
			{
				this.first = first;
				this.second = second;
			}
			public int first;
			public int second;
		}
		
		Stack<IntPair> Q = new Stack<IntPair>();
		Line2D line1 = new Line2D.Float(x1, y1, x2, y2);
		int edgeslimit = (isDirected() ? G.size() : G.size()/2);
		for(int i = 0; i < G.size(); i++)
			for(int j = 0; j < edgeslimit; j++)
			{
				if(isDirected() && G.get(i).get(j).isExist() && G.get(j).get(i).isExist())
				{
					Edge2D tmp = new Edge2D(getVertex2D(i), getVertex2D(j), G.get(i).get(j), directed);
					double[] arr = tmp.getCurvePoints();
					for(int t = 0; t < 4; t+=2)
					{
						Line2D line2 = new Line2D.Double(arr[t], arr[t+1], arr[t+2], arr[t+3]);
						if(line1.intersectsLine(line2))
						{
							Q.add(new IntPair(i,j));
							break;
						}
					}
				}
				else
				if(G.get(i).get(j).isExist())
				{
					Edge2D tmp = new Edge2D(getVertex2D(i), getVertex2D(j), G.get(i).get(j), directed);
					int[] arr = tmp.getLinePoints();
					Line2D line2 = new Line2D.Float(arr[0], arr[1], arr[2], arr[3]);
					if(line1.intersectsLine(line2))
						Q.add(new IntPair(i,j));
				}
			}
		Edge2D[] edges = new Edge2D[Q.size()];
		int i = 0;
		while(!Q.empty())
		{
			IntPair e = Q.pop();
			edges[i++] = getEdge2D(e.first, e.second);
			this.removeEdge(e.first, e.second);
		}
		return edges;
	}
	
	public Edge2D getEdge2D(int a, int b)
	{
		return new Edge2D(getVertex2D(a),getVertex2D(b),G.get(a).get(b), directed);
	}
	
	//Moves vertex numbered i to place (x1,y1)
	public void moveVertex(int i, int x1, int y1)
	{
		getVertex2D(i).x = x1;
		getVertex2D(i).y = y1;
	}
	
	//Moves vertex numbered i by vector (x1,y1)
	public void moveVertexByVector(int i, int x1, int y1)
	{
		getVertex2D(i).x += x1;
		getVertex2D(i).y += y1;
	}
	
	//Gets nearest vertex to (x,y)
	public Vertex2D getNearestVertex(int x, int y)
	{
		int r = Vertex2D.RADIUS;
		for(int i = 0; i < G.size(); i++)
		{
			Vertex2D v = (Vertex2D)V.get(i);
			if(v == null) continue;
			int nx = ((Vertex2D)v).x - x;
			int ny = ((Vertex2D)v).y - y;
			if(nx*nx + ny*ny <= r*r)
				return (Vertex2D)v;
		}
		return null;
	}
	
	//Adds new vertex, by finding lowest number of vertex which isn't in graph yet
	//then returns the number of vertex.
	public int addNewVertex()
	{
		int v = G.size();
		for(int i = 0; i < G.size(); i++)
		{
			if(!V.containsKey(i))
			{
				v = i;
				break;
			}
		}
		this.addVertex(v);
		return v;
	}
	
	//Draws graph on Graphics2D object.
	public void draw(Graphics2D g2d)
	{
		g2d.setTransform(new AffineTransform());
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(Edge2D.LINE));
		for(int i = 0; i < this.capacity(); i++)
				for(int j = (directed ? 0 : i); j < this.capacity(); j++)
					if(G.get(i).get(j).isExist())
					{
						Edge2D tmp = new Edge2D(getVertex2D(i), getVertex2D(j), G.get(i).get(j), directed);
						tmp.draw(g2d, (G.get(j).get(i).isExist() && isDirected()) );
					}
		for(int i = 0; i < G.size(); i++)
		{
			if(V.containsKey(i))
				((Vertex2D)V.get(i)).draw(g2d);
		}
	}
	
	public Vertex2D getVertex2D(int i)
	{
		return (Vertex2D) V.get(i);
	}
}