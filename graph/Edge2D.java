package graph;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author goovie
 * Represents graph edge on plane.
 * This class contains two fields of Vertex2D instead of
 * normal vertices.
 * There is also extra method called draw for drawing 
 * edge on plane.
 */
public class Edge2D extends Edge
{
	public Edge2D(Vertex2D a, Vertex2D b)
	{
		super(a, b);
		this.a = a;
		this.b = b;
		this.directed = false;
		color = DEFAULT_EDGE_COLOR;
	}
	
	public Edge2D(Vertex2D a, Vertex2D b, boolean directed)
	{
		super(a, b, directed);
		this.a = a;
		this.b = b;
		color = DEFAULT_EDGE_COLOR;
		this.directed = directed;
	}
	
	public Edge2D(Vertex2D a, Vertex2D b, GraphMatrixEdge e, boolean directed)
	{
		super(a, b, e, directed);
		this.a = a;
		this.b = b;
		this.color = e.getColor();
		this.directed = directed;
		this.weight = e.getWeight();
		this.weighted = e.isWeighted();
	}
	
	@Override
	public Vertex2D getA()
	{
		return a;
	}
	
	@Override
	public Vertex2D getB()
	{
		return b;
	}
	
	public double[] getCurvePoints()
	{
		int r = Vertex2D.RADIUS;
		int ar = 5;
		
		double a1 = b.x-a.x, b1 = b.y-a.y;
		double d1 = Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
		double sina1 = b1/d1;
		double cosa1 = a1/d1;
		double[] arr = new double[8];
		double x1,y1,x2,y2;
		
		
		//We're computing middle point of bezier curve
		double e = 2*r;
		double xp = (a.x+b.x)/2;
		double yp = (a.y+b.y)/2;
		double x3 = xp-sina1*e;
		double y3 = yp+cosa1*e;
		//g2d.drawLine((a.x+b.x)/2, (a.y+b.y)/2, (int)x3, (int)y3);
		
		//two last indexes contain middle control point of bezier curve
		//this is because this method is used in finding crossings between lines and edges
		//and drawing edge aswell
		arr[6] = x3; arr[7] = y3;
		//We're computing last point of bezier curve
		double a2 = b.x-x3, b2 = b.y-y3;
		double d2 = Math.sqrt(Math.pow(x3-b.x, 2) + Math.pow(y3-b.y, 2));
		double sina2 = b2/d2;
		double cosa2 = a2/d2;
		double tx2 = cosa2*(r+ar);
		double ty2 = sina2*(r+ar);
		x2 = (int)(b.x - tx2);
		y2 = (int)(b.y - ty2);
		
		//We're computing starting point of bezier curve
		double a3 = x3-a.x, b3 = y3-a.y;
		double d3 = Math.sqrt(Math.pow(x3-a.x, 2) + Math.pow(y3-a.y, 2));
		double sina3 = b3/d3;
		double cosa3 = a3/d3;
		double tx3 = cosa3*r;
		double ty3 = sina3*r;
		x1 = (int)(a.x + tx3);
		y1 = (int)(a.y + ty3);
		
		//indexes at 2 and 3 contain point where the curve is in the middle
		arr[0] = x1; arr[1] = y1; arr[2] = xp - sina1*r; arr[3] = yp + cosa1*r; arr[4] = x2; arr[5] = y2;
		return arr;
	}
	
	public int[] getLinePoints()
	{
		int r = Vertex2D.RADIUS;
		int x1,y1,x2,y2;
		double a1 = b.x-a.x, b1 = b.y-a.y;
		double d = Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
		double sina = b1/d;
		double cosa = a1/d;
		double tx = cosa*r;
		double ty = sina*r;
		x2 = (int)(b.x-tx);
		y2 = (int)(b.y-ty);
		x1 = (int)(a.x+tx);
		y1 = (int)(a.y+ty);
		int[] arr = new int[4];
		arr[0]=x1;arr[1]=y1;arr[2]=x2;arr[3]=y2;
		return arr;
	}
	
