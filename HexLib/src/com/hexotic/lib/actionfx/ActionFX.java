package com.hexotic.lib.actionfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActionFX extends JFrame implements Runnable{
	
	private static final String resource = "resources/actionFX/";
	private int frameX = 0;
		
	public ActionFX(int x, int y){ 
       setTitle("ActionFX");
       this.setContentPane(new FXPanel());
       this.setUndecorated(true);
       setBackground(new Color(0,0,0,0));
       pack();
       this.setLocation(200,200);
       setVisible(true);
       new Thread(this).start();
	}
	
	class FXPanel extends JPanel{
		
		public FXPanel(){
			this.setPreferredSize(new Dimension(124,124));
			this.setOpaque(false);
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			ClassLoader cldr = this.getClass().getClassLoader();
			
			Image animation = null;
			try {
				animation = new ImageIcon(new URL(cldr.getResource(resource)+"poof.png")).getImage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Color c=new Color(0,0,0,0);
			g2d.setPaint(c);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			int y = 0 - (frameX*124);
			g2d.drawImage(animation, 0, y, null);
			g2d.dispose();
		}
	}

	@Override
	public void run() {
		while(frameX < 5){
			try{
				Thread.sleep(40);
				this.revalidate();
				this.repaint();
				frameX++;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		this.dispose();
	}
}
