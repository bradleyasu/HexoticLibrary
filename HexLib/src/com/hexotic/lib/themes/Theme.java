package com.hexotic.lib.themes;

import java.awt.Color;
import java.awt.Font;

public interface Theme {

	public Color getBackgroundColor();
	public Color getForeground();
	public Color getFontColor();
	public Font getFont();
	public Color[] getColorPalette();
	
}
