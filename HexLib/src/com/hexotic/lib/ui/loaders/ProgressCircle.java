package com.hexotic.lib.ui.loaders;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import com.hexotic.lib.resource.Drawable;
import com.hexotic.lib.util.StringOps;

/**
 * Pretty progress circle thingy. This object is a modern looking progress bar
 * in the shape of a circle
 * 
 * @author Bradley Sheets
 * 
 */
public class ProgressCircle implements Drawable {

	/* By Default, the progress is 0% */
	private double progress = 0;
	private int arcStart = 90;

	private Color colorOne = Color.BLACK;
	private Color colorTwo = Color.GRAY;

	private Color fontColor = Color.white;
	private Font font = new Font("Arial", Font.BOLD, 28);
	private boolean cycle = false;
	private boolean showText = true;
	
	private Timer timer;
	
	private List<ProgressListener> listeners;

	public ProgressCircle() {

		listeners = new ArrayList<ProgressListener>();
	}

	public void setProgress(double progress) {
		if (progress > 100) {
			progress = 100;
		}
		if (progress < 0) {
			progress = 0;
		}
		this.progress = progress;
	}

	public void setColor(Color start, Color end) {
		colorOne = start;
		colorTwo = end;
	}
	
	public double getProgress() {
		return this.progress;
	}
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public void setFontColor(Color color){
		this.fontColor = color;
	}
	
	public void showText(boolean showText){
		this.showText = showText;
	}
	
	public boolean isShowingText() {
		return this.showText;
	}

	@Override
	public void Draw(Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Object resetStrokeHint = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
		
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		
		Stroke resetStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(8.0f));
		GradientPaint gp = new GradientPaint(75, 75, colorOne, 10, 95, colorTwo, true);
		g2.setPaint(gp);

		int arc = (int) (progress / 100.00 * 360);
		
		// Make the arc half full if the progress is 0
		if(progress == 0){
			arc = (int) (1 / 100.00 * 360);
		}
		g2.drawArc(x, y, width, height, arcStart, arc * -1);

		// Draw and Center percent String
		if(showText){
			g2.setFont(font);
			g2.setColor(fontColor);
			
			String percent = String.valueOf((int) progress) + "%";
			Dimension dims = StringOps.getStringBounds(g2, font, percent);
			int px = (int) (width/2 - dims.getWidth()/percent.length())+x;
			int py = (int) (height/2 + dims.getHeight()/2 );
			g2.drawString(percent, px, py);
		}
		
		g2.setStroke(resetStroke);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, resetStrokeHint);
	}
	
	public void addProgressListener(ProgressListener listener){
		listeners.add(listener);
	}
	
	private void notifyListeners() {
		for(ProgressListener listener: listeners){
			listener.progressUpdated(progress);
		}
	}

	public void cycle() {
		ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            		arcStart -= 5;
            		notifyListeners();
            }
        };
        cycle = true;
        timer = new Timer(75 ,taskPerformer);
        timer.setRepeats(true);
        timer.start();
	}
	
	public void stopCycle() {
		cycle = false;
		if(timer != null){
			timer.stop();
		}
	}
	

}
