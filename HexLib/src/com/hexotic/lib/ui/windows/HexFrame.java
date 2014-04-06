package com.hexotic.lib.ui.windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import com.hexotic.lib.themes.Theme;
import com.hexotic.lib.themes.ThemeFactory;
import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.lib.ui.panels.HexPanel;
import com.hexotic.lib.ui.panels.TitleBar;

public class HexFrame extends JFrame{

	private Theme theme;
	
	public static void main(String[] args){
		HexFrame frame = new HexFrame();
		frame.setTitle("My Title");;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		frame.setVisible(true);
	}
	
	private HexPanel container;
	
	public HexFrame(){
		super();
		theme = ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		container = new HexPanel(theme);
		container.setTagVisible(true);
		container.setFooterVisible(true);
		container.setLayout(new AnimatedGridLayout());
		
		this.setContentPane(container);
		setTheme(ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT));
		
		this.getRootPane().setBorder(BorderFactory.createLineBorder(theme.getBackgroundColor().darker()));

	}
	
	public Theme getTheme(){
		return theme;
	}
	
	public void setTheme(Theme theme){
		this.theme = theme;
		this.setBackground(this.theme.getBackgroundColor());
		this.setForeground(this.theme.getForeground());
		container.setTheme(theme);
	}

		
}
