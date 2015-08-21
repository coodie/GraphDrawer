package GUI;
import graph.Edge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import utility.RemoveEdgesGraphAction;
import utility.ScreenImage;

import algorithm.GraphAlgorithm2DClass;

/**
 * @author goovie
 * Alt + S = saving to image
 * Ctrl + S = saving to file
 * Shift + D = removing edges which aren't black
 * Shift + Ctrl + Z = redo
 * Ctrl + Z = undo
 * A - algorithm panel (clears do stack)
 * R - drawing graph in place
 * D - setting color of all vertices and edges to default (also clears do stack)
 */
public class GraphComponentKeyShortcuts implements KeyListener
{
	public GraphComponentKeyShortcuts(GraphComponent comp)
	{
		this.comp = comp;
	}
	@Override
	public void keyPressed(final KeyEvent e)
	{
		Runnable temp = new Runnable()
		{
			@Override
			public void run()
			{
				mainSemaphore = true;
				comp.setBlocked(true);
				if(e.isShiftDown() && e.isControlDown())
				{
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_Z:
							comp.redo();
							break;
					}
				}
				else
				//If alt is down:
				if(e.isAltDown())
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_S:
							try
							{
								BufferedImage image = ScreenImage.createImage(comp);
								//Path currentRelativePath = Paths.get("");
								//String s = currentRelativePath.toAbsolutePath().toString();
								String s = System.getProperty("user.dir");
								
								DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
								String date = dateFormat.format(new Date());
								String filepath = s + File.separator + date + ".bmp";
								ScreenImage.writeImage(image, filepath);
								System.out.println("Saving as image to: \n" + filepath);
								//JOptionPane.showMessageDialog(null, filepath);
							} catch (IOException e1)
							{
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "ERROR SAVING IMAGE");
								e1.printStackTrace();
							}
							break;
						
					}
				else
				//If control is down
				if(e.isControlDown())
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_S:
							
							try
							{
								//Path currentRelativePath = Paths.get("");
								//String s = currentRelativePath.toAbsolutePath().toString();
								String s = System.getProperty("user.dir");
								
								DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
								String date = dateFormat.format(new Date());
								String filepath = s + File.separator + date + ".txt";
								PrintWriter writer = new PrintWriter(filepath);
								writer.print(comp.getGraph().toString());
								writer.close();
								System.out.println("Saving as txt to: \n" + filepath);
								//JOptionPane.showMessageDialog(null, filepath);
							} catch (FileNotFoundException e1)
							{
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "ERROR SAVING FILE");
								e1.printStackTrace();
							}
							break;
						case KeyEvent.VK_Z:
							//comp.undo();
							break;
					}
				else
				if(e.isShiftDown())
				//If shift down
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_D:
							Edge[] tmp = comp.getGraph().removeUnMarkedEdges();
							comp.addAction(new RemoveEdgesGraphAction(comp.getGraph(), tmp));
							break;
					}
				else
				//If nothing is down
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_R:
							GraphAlgorithm2DClass alg = new GraphAlgorithm2DClass(comp.getGraph());
							alg.ForceBasedDrawing(comp.getWidth(), comp.getHeight(),false);
							break;
						case KeyEvent.VK_A:
							AlgorithmPanel.display(comp);
							comp.clearActionStacks();
							break;
						case KeyEvent.VK_D:
							comp.getGraph().recolor();
							comp.clearActionStacks();
							break;
					}
					
				comp.setBlocked(false);
				comp.repaint();
				mainSemaphore = false;
			}
		};
		if(!mainSemaphore)
		{
			Thread thread = new Thread(temp);
			thread.start();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}
	
	//private static final char[] allowedKeys = {'r', 'R', 'b', 'd', 'E', 'm', 'f', 't', 'k' };
	private GraphComponent comp;
	private static boolean mainSemaphore = false;
}
