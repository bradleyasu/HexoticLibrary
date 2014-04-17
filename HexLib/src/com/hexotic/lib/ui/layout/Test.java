package com.hexotic.lib.ui.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Test extends JFrame{

	public static void main(String[] args){
		new Test();
	}
//	
	public Test(){
		
		this.setTitle("TEST");;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel parent = new JPanel();
		
		AnimatedGridLayout  layout = new AnimatedGridLayout(true);
		parent.setLayout(layout);
		
		this.add(new JScrollPane(parent));
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(1000, 700));
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		this.setLocation(x, y);
		this.setVisible(true);
		
		Color color = Color.BLUE;
		Random rand = new Random();
		List<ComparePanel> panels = new ArrayList<ComparePanel>();
		for(int i = 0; i < 120; i++){
			ComparePanel panel = new ComparePanel(i);
			panel.setBackground(color);
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.setPreferredSize(new Dimension(100,rand.nextInt(50)+100));
			panel.add(new JButton("Hello "+i));
			parent.add(panel);
			panels.add(panel);
			color = color.brighter().brighter();

		}
		
	}
	
}

class ComparePanel extends JPanel implements Comparable<ComparePanel>{
	private int id; 
	public ComparePanel(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
	
	public int compareTo(ComparePanel panel) {
		return this.id - panel.getId();
	}
}
