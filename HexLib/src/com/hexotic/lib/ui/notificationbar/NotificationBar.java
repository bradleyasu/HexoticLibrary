package com.hexotic.lib.ui.notificationbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;

public class NotificationBar extends JPanel{

	private static final long serialVersionUID = 6047982077151477833L;
	private final String resource = "/resources/images/";
	private Color[] colors;
	private ImageIcon[] icons;

	private JLabel icon;
	private JLabel message;
	private NotificationOptionPanel optionsPanel;
	
	private int type = 0;
	private int options = 0;
	private boolean collapsed = true;
	
	private List<NotificationListener> listeners = new ArrayList<NotificationListener>();
	
	public NotificationBar(){
		try {
			init();
			this.setPreferredSize(new Dimension(200, 0));
			this.setLayout(new BorderLayout());
			this.add(icon, BorderLayout.WEST);
			this.add(message, BorderLayout.CENTER);
			this.add(optionsPanel, BorderLayout.EAST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setCollapsed(boolean collapsed){
		this.collapsed = collapsed;
		if(this.collapsed){
			for(int i = 30; i >=0 ; i--){
				this.setPreferredSize(new Dimension(200,i));
				this.revalidate();
				this.repaint();
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
		listeners.clear();
		listeners.add(notification.getListener());
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
			new ImageIcon(this.getClass().getResource(resource+"information.png")),
			new ImageIcon(this.getClass().getResource(resource+"error.png")),
			new ImageIcon(this.getClass().getResource(resource+"exclamation.png")),
			new ImageIcon(this.getClass().getResource(resource+"accept.png"))
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
				for(ActionListener l : button.getActionListeners()){
					button.removeActionListener(l);
				}
				button.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						for(NotificationListener l :  listeners){
							l.optionSelected(((SoftButton)e.getSource()).getText());
						}
					}
				});
			}
		}
				
	}
}
