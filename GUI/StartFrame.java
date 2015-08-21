package GUI;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import utility.GraphInputAnalyser;

public class StartFrame extends JFrame
{
	public StartFrame()
	{
		input = new JTextArea();
		JScrollPane scroll = new JScrollPane(input);
		JPanel boxPanel = new JPanel();
		
		directedBox = new JCheckBox("directed");
		directedBox.setSelected(false);
		weightedBox = new JCheckBox("weighted");
		weightedBox.setSelected(false);
		zeroBox = new JCheckBox("zero");
		zeroBox.setSelected(false);
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		
		
		JButton displayButton = new JButton("Display");
		displayButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				display();
			}
		});
		JButton pasteButton = new JButton("Paste");
		pasteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				String result = "";
				try
				{
					result = (String) clipboard.getData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				input.setText(result);
			}
		});
		boxPanel.add(directedBox);
		boxPanel.add(weightedBox);
		boxPanel.add(zeroBox);
		boxPanel.add(pasteButton);
		boxPanel.add(displayButton);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 300);
		setResizable(false);
		add(scroll, BorderLayout.CENTER);
		add(boxPanel, BorderLayout.EAST);
	}
	
	public void display()
	{
		int dir = (directedBox.isSelected()?1:-1);
		int wei = (weightedBox.isSelected()?1:-1);
		int zer = (zeroBox.isSelected()?1:-1);
		GraphInputAnalyser analyser = new GraphInputAnalyser(input.getText());
		if(!analyser.analyse(dir,wei,zer))
		{
			JOptionPane.showMessageDialog(this, "WRONG INPUT!");
			return;
		}
		MainFrame frame = new MainFrame(analyser.getGraph());
		frame.setVisible(true);
		this.dispose();
	}
	
	final JTextArea input;
	final JCheckBox directedBox;
	final JCheckBox weightedBox;
	final JCheckBox zeroBox;
}
