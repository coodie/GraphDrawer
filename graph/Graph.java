package graph;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author goovie
 * This class represents graph.
 * I allowed vertices to be numbered as ints, and used matrix to represent graph.
 */
public class Graph
{
	//Creates graph with initial capacity and n vertices numbered from 0 to n
	public Graph(int n)
	{
		G = new ArrayList<ArrayList<GraphMatrixEdge>>(n+1);
		V = new HashMap<Integer, Vertex>(100);
		for(int i = 0; i <= n; i++)
		{
			ArrayList<GraphMatrixEdge> tmp = new ArrayList<GraphMatrixEdge>(n+1); 
			for(int j = 0; j <= n; j++)
				tmp.add(new GraphMatrixEdge());
			G.add(tmp);
			V.put(i, new Vertex(i));
			
		}
	}
	
	//Adds new vertex with number v to the graph
	//If the graph is not big enough to contain the vertex, then it's capacity is increased
	//so it can actually contain given vertex.
	public void addVertex(int v)
	{
		if(V.containsKey(v)) return;
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
			}
		}
		V.put(v, new Vertex(v));
	}
	
	public Edge[] removeVertex(int v)
	{
		ArrayList<Edge> array = new ArrayList<Edge>();
		if(directed)
			for(int i = 0; i < G.size(); i++)
			{
				if(G.get(v).get(i).isExist())
				{
					G.get(v).get(i).setExist(false);
					array.add(getEdge(v, i));
				}
				if(G.get(i).get(v).isExist())
				{
					G.get(i).get(v).setExist(false);
					array.add(getEdge(i, v));
				}
			}
		else
			for(int i = 0; i < G.size(); i++)
			{
				if(G.get(v).get(i).isExist() || G.get(i).get(v).isExist())
				{
					G.get(v).get(i).setExist(false);
					G.get(i).get(v).setExist(false);
					array.add(getEdge(v, i));
				}
			}
		V.remove(v);
		return Arrays.copyOf(array.toArray(), array.size(), Edge[].class);
	}
	
	public void setVertexColor(int v, Color color)
	{
		if(V.containsKey(v))
			V.get(v).setColor(color);
	}
	
	//Adds edge, but with weight.
	public void addEdge(int a, int b, int c)
	{
		if(!isWeighted() || a == b) return;
		if(a >= G.size() || b >= G.size())
			return;
		if(directed)
		{
			G.get(a).set(b, new GraphMatrixEdge(true,c));
		}
		else
		{
			G.get(a).set(b, new GraphMatrixEdge(true,c));
			G.get(b).set(a, new GraphMatrixEdge(true,c));
		}
	}
	
	public void addEdge(int a, int b)
	{
		if(a >= G.size() || b >= G.size() || a == b)
			return;
		if(directed)
		{
			G.get(a).set(b, new GraphMatrixEdge(true));
			if(isWeighted())
			{
				G.get(a).get(b).setWeighted(true);
			}
		}
		else
		{
			G.get(a).set(b, new GraphMatrixEdge(true));
			G.get(b).set(a, new GraphMatrixEdge(true));
			if(isWeighted())
			{
				G.get(a).get(b).setWeighted(true);
				G.get(b).get(a).setWeighted(true);
			}
		}
	}
	
	public void removeEdge(int a, int b)
	{
		if(directed)
		{
			G.get(a).set(b, new GraphMatrixEdge(false));
		}
		else
		{
			G.get(a).set(b, new GraphMatrixEdge(false));
			G.get(b).set(a, new GraphMatrixEdge(false));
		}
	}

	public void setEdgeColor(int a, int b, Color color)
	{
		if(directed)
		{
			G.get(a).get(b).setColor(color);
		}
		else
		{
			G.get(a).get(b).setColor(color);
			G.get(b).get(a).setColor(color);
		}
	}
	
	//Sets colors of all vertices and edges to default.
	public void recolor()
	{
		for(int i = 0; i < G.size(); i++)
			for(int j = 0; j < G.get(i).size(); j++)
				if(G.get(i).get(j).isExist()) G.get(i).get(j).setDefaultColor(); 
		for(int i = 0; i < G.size(); i++)
			if(V.containsKey(i))
				V.get(i).setDefaultColor();
	}
	
	//Returns all neighbors of vertice V as an array
	public int[] getAllNeigbours(int v)
	{
		int cnt = 0;
		for(int i = 0; i < capacity(); i++)
			if(isVertex(i) && isEdge(v, i))
				cnt++;
		int[] tmp = new int[cnt];
		cnt = 0;
		for(int i = 0; i < capacity(); i++)
			if(isVertex(i) && isEdge(v, i))
				tmp[cnt++] = i;
		return tmp;
	}
	
	public Edge getEdge(int a, int b)
	{
		if(!V.containsKey(a) || ! V.containsKey(b)) return null;
		return new Edge(V.get(a), V.get(b), G.get(a).get(b), directed);
	}
	
	public void setEdgeWeight(int a, int b, int c)
	{
		if(isEdge(a, b))
			G.get(a).get(b).setWeight(c);
		else
			return;
		if(!directed)
			G.get(b).get(a).setWeight(c);
	}
	
	public int getEdgeWeight(int a, int b)
	{
		return G.get(a).get(b).getWeight();
	}
	
	public Color getEdgeColor(int a, int b)
	{
		return G.get(a).get(b).getColor();
	}
	
	public boolean isEdge(int a, int b)
	{
		return G.get(a).get(b).isExist();
	}
	
	public boolean isVertex(int v)
	{
		return V.containsKey(v);
	}
	
	
	//zwraca maksymalna ilosc wierzcholkow jakie moga byc w grafie
	public int capacity()
	{
		return G.size();
	}
	
	//zwraca tablice dostepnych wierzcholkow (tzn takich ktore sa w grafie)
	public int[] getAvaiableVertices()
	{
		int cnt = 0;
		for(int i = 0; i < G.size(); i++)
			if(isVertex(i))
				cnt++;
		int[] tab = new int[cnt];
		cnt = 0;
		for(int i = 0; i < G.size(); i++)
			if(isVertex(i))
				tab[cnt++] = i;
		return tab;
	}
	
	public Edge[] removeUnMarkedEdges()
	{
		ArrayList<Edge> array = new ArrayList<Edge>();
		int n = G.size();
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
			{
				GraphMatrixEdge tmp = G.get(i).get(j);
				if(tmp.isExist() && tmp.getColor() == GraphMatrixEdge.DEFAULT_EDGE_COLOR)
				{
					removeEdge(i,j);
					array.add(getEdge(i, j));
				}
			}
		return Arrays.copyOf(array.toArray(), array.size(), Edge[].class);
	}
	
	public Edge[] removeMarkedEdges()
	{
		ArrayList<Edge> array = new ArrayList<Edge>();
		int n = G.size();
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
			{
				GraphMatrixEdge tmp = G.get(i).get(j);
				if(tmp.isExist() && tmp.getColor() != GraphMatrixEdge.DEFAULT_EDGE_COLOR)
				{
					removeEdge(i,j);
					array.add(getEdge(i, j));
				}
			}
		return Arrays.copyOf(array.toArray(), array.size(), Edge[].class);
	}
	public boolean isWeighted()
	{
		return weighted;
	}

	public void setWeighted(boolean weighted)
	{
		this.weighted = weighted;
		
		for(int i = 0; i < G.size(); i++)
				for(int j = 0; j < G.size(); j++)
					G.get(i).get(j).setWeighted(weighted);
	}
	public String toString()
	{
		StringBuilder builder = new StringBuilder("");
		int n = capacity(), max = 0, m = 0;
		for(int i = 0; i < n; i++)
			if(isVertex(i)) max = i;
		int[] V = getAvaiableVertices();
		if(isWeighted())
		{
			for(int i = 0; i < V.length; i++)
			{
				int v = V[i];
				for(int j = (isDirected()) ? 0 : i+1; j < V.length; j++)
				{
					int w = V[j];
					if(isEdge(v, w))
					{
						builder.append(v + " " + w + " " + getEdgeWeight(v, w) + "\n");
						m++;
					}
				}
			}
		}
		else
		{
			for(int i = 0; i < V.length; i++)
			{
				int v = V[i];
				for(int j = (isDirected()) ? 0 : i+1; j < V.length; j++)
				{
					int w = V[j];
					if(isEdge(v, w))
					{
						builder.append(v + " " + w + "\n");
						m++;
					}
				}
			}
		}
		return max + " " + m + "\n" + builder.toString();
	}
	
	public void addEdge(Edge e)
	{
		
		int a = e.getA().getName();
		int b = e.getB().getName();
		G.get(a).get(b);
		if(isDirected())
		{
			G.get(a).get(b).setExist(true);
			G.get(a).get(b).setWeighted(weighted);
			G.get(a).get(b).setColor(e.getColor());
			G.get(a).get(b).setWeight(e.getWeight());
		}
		else
		{
			G.get(a).get(b).setExist(true);
			G.get(a).get(b).setWeighted(weighted);
			G.get(a).get(b).setColor(e.getColor());
			G.get(a).get(b).setWeight(e.getWeight());
			G.get(b).get(a).setExist(true);
			G.get(b).get(a).setWeighted(weighted);
			G.get(b).get(a).setColor(e.getColor());
			G.get(b).get(a).setWeight(e.getWeight());
		}
			
	}
	
	public boolean isDirected()
	{
		return directed;
	}

	public void setDirected(boolean directed)
	{
		this.directed = directed;
	}
	
	protected boolean weighted;
	protected boolean directed;
	
	protected HashMap<Integer,Vertex> V;
	protected ArrayList<ArrayList<GraphMatrixEdge> > G;
}