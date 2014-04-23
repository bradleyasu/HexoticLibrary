package com.hexotic.lib.ui.input.textfield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JTextField;

public class ModernTextField extends BasicTextField{

	public ModernTextField(){
		this("");
	}
	
	public ModernTextField(String placeholder){
		super(placeholder);
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g.create();
	    JTextField field = getField();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(field.getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setColor(field.getForeground());
		g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
		g2d.drawLine(0, getHeight()/2, 0, getHeight());
		g2d.drawLine(getWidth()-1, getHeight()/2, getWidth()-1, getHeight());
	}
}
