package com.hexotic.lib.ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class SimpleScroller extends BasicScrollBarUI {

	public SimpleScroller() {
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
	}

	 
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
	}

	@Override
	protected void paintDecreaseHighlight(Graphics g) {
		Insets insets = scrollbar.getInsets();
		Rectangle thumbR = getThumbBounds();
		g.setColor(new Color(137, 144, 144));

		if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			int x = thumbR.x ;
			int y = decrButton.getY() + decrButton.getHeight();
			int w = thumbR.width;
			int h = thumbR.y - y;
			g.fillRect(x, y, w, h);
		} else {
			int x, w;
			if (scrollbar.getComponentOrientation().isLeftToRight()) {
				x = decrButton.getX() + decrButton.getWidth() / 2;
				w = thumbR.x - x;
			} else {
				x = thumbR.x + thumbR.width;
				w = decrButton.getX() - x;
			}
			int y = insets.top;
			int h = scrollbar.getHeight() - (insets.top + insets.bottom);
			g.fillRect(x, y, w, h);
		}
	}

	@Override
	protected void paintIncreaseHighlight(Graphics g) {
		Insets insets = scrollbar.getInsets();
		Rectangle thumbR = getThumbBounds();
		g.setColor(new Color(202, 207, 203));

		if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			int x = thumbR.x;
			int y = thumbR.y;
			int w = thumbR.width;
			int h = incrButton.getY() - y;
			g.fillRect(x, y, w, h);
		}
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(Color.WHITE);
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
		paintDecreaseHighlight(g);
		paintIncreaseHighlight(g);

	}


	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
			return;
		}

		int w = thumbBounds.width;
		int h = thumbBounds.height;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.translate(thumbBounds.x, thumbBounds.y);

		GradientPaint gr = new GradientPaint(2, 0, new Color(0x424242), 18, 16, new Color(0x484848));
		g2.setPaint(gr);
//		g2.fill(new Ellipse2D.Double(2, 0, w - 1, h - 1));
		g2.fillRect(0, 0, w, h);

//		if(isThumbRollover()){
//			gr = new GradientPaint(2, 0, new Color(0xc0c0c0), 18, 16, new Color(137, 144, 144));
//			g2.setPaint(gr);
//		} else {
//			g2.setPaint(new Color(137, 144, 144));
//		}
//		g2.fill(new Ellipse2D.Double(6, 4, 7, 7));

		g.translate(-thumbBounds.x, -thumbBounds.y);
	}

}
