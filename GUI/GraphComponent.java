package GUI;
import javax.swing.*;

import utility.GraphAction;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import graph.*;
import utility.*;
/**
 * @author goovie
 * This class contains and draws graph on any JFrame.
 * All we need to make this work is give this any Graph2D object.
 * The mouse is built-in and pretty simple:
 * 
 * Double left-click on vertex removes it.
 * Double left-click on empty space adds new vertex with lowest possible number.
 * 
 * Pressing and dragging vertex moves it.
 * Pressing and dragging empty space moves entire graph.
 * 
 * Right clicking on vertex and dragging it to other vertex adds new Edge,
 * 		whenever graph is weighted you are asked to give weight of an Edge.
 * Right clicking on empty space and dragging it to other space
 * 		creates line. When you release mouse all of edges which crossed
 * 		with that line are removed from graph.
 */
public class GraphComponent extends JComponent
{
	public GraphComponent(Graph2D G)
	{
		undoStack = new Stack<GraphAction>();
		redoStack = new Stack<GraphAction>();
		edgeColor = Edge.DEFAULT_EDGE_COLOR;
		vertexColor = Vertex.DEFAULT_VERTEX_COLOR;
		this.G = G;
		addMouseListener();
		addKeyListener();
		this.setFocusable(true);
	}
	
