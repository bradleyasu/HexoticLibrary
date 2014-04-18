package com.hexotic.lib.ui.panels;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JPanel;

import com.hexotic.lib.themes.Theme;

public class HexPanel extends JPanel{

	private Theme theme;
	private boolean showTag = false;
	private boolean showFooter = false;
	
	public HexPanel(Theme theme){
		this.theme = theme;
	}
	
	public void setTagVisible(boolean visible){
		this.showTag = visible;
	}
	
	public void setFooterVisible(boolean visible){
		this.showFooter = visible;
	}
	
	public void setTheme(Theme theme){
		this.theme = theme;
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);       
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 Point2D center = new Point2D.Float(getWidth()/2,getHeight()+50);
	     float radius = getWidth();
	     float[] dist = {0.0f, 1.0f};
	     Color[] colors = {theme.getBackgroundColor(), theme.getBackgroundColor().darker()};
		RadialGradientPaint p = new RadialGradientPaint(center, radius,
		                                 dist, colors,
		                                 CycleMethod.NO_CYCLE);
		//g2d.setPaint(new GradientPaint(new Point(0, 0), theme.getBackgroundColor(), new Point(0,getHeight()), theme.getBackgroundColor().darker()));
		g2d.setPaint(p);
		g2d.fillRect(0,0, getWidth(), getHeight());
		
		Color[] palette = theme.getColorPalette();
		Random gen = new Random();
		if(showTag) {
			for(int i = 4; i < 9; i++){
				g2d.setColor(palette[gen.nextInt(palette.length)]);
				g2d.fillRect(0,i*15, 15,15);
			}
		}
		
		if(showFooter) {
			for(int i = 0; i < getWidth()/5; i++){
				g2d.setColor(palette[gen.nextInt(palette.length)]);
				g2d.fillRect(i*5,getHeight()-5, 5, 5);
			}
		}
	}  
}