	public void draw(Graphics2D g2d, boolean curve)
	{
		//We're drawing an arrow
		if(directed)
		{
			if(curve)
			{
				int ar = 5;
				double[] arr = getCurvePoints();
				double x1 = arr[0],y1 = arr[1], x3 = arr[6], y3=arr[7], x2=arr[4], y2 = arr[5];
				Path2D path = new Path2D.Double();
				path.moveTo(x1,y1);
				path.curveTo(x1,y1,x3,y3,x2,y2);
				g2d.setColor(color);
				g2d.draw(path);
				Polygon arrowHead = new Polygon();  
				arrowHead.addPoint( (int)x2, (int)y2+ar);
				arrowHead.addPoint( (int)x2-ar, (int)y2-ar);
				arrowHead.addPoint( (int)x2+ar, (int)y2-ar);
				
				AffineTransform orig = g2d.getTransform();
				AffineTransform tran = new AffineTransform();
				double theta = (y3 > y2) ? Math.PI: 0;
				if(x2 != x3)
				{
					theta =  Math.atan( (double)(y2-y3)/(double)(x2-x3) ) + Math.PI/2;
					if(x2 - x3 > 0)
						theta += Math.PI;
				}
				tran.rotate(theta, x2, y2);
				g2d.setTransform(tran);
				g2d.fillPolygon(arrowHead);
				g2d.setTransform(orig);
				
			}
			else
			{
				int r = Vertex2D.RADIUS;
				int ar = 5;
				double x1,y1,x2,y2;
				double a1 = b.x-a.x, b1 = b.y-a.y;
				double d = Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
				double sina = b1/d;
				double cosa = a1/d;
				double tx2 = cosa*(r+ar);
				double ty2 = sina*(r+ar);
				x2 = (int)(b.x - tx2);
				y2 = (int)(b.y - ty2);
				double tx1 = cosa*r;
				double ty1 = sina*r;
				x1 = (int)(a.x+tx1);
				y1 = (int)(a.y+ty1);
				
				
				Polygon arrowHead = new Polygon();  
				arrowHead.addPoint( (int)x2, (int)y2+ar);
				arrowHead.addPoint( (int)x2-ar, (int)y2-ar);
				arrowHead.addPoint( (int)x2+ar, (int)y2-ar);
				g2d.setColor(color);
				g2d.draw(new Line2D.Double(x1, y1, x2, y2));
				AffineTransform orig = g2d.getTransform();
				AffineTransform tran = new AffineTransform();
				double theta = (y1 > y2) ? Math.PI: 0;
				if(x2 != x1)
				{
					theta =  Math.atan( (double)(y2-y1)/(double)(x2-x1) ) + Math.PI/2;
					if(x2 - x1 > 0)
						theta += Math.PI;
				}
				tran.rotate(theta, x2, y2);
				g2d.setTransform(tran);
				g2d.fillPolygon(arrowHead);
				g2d.setTransform(orig);
			}
			
		}
		else
		{
			int[] arr = getLinePoints();
			int x1=arr[0],y1=arr[1],x2=arr[2],y2=arr[3];
			g2d.setColor(color);
			g2d.setStroke(new BasicStroke(LINE));
			g2d.draw(new Line2D.Float(x1, y1, x2, y2));
		}
		if(weighted)
		{
			g2d.setColor(Color.BLACK);
			int midx = (a.x+b.x)/2;
			int midy = (a.y+b.y)/2;
			int x1, y1, x2, y2;
			if(a.x < b.x)
			{
				x1 = a.x;
				y1 = a.y;
				x2 = b.x;
				y2 = b.y;
			}
			else
			{
				x1 = b.x;
				y1 = b.y;
				x2 = a.x;
				y2 = a.y;
			}
			g2d.setFont(new Font("Arial Black", Font.BOLD, 16));
			AffineTransform orig = g2d.getTransform();
			AffineTransform tran = new AffineTransform();
			double theta = Math.PI/2;
			if(x2 != x1)
				theta =  Math.atan( (double)(y2-y1)/(double)(x2-x1) );
			
			tran.rotate(theta, midx, midy);
			g2d.setTransform(tran);
			String label = Integer.toString(weight);
			double strw = g2d.getFontMetrics().getStringBounds(label, g2d).getWidth();
			double strh = g2d.getFontMetrics().getStringBounds(label, g2d).getHeight();
			double start = strh/2 - strw;
			double ypoint = midy - DISTANCE_FROM_EDGE;
			if(curve)
			{
				if(b.x < a.x)
					ypoint -= Vertex2D.RADIUS;
				else
					ypoint += Vertex2D.RADIUS;
			}
				
			g2d.drawString(label, (int) (start + midx), (int)ypoint);
			g2d.setTransform(orig);
		}
	}
	public static final int DISTANCE_FROM_EDGE = 6;
	public static final int SHIFT_DISTANCE = 10;
	public static final int LINE = 3;
	private Vertex2D a, b;
}
