package com.hexotic.lib.ui.panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.ui.windows.HexFrame;
/**
 * The title bar provides a bar that can drag a window without
 * any default OS title bar.  Intended for windows without chrome (obviously)
 * 
 * @author Bradley Sheets
 *
 */
public class TitleBar extends JPanel{
	private JLabel close;
	private int posX = 0;
	private int posY = 0;
	private HexFrame mainWindow; 
	private enum STATE {MAXIMIZE, NORMAL, MINIMIZED};
	
	private STATE state = STATE.NORMAL;
	
	private Rectangle normalBounds = null;
	
	public TitleBar(String title, HexFrame window){
		this.setPreferredSize(new Dimension(100,20));
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		mainWindow = window;
		close = new JLabel("X");
		close.setPreferredSize(new Dimension(10,10));
		close.setVerticalAlignment(JLabel.CENTER);
		close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close.setForeground(mainWindow.getTheme().getForeground().brighter().brighter());
		close.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				close.setForeground(mainWindow.getTheme().getForeground());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				close.setForeground(mainWindow.getTheme().getForeground().brighter().brighter());
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		this.add(close);
		
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((e.getClickCount() % 2 == 0)) {
				     e.consume();
				     if(state == STATE.NORMAL){
				    	 normalBounds = mainWindow.getBounds();
				    	 mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				    	 state = STATE.MAXIMIZE;
				     } else {
				    	 mainWindow.setBounds(normalBounds);
				    	 mainWindow.setState(JFrame.NORMAL);
				    	 state = STATE.NORMAL;
				     }
				}
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
			}
			
		});
		
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				posX = e.getX();
				posY = e.getY();
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter(){
		     public void mouseDragged(MouseEvent e){
				mainWindow.setLocation (e.getXOnScreen()-posX,e.getYOnScreen()-posY);
		     }
		});
	}

	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Apply our own painting effect
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(new GradientPaint(new Point(0, 0), mainWindow.getTheme().getBackgroundColor(), new Point(0, getHeight()*2), mainWindow.getTheme().getBackgroundColor().darker()));  
		g2d.fillRect(0,0, getWidth(), getHeight());
		g2d.setPaint(mainWindow.getTheme().getBackgroundColor().darker());
		g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
	}
	
}
