package com.hexotic.lib.ui.input.textfield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JTextField;

public class BubbleTextField extends BasicTextField{

	public BubbleTextField(){
		
	}
	
	public BubbleTextField(String placeholder){
		super(placeholder);
	}
	
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g.create();
	    JTextField field = getField();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	    Point2D start = new Point2D.Float(0, 0);
	    Point2D end = new Point2D.Float(0, getHeight());
	    
	    float[] dist = {0.0f, 0.2f, 0.7f, 1.0f};
	    Color[] colors;
    	colors = new Color[]{field.getBackground().darker(), field.getBackground(), field.getBackground(), field.getBackground().darker()};
    	g2d.setPaint(new LinearGradientPaint(start, end, dist, colors)); 
    	
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5,5);
		g2d.setColor(field.getBackground().darker().darker());
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 5,5);
	}
}
