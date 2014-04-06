package com.hexotic.lib.switches;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexotic.lib.themes.Theme;
import com.hexotic.lib.themes.ThemeFactory;

/**
 * BasicSwitch
 * 
 * A basic on/off switch (or slider).
 * A SwitchListener can detect the current state of the switch
 * and user input
 * 
 * @author Bradley Sheets
 *
 */
public class BasicSwitch extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 4591972773400896741L;
	private boolean on = false;
	private int onX = 1;
	private int offX = 1;
	private int arc = 10;
	private int currentPos = offX;
	private boolean isRunning = false;
	private String onText;
	private String offText;
	private Color foreground;
	private Color background;
	private Color fontColor;
	private Font font;
	
	private List<SwitchListener> listeners = new ArrayList<SwitchListener>();
	
	public BasicSwitch(String offText, String onText, int width, int height, int arc){
		this.setPreferredSize(new Dimension(width, height));
		this.arc = arc;
		this.offText = offText;
		this.onText = onText;
		onX = width/2+2;
		this.setBackground(new Color(0,0,0,0));
		this.setOpaque(false);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setTheme(ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT));
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				throwSwitch();
			}
		});
		this.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e) {
				if(e.getX() <= onX && e.getX() >= offX){
					currentPos = e.getX();
					refresh();
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
		});
	
	}
	
	/**
	 * Set the theme of the switch
	 * @param theme The Theme
	 */
	public void setTheme(Theme theme){
		setBackground(theme.getBackgroundColor());
		this.setForeground(theme.getBackgroundColor());
		this.setFontColor(theme.getForeground());
		this.setFont(theme.getFont());
	}
	
	/**
	 * Manually override the background color of the switch
	 * 
	 * @param color Background Color
	 */
	public void setBackground(Color color){
		this.background = color;
	}
	
	/**
	 * Manually override the foreground color of the switch
	 * 
	 * @param color Foreground Color
	 */
	public void setForeground(Color color){
		this.foreground = color;
	}
	
	/**
	 * Manually override the font of the switch
	 * 
	 * @param font Font
	 */
	public void setFont(Font font){
		this.font = font;
	}
	
	/**
	 * Manually override the font color of the switch
	 * 
	 * @param color Font color
	 */
	public void setFontColor(Color color){
		this.fontColor = color;
	}
	
	/**
	 * Add a switch listener that can monitor when
	 * the switch state is updated
	 * 
	 * @param listener A SwitchListener
	 */
	public void addSwitchListener(SwitchListener listener){
		listeners.add(listener);
	}
	
	/**
	 * Toggle the switch
	 */
	public void throwSwitch(){
		on = !on;
		new Thread(this).start();
		for (SwitchListener listener : listeners)
			listener.switchTriggered(new SwitchEvent(on ? SwitchEvent.ON : SwitchEvent.OFF));
	}
	
	@Override
	public void run(){
		while(isRunning){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}	
		}
		isRunning = true;
		int destPos = offX;
		if(on){
			destPos = onX;
		}
		while(currentPos != destPos){
			if(currentPos > destPos){
				currentPos -= (currentPos - destPos) /2;
				currentPos--;
			} else {
				currentPos += (destPos - currentPos) /2;
				currentPos++;
			}
			this.revalidate();
			this.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			
		}
		isRunning = false;
	}
	
	private void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setPaint(new GradientPaint(new Point(0, 0),background.darker(), new Point(0, getHeight()*2), background.brighter()));
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		g2d.setColor(background.darker().darker());
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
		
		g2d.setPaint(new GradientPaint(new Point(0, 0),foreground.brighter(), new Point(0, getHeight()*2), foreground.darker()));
		g2d.fillRoundRect(currentPos,1, getWidth()/2-4, getHeight()-3, arc, arc);
		g2d.setColor(foreground.darker().darker());
		g2d.drawRoundRect(currentPos,1, getWidth()/2-4, getHeight()-3, arc, arc);
		
		g2d.setColor(fontColor);
		
		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics(font);
		if(on){
			int StringWidth = (int)metrics.getStringBounds(offText,g2d).getWidth();
			g2d.drawString(offText, getWidth()/4 - StringWidth/2,  getHeight()/2 + metrics.getHeight()/4);
		} else {
			int StringWidth = (int)metrics.getStringBounds(onText,g2d).getWidth();
			g2d.drawString(onText, getWidth()/2 + getWidth()/4 -  StringWidth/2,  getHeight()/2 + metrics.getHeight()/4);
		}
	}
	
}
