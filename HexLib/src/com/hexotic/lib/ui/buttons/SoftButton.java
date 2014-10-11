package com.hexotic.lib.ui.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.hexotic.lib.themes.Theme;
import com.hexotic.lib.themes.ThemeFactory;

public class SoftButton extends JButton{

	private static final long serialVersionUID = -5068382421001647103L;
	private Color backgroundColor = ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT).getBackgroundColor();
	private Color foregroundColor = ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT).getForeground();
	private Font font = ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT).getFont();
	private String text;
	private boolean isHover = false;
	private int offset = 0;
	private int arc = 10;
	private boolean showShadow = true;
	
	public SoftButton(String buttonText){
		this.text = buttonText;
		init();
	}
	
	public SoftButton(String buttonText, Color backgroundColor, Color foregroundColor, Font font){
		this.text = buttonText;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
		this.font = font;
		init();
	}
	
	public void setBackgroundColor(Color color){
		this.backgroundColor = color;
	}
	
	public void setForegroundColor(Color color){
		this.foregroundColor = color;
	}
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public void setTheme(Theme theme){
		setBackgroundColor(theme.getBackgroundColor());
		setForegroundColor(theme.getForeground());
		setFont(theme.getFont());
	}
	
	public void showShadow(boolean shadow){
		showShadow = shadow;
	}
	
	public void setArc(int arc){
		this.arc = arc;
	}
	
	private void init(){
		this.setText(text);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(new Color(0,0,0,0));
		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(120,30));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				isHover = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				isHover = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				offset = 1;
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				offset = 0;
			}
			
		});
	}
	
	public void setSoftButtonText(String text){
		this.text = text;
		this.setText(text);
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	    Point2D start = new Point2D.Float(0, 0);
	    Point2D end = new Point2D.Float(0, getHeight());
	    
	    float[] dist = {0.0f, 0.2f, 0.8f, 1.0f};
	    Color[] colors;
	    if(!isHover){
	    	colors = new Color[]{backgroundColor.brighter(), backgroundColor, backgroundColor, backgroundColor.darker()};
	    } else if(offset > 0){
	    	colors = new Color[]{backgroundColor.darker(), backgroundColor, backgroundColor, backgroundColor.darker()};
	    } else {
	    	colors = new Color[]{backgroundColor.brighter(), backgroundColor, backgroundColor, backgroundColor.brighter()};
	    }
		g2d.setPaint(new LinearGradientPaint(start, end, dist, colors));  
		g2d.fillRoundRect(offset, offset, getWidth()-offset-offset, getHeight()-offset-offset, arc, arc);
		
		g2d.setPaint(backgroundColor.darker().darker());
		g2d.drawRoundRect(offset, offset, getWidth()-1-offset-offset, getHeight()-1-offset-offset, arc, arc);
		
		if(offset > 0 && showShadow){
			g2d.setColor(new Color(44,44,44,80));
			g2d.fillRoundRect(4, getHeight()-2, getWidth()-8, 2, arc-2, arc-2);
		}
		
		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics(font);
		int stringWidth = (int)metrics.getStringBounds(text,g2d).getWidth();
		int stringHeight = (int)metrics.getStringBounds(text,g2d).getHeight();
		g2d.setColor(foregroundColor);
		g2d.drawString(text, getWidth()/2 - stringWidth/2, getHeight()/2+stringHeight/3);
		
		
	}
	
	
}
