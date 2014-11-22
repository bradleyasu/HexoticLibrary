package com.hexotic.lib.ui.loaders;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;


public class ToxicProgress extends ProgressLoader{

    private int yScale  = 0;
    private int yBase   = yScale;
    private Color color = new Color(0xffffff);
	private boolean reverse = false;
	
	private int upperLimit = 12;
	private int lowerLimit = -2;
	private List<Bubble> bubbles;
	private Random bubbleRandomizer = new Random();
	public ToxicProgress() {
		
		bubbles = new ArrayList<Bubble>();
	}
	
	public void cycle() {
        timer = new Timer(75 , new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(getProgress() == 100){
					yScale = 0;
					yBase = 0;
					
				} else {
					if(yScale > upperLimit || yScale < lowerLimit){
						yBase   = yScale;
						reverse = !reverse;
					}
					if(reverse){
						yScale--;
					} else {
						yScale++;
					}
					yBase   = Math.abs(yScale);
				}
				if(getProgress() == 100 && bubbles.isEmpty()){
					stopCycle();
				} else {
					for(Bubble bubble : bubbles){
						bubble.move();
					}
				}
				update();
			}
        });
        timer.setRepeats(true);
        timer.start();
	}
	
	private void update(){
		notifyListeners();
	}
	
	
	public void stopCycle() {
		if(timer != null){
			timer.stop();
		}
	}

	public void addBubbles(int count, int height, int width){
		bubbles.clear();
		Random rand = new Random();
		for(int i = 0; i < count; i++){
			bubbles.add(new Bubble(rand.nextInt(width), rand.nextInt(height)));
		}
	}
	
	
	@Override
	public void Draw(Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g;
		if(bubbles.isEmpty()){
			addBubbles(40, height, width);
		}
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Object resetStrokeHint = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
		
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		
		Stroke resetStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(1.0f));

		

        int startX = x;
        int startY = y;
        g2.setColor(new Color(0x232323));
        g2.fillRect(startX, startY, width, height);
        
        
        
        GradientPaint gp1 = new GradientPaint(0, 0, color, 0, height, color.darker().darker(), true);
        g2.setPaint(gp1);
        double percentage = getProgress()/100 * height;
        for( int i=startX; i < width+startX; i++ )
        {   x = i;
            y = (int)( height - percentage + yBase - Math.sin( Math.toRadians(i) ) * yScale ) + startY;
            g2.drawLine( x, y, x, y );
           	g2.drawLine(x, y, x, height+startY);
        }
        
        for(Bubble bubble : bubbles){
        	if(bubble.getY() > height-percentage+yBase+upperLimit && (bubble.getX() > startX && bubble.getX() < width-startX) && (bubble.getY() > startY && bubble.getY() < height-startY)){
        		g2.fillOval(bubble.getX(), bubble.getY(), bubble.getSize(), bubble.getSize());
        	} else if(getProgress()<100){
        		bubble.reset();
        	}
        }
        
        
        GradientPaint glass = new GradientPaint(startX+25,startY+15, new Color(255,255,255,80), width, startY+15, new Color(255,255,255,0), true);
        g2.setPaint(glass);
        g2.fillRect(startX, startY, width, height);

        
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawRect(startX, startY, width, height);
		
		g2.setStroke(resetStroke);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, resetStrokeHint);
	}
	
	
	class Bubble{
		
		private int x;
		private int y;
		private int size = 10;
		private int wobble = size;
		private int rightLim;
		private int leftLim;
		private boolean reverse;
		private int startX;
		private int startY;
		
		public Bubble(int x, int y) {
			this.x = x;
			this.y = y;
			this.startX = x;
			this.startY = y;
			this.rightLim = x+wobble;
			this.leftLim = x-wobble;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int getSize(){
			return size;
		}
		
		public void reset() {
			x = bubbleRandomizer.nextInt(startX);
			y = startY;
		}
		
		public void move() {
			y-=size;
			if(x > rightLim || x < leftLim){
				reverse = !reverse;
			}
			if(reverse){
				x-=bubbleRandomizer.nextInt(wobble);
			} else {
				x+=bubbleRandomizer.nextInt(wobble);
			}
		}
		
	}
}
