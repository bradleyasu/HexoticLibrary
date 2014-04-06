package com.hexotic.lib.themes;

import java.awt.Color;
import java.awt.Font;

public class DarkTheme implements Theme{

	private Color background;
	private Color forground;
	private Color fontColor;
	private Font font;
	private Color[] palette;
	
	public DarkTheme(){
		this.background = new Color(0x424242);
		this.forground = new Color(0x323232);
		this.font = new Font("Arial", 14, Font.BOLD);
		this.fontColor = new Color(0x010101);
		palette = new Color[]{new Color(0x010101), 
							  new Color(0x858D83), 
							  new Color(0xDADBDA), 
							  new Color(0x343833), 
							  new Color(0x222B21)};
	}
	
	@Override
	public Color getBackgroundColor() {
		return background;
	}

	@Override
	public Color getForeground() {
		return forground;
	}

	@Override
	public Color getFontColor() {
		return fontColor;
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return font;
	}

	@Override
	public Color[] getColorPalette() {
		return palette;
	}

}
