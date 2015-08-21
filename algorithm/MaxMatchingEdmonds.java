package algorithm;

import graph.*;
import java.awt.Color;

import java.util.*;

/**
 * @author goovie
 * Edmond's algorithm for finding maximum matching in any graph.
 * Taken from:
 * https://sites.google.com/site/indy256/algo/edmonds_matching
 * 
 * Complexity O(n^3)
 */
public class MaxMatchingEdmonds implements GraphAlgorithm
{
	public MaxMatchingEdmonds(Graph G)
	{
		this.G = G;
	}
	private static int lca(int[] match, int[] base, int[] p, int a, int b)
	{
		boolean[] used = new boolean[match.length];
		while (true)
		{
			a = base[a];
			used[a] = true;
			if (match[a] == -1)
				break;
			a = p[match[a]];
		}
		while (true)
		{
			b = base[b];
			if (used[b])
				return b;
			b = p[match[b]];
		}
	}

	private void markPath(int[] match, int[] base, boolean[] blossom,
			int[] p, int v, int b, int children)
	{
		for (; base[v] != b; v = p[match[v]])
		{
			blossom[base[v]] = blossom[base[match[v]]] = true;
			p[v] = children;
			children = match[v];
		}
	}

	private int findPath(List<Integer>[] graph, int[] match, int[] p,
			int root)
	{
		int n = graph.length;
		boolean[] used = new boolean[n];
		Arrays.fill(p, -1);
		int[] base = new int[n];
		for (int i = 0; i < n; ++i)
			base[i] = i;

		used[root] = true;
		int qh = 0;
		int qt = 0;
		int[] q = new int[n];
		q[qt++] = root;
		while (qh < qt)
		{
			int v = q[qh++];

			for (int to : graph[v])
			{
				if (base[v] == base[to] || match[v] == to)
					continue;
				if (to == root || match[to] != -1 && p[match[to]] != -1)
				{
					int curbase = lca(match, base, p, v, to);
					boolean[] blossom = new boolean[n];
					markPath(match, base, blossom, p, v, curbase, to);
					markPath(match, base, blossom, p, to, curbase, v);
					for (int i = 0; i < n; ++i)
						if (blossom[base[i]])
						{
							base[i] = curbase;
							if (!used[i])
							{
								used[i] = true;
								q[qt++] = i;
							}
						}
				} else if (p[to] == -1)
				{
					p[to] = v;
					if (match[to] == -1)
						return to;
					to = match[to];
					used[to] = true;
					q[qt++] = to;
				}
			}
		}
		return -1;
	}

	private void maxMatching(List<Integer>[] graph)
	{
		int n = graph.length;
		int[] match = new int[n];
		Arrays.fill(match, -1);
		int[] p = new int[n];
		for (int i = 0; i < n; ++i)
		{
			if (match[i] == -1)
			{
				int v = findPath(graph, match, p, i);
				while (v != -1)
				{
					int pv = p[v];
					int ppv = match[pv];
					match[v] = pv;
					match[pv] = v;
					v = ppv;
				}
			}
		}

		matching = match;
	}

	public void startAlgorithm()
	{
		int n = G.capacity();
		List<Integer>[] g= new List[n];
		for (int i = 0; i < n; i++)
			g[i] = new ArrayList<>();

		for (int i = 0; i < n; i++)
			for (int j = 0; j <n; j++)
			{
				if (i == j)
					continue;
				if (G.isEdge(i, j))
					g[i].add(j);
			}
		maxMatching(g);
		for (int v = 0; v < n; v++)
		{
			if (matching[v] != -1)
			{
				if (G.isEdge(v, matching[v]))
					G.setEdgeColor(v, matching[v], MATCHING_COLOR);
			}
		}
	}
	
	public String getName()
	{
		return NAME;
	}
	
	private Graph G;
	private int[] matching;
	private static Color MATCHING_COLOR = Color.RED;
	public static final String NAME = "Max Matching";
}