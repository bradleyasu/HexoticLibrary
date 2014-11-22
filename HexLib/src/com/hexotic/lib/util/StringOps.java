package com.hexotic.lib.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class StringOps {

	
	 public static Dimension getStringBounds(Graphics2D g2, Font font, String text) {
		// get metrics from the graphics
		 FontMetrics metrics = g2.getFontMetrics(font);
		 // get the height of a line of text in this
		 // font and render context
		 int hgt = metrics.getHeight();
		 // get the advance of my text in this font
		 // and render context
		 int adv = metrics.stringWidth(text);
		 // calculate the size of a box to hold the
		 // text with some padding.
		 Dimension size = new Dimension(adv+2, hgt+2);
		 return size;
    }

}
