package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * @author goovie
 * Vertex class for representing Vertex on a plane,
 * contains extra fields of coordinates,
 * and extra method draw for drawing on Graphics2D.
 */
public class Vertex2D extends Vertex
{
	public Vertex2D(int name)
	{
		super(name);
		this.name = name;
	}
	
	public Vertex2D(int name, int x, int y)
	{
		super(name);
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public Vertex2D clone()
	{
		return new Vertex2D(name, x, y);
	}
	@Override
	public String toString()
	{
		return "" + x + " " + y + " " + name;
	}
	
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(color);
		float newX = x - RADIUS;
		float newY = y - RADIUS;
		g2d.fill(new Ellipse2D.Float(newX, newY, RADIUS*2, RADIUS*2));
		g2d.setColor(Color.BLACK);
		g2d.draw(new Ellipse2D.Float(newX, newY, RADIUS*2, RADIUS*2));
		g2d.setFont(new Font("xxx", Font.BOLD, 14));
		String label = Integer.toString(name);
		double strw = g2d.getFontMetrics().getStringBounds(label, g2d).getWidth();
		double strh = g2d.getFontMetrics().getStringBounds(label, g2d).getHeight();
		g2d.drawString(label, (int) (x-strw/2.0), (int)(y+strh/2.3));
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	public static final int RADIUS = 15;
	public int x, y;
}
