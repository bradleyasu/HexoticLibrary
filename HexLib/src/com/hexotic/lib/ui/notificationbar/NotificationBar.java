package com.hexotic.lib.ui.notificationbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;

public class NotificationBar extends JPanel{

	private final String resource = "resources/images/";
	private ClassLoader cldr = this.getClass().getClassLoader();
	private Color[] colors;
	private ImageIcon[] icons;

	private JLabel icon;
	private JLabel message;
	private NotificationOptionPanel optionsPanel;
	
	private int type = 0;
	private int options = 0;
	private boolean collapsed = true;
	
	public NotificationBar(){
		try {
			init();
			this.setPreferredSize(new Dimension(200, 0));
			this.setLayout(new BorderLayout());
			this.add(icon, BorderLayout.WEST);
			this.add(message, BorderLayout.CENTER);
			this.add(optionsPanel, BorderLayout.EAST);
		} catch (MalformedURLException e) {}
	}
	
	public void setCollapsed(boolean collapsed){
		this.collapsed = collapsed;
		if(this.collapsed){
			for(int i = 30; i >=0 ; i--){
				this.setPreferredSize(new Dimension(200,i));
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
				}
			}
		} else {
			for(int i = 1; i <=30 ; i += i+(i/2)){
				this.setPreferredSize(new Dimension(200, i));
				this.revalidate();
				this.repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void showNotification(Notification notification){
		type = notification.getType();
		options = notification.getOptions();
		icon.setIcon(icons[type]);
		message.setText(notification.getMessage());
		optionsPanel.setOptions(options);
		this.setBackground(colors[notification.getType()]);
		this.setBorder(BorderFactory.createLineBorder(colors[notification.getType()].darker().darker()));
		setCollapsed(false);
	}
	
	private void init() throws MalformedURLException{
		colors = new Color[] {
				new Color(0xa2caff), 
				new Color(0xf8ff80), 
				new Color(0xfd5a5a),
				new Color(0xcaffa2)
		};
		icons = new ImageIcon[]{
			new ImageIcon(new URL(cldr.getResource(resource)+"information.png")),
			new ImageIcon(new URL(cldr.getResource(resource)+"error.png")),
			new ImageIcon(new URL(cldr.getResource(resource)+"exclamation.png")),
			new ImageIcon(new URL(cldr.getResource(resource)+"accept.png"))
		};
		icon = new JLabel(icons[0]);
		icon.setPreferredSize(new Dimension(24,24));
		message = new JLabel("");
		message.setPreferredSize(new Dimension(200, 30));
		optionsPanel = new NotificationOptionPanel();
	}

	class NotificationOptionPanel extends JPanel{
		private SoftButton[] buttons = {
				new SoftButton("OK"),
				new SoftButton("Yes"),
				new SoftButton("No"),
				new SoftButton("Close")
		};
		private Font font = new Font("Arial", Font.BOLD, 11);
		public NotificationOptionPanel(){
			this.setOpaque(false);
			this.setBackground(new Color(0,0,0,0));
		}
		
		public void setOptions(int options){
			buttonSetup();
			this.removeAll();
			switch (options){
			case Notification.OK:
				this.add(buttons[0]);
				break;
			case Notification.YES_NO:
				this.add(buttons[1]);
				this.add(buttons[2]);
				break;
			case Notification.CLOSE:
				this.add(buttons[3]);
				break;
			}
		}
		
		private void buttonSetup(){
			for(SoftButton button : buttons){
				button.setBackgroundColor(colors[type]);
				button.setForegroundColor(new Color(0x242424));
				button.setFont(font);
				button.setArc(4);
				button.showShadow(false);
				button.setPreferredSize(new Dimension(60,20));
			}
		}
				
	}
}
