package com.hexotic.lib.ui.input.textfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import com.hexotic.lib.util.StringOps;

/**
 * This is a custom "pretty" looking input box with placeholder
 * functionality for the YouTube Downloader UI
 * 
 * @author Bradley Sheets
 *
 */
public class ModernTextField extends JTextField {

	private String resource = "/resources/images/";
	
	private static final long serialVersionUID = 127422547464506328L;
	private String prompt;
	private Image confirm;
	private Image deny;
	private boolean showIcons = true;

	private boolean acceptedInput = false;

	public ModernTextField(String text, String prompt) {
		super(text);
		this.prompt = prompt;

		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 25));
		this.setForeground(new Color(0x424242));
		
		setAcceptIcon((new ImageIcon(getIcon("accept"))).getImage());
		setDenyIcon((new ImageIcon(getIcon("error"))).getImage());
		

	}

	public void setAcceptIcon(Image image){
		this.confirm = image;
	}
	
	public void setDenyIcon(Image image) {
		this.deny = image;
	}
	
	public void showIcons(boolean show){
		this.showIcons = show;
	}
	
	public boolean isShowingIcons() {
		return showIcons;
	}
	public void setAccepted(boolean accepted){
		this.acceptedInput = accepted;
		this.revalidate();
		this.repaint();
	}
	
	private URL getIcon(String type){
		return this.getClass().getResource(resource+type+".png");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(new Color(0xffffff));
		g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);

		g2d.setColor(new Color(0xe0e0e0));
		g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 2, 4, 4);

		Paint p = new GradientPaint(0, 0, new Color(0xd8d8d8), 0, 2, new Color(0, 0, 0, 0));
		g2d.setPaint(p);
		g2d.fillRect(0, 0, getWidth(), 2);

		Dimension bounds = StringOps.getStringBounds(g2d, this.getFont(), prompt);
		
		int verticalLoc = getHeight() / 2  - (int) (bounds.getHeight()/2);
		
		if (!getText().isEmpty() && acceptedInput) {
			if(showIcons)
				g2d.drawImage(confirm, getWidth() - 20, verticalLoc, null);
		} else if (!getText().isEmpty() && !acceptedInput) {
			if(showIcons)
				g2d.drawImage(deny, getWidth() - 20, verticalLoc, null);
		} else {
			g2d.setColor(new Color(0x888888));
			g2d.setFont(this.getFont());
			g2d.drawString(prompt, 5, getHeight()/2+5);
		}
	}
}
