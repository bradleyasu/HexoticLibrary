package com.hexotic.lib.ui.windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;

public class Test extends JFrame{
	
	public static void main(String[] args){
		new Test();
	}

	public Test(){
		this.setTitle("Hello");
		//HexPanel panel = new HexPanel(ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT));
		JPanel panel = new JPanel();
		panel.setLayout(new AnimatedGridLayout());
		this.setPreferredSize(new Dimension(800,500));
		
		this.add(new JScrollPane(panel));
		pack();
		this.setVisible(true);
		for(int i = 0; i < 500; i++){
//			HexPanel p = new HexPanel(ThemeFactory.getInstance().getTheme(ThemeFactory.DARK));
			JPanel p = new JPanel();
			p.setPreferredSize(new Dimension(40,40));
			p.setBackground(Color.RED);
//			p.setFooterVisible(true);
			panel.add(p);
		}
		
	}
}
