package com.hexotic.lib.aa.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.ui.panels.FlipPanel;

public class Test extends JFrame{
	private FlipPanel flipper;
	public static void main(String[] args){
		new Test();
	}
	
	public Test(){
		this.setTitle("Test");
		this.setPreferredSize(new Dimension(500,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		this.setLayout(new BorderLayout());
		flipPanelTest();
		
		JButton button = new JButton("FLIP");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				flipper.flip();
			}
		});
		
		this.add(button, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
	
	public void flipPanelTest(){
		JPanel front = new JPanel();
		front.setBackground(Color.RED);
		front.setLayout(new BorderLayout());
		front.add(new JLabel("FRONT PANEL"), BorderLayout.NORTH);
		front.add(new JEditorPane(), BorderLayout.CENTER);
		
		JPanel back = new JPanel();
		back.setBackground(Color.GREEN);
		back.add(new JLabel("BACK PANEL"));
		
		flipper = new FlipPanel(front, back);
		
		this.add(flipper, BorderLayout.CENTER);
		
	}
	
}