	private void addMouseListener()
	{
		final GraphComponent parentGraphComponent = this;
		MouseListener lis = new MouseListener()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						if(blocked) return;
						int px = e.getX();
						int py = e.getY();
						Vertex2D p = G.getNearestVertex(px, py);
						if(e.getClickCount() == 1)
						{
							if(p != null)
								if(e.getButton() == MouseEvent.BUTTON1)
								{
									G.setVertexColor(p.getName(), vertexColor);
									repaint();
								}
						}
						if(e.getClickCount() == 2)
						{
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								if(p == null)
								{
									int v = G.addNewVertex();
									G.moveVertex(v, px, py);
									G.setVertexColor(v, vertexColor);
									addAction(new AddVertexGraphAction(G, G.getVertex2D(v)));
									repaint();
									return;
								}
								
								if(p != null)
								{
									int v = p.getName();
									addAction(new RemoveVertexGraphAction(G, G.getVertex2D(v),G.removeVertex(v)));
									repaint();
									return;
								}
							}
						}
					}

					@Override
					public void mouseEntered(MouseEvent e)
					{
					}

					@Override
					public void mouseExited(MouseEvent e)
					{
					}

					//Pressing mouse sets lx,ly as the points where mouse was pressed for last time
					//this essential when moving graph or adding edge.
					@Override
					public void mousePressed(MouseEvent e)
					{
						if(blocked) return;
						int px = e.getX();
						int py = e.getY();
						lx = px;
						ly = py;
						p = G.getNearestVertex(px, py);
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							//We're setting starting set of vertices so we can possibly move the graph.
							if(p == null)
							{
								start = new HashMap<Integer, Vertex2D>(G.capacity());
								for(int i = 0; i < G.capacity(); i++)
									if(G.isVertex(i))
										start.put(i, G.getVertex2D(i).clone());
								lx = px;
								ly = py;
							}
						}
						
						if(e.getButton() == MouseEvent.BUTTON3)
						{
							//We found nearest vertex so we want to add new edge coming from it.
							if(p != null)
							{
								newEdge = new Edge2D(p, new Vertex2D(0), G.isDirected());
								newEdge.setColor(edgeColor);
							}
							//Or if we didn't find nearest vertex, then we are removing edges from graph.
							if(p == null)
							{
								cutx = px;
								cuty = py;
								cutting = true;
							}
						}
					}

					@Override
					public void mouseReleased(MouseEvent e)
					{
						if(blocked) return;
						//Removing edge.
						if(cutting)
						{
							addAction(new RemoveEdgesGraphAction(G, G.removeAllCrossingEdges(lx, ly, cutx, cuty)));
							repaint();
							cutting = false;
							return;
						}
						//Moving Edge
						if(newEdge == null) return;
						int px = e.getX();
						int py = e.getY();
						Vertex2D p = G.getNearestVertex(px, py);
						//Adding new edge.
						if(p != null)
						{
							
							int a = newEdge.getA().getName();
							int b = p.getName();
							G.addEdge(newEdge.getA().getName(), p.getName());
							G.setEdgeColor(a, b, edgeColor);
							boolean succes = true;
							if(G.isWeighted())
							{
								
								String weight = JOptionPane.showInputDialog(parentGraphComponent, "Give weight of an edge");
								if(weight != null)
								{
									int w = Integer.parseInt(weight);
									G.setEdgeWeight(a, b, w);
									
								}
								else
								{
									G.removeEdge(a, b);
									succes = false;
								}
							}
							if(succes)
							{
								Edge2D[] edges = new Edge2D[1];
								edges[0] = G.getEdge2D(a,b);
								addAction(new AddEdgesGraphAction(G, edges));
							}
							newEdge = null;
							repaint();
						}
						//If mouse was released and no nearest vertice was found, then we add no edge.
						else
						{
							newEdge = null;
							repaint();
						}
					}
				};
		MouseMotionListener molis = new MouseMotionListener()
				{

					@Override
					public void mouseDragged(MouseEvent e)
					{
						if(blocked) return;
						int px = e.getX();
						int py = e.getY();
						//Moving the line to cut out the edges.
						if(cutting)
						{
							cutx = px;
							cuty = py;
							repaint();
							return;
						}
						
						//Moving the newly added edge.
						if(newEdge != null)
						{
							newEdge.getB().x = px;
							newEdge.getB().y = py;
							Vertex2D p = G.getNearestVertex(px, py);
							if(p != null)
							{
								newEdge.getB().x = p.x;
								newEdge.getB().y = p.y;
							}
							repaint();
							return;
						}
						//Moving chosen vertex.
						if(p != null)
						{
							p.x = px;
							p.y = py;
							repaint();
						}
						//Moving the entire graph.
						if(p == null)
						{
							for(int i = 0; i < G.capacity(); i++)
							{
								if(!G.isVertex(i)) continue;
									G.moveVertex(i, start.get(i).x+px-lx, start.get(i).y+py-ly);
							}
							repaint();
							return;
						}
					}

					@Override
					public void mouseMoved(MouseEvent arg0)
					{
					}
					
				};
		this.addMouseListener(lis);
		this.addMouseMotionListener(molis);
	}
	
	private void addKeyListener()
	{
		KeyListener lis = new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.isShiftDown() && !e.isControlDown())
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_1:
							edgeColor = Edge.DEFAULT_EDGE_COLOR;
							break;
						case KeyEvent.VK_2:
							edgeColor = Color.RED;
							break;
						case KeyEvent.VK_3:
							edgeColor = Color.BLUE;
							break;
						case KeyEvent.VK_4:
							edgeColor = Color.GREEN;
							break;
						case KeyEvent.VK_R:
							Random gen = new Random();
							edgeColor = new Color(gen.nextInt(256), gen.nextInt(256),gen.nextInt(256));
							break;
					}
				if(!e.isShiftDown() && e.isControlDown())
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_1:
							vertexColor = Vertex.DEFAULT_VERTEX_COLOR;
							break;
						case KeyEvent.VK_2:
							vertexColor = Color.RED;
							break;
						case KeyEvent.VK_3:
							vertexColor = Color.BLUE;
							break;
						case KeyEvent.VK_4:
							vertexColor = Color.GREEN;
							break;
						case KeyEvent.VK_R:
							Random gen = new Random();
							vertexColor = new Color(gen.nextInt(256), gen.nextInt(256),gen.nextInt(256));
							break;
					}
			}
			@Override
			public void keyReleased(KeyEvent e)
			{
			}

			@Override
			public void keyTyped(KeyEvent arg0){}
			
		};
		this.addKeyListener(lis);
	}
	
	public void undo()
	{
		if(undoStack.isEmpty()) return;
		GraphAction action = undoStack.pop();
		if(action!= null)
		{
			action.performReversed();
			redoStack.push(action);
			repaint();
		}
	}
	
	//TODO has to be implemented
	public void redo()
	{
		if(redoStack.isEmpty()) return;
		GraphAction action = redoStack.pop();
		if(action!= null)
		{
			action.perform();
			undoStack.add(action);
			repaint();
		}
	}
	
	public Graph2D getGraph()
	{
		return G;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		G.draw(g2d);
		//Just checking if we are adding new edge.
		if(newEdge != null)
			newEdge.draw(g2d,false);
		//We might also be removing edges.
		if(cutting)
		{
			g2d.setColor(Color.RED);
			g2d.drawLine(lx, ly, cutx, cuty);
		}
	}
	
	public boolean getBlocked()
	{
		return blocked;
	}
	
	public void setBlocked(boolean b)
	{
		blocked = b;
	}
	
	public void addAction(GraphAction a)
	{
		undoStack.add(a);
		redoStack.clear();
	}
	
	public void addActionAndPerform(GraphAction a)
	{
		a.perform();
		undoStack.add(a);
		redoStack.clear();
	}
	
	public void clearActionStacks()
	{
		undoStack.clear();
		redoStack.clear();
	}
	
	private boolean blocked = false; //if this is true, mouse doesnt work
	private Vertex2D p; //Reference to lastly found vertex.
	private HashMap<Integer, Vertex2D> start; //Starting set of vertices used when moving the entire graph.
	private int lx, ly; //Coordinates of place where mouse was lastly pressed.
	private Edge2D newEdge; //Auxiliary edge being drawn when we're adding new edge to the graph.
	private boolean cutting; //Sets the mode of cutting edges.
	private int cutx, cuty; //Ending coordinates of removing line.
	private Color edgeColor;
	private Color vertexColor;
	private Stack<GraphAction> undoStack;
	private Stack<GraphAction> redoStack;
	private Graph2D G; //Reference to graph being drawn on component.
}


