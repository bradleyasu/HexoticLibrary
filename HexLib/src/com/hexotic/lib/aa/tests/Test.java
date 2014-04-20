package com.hexotic.lib.aa.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;

import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.lib.switches.SwitchEvent;
import com.hexotic.lib.switches.SwitchListener;
import com.hexotic.lib.themes.ThemeFactory;
import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.notificationbar.Notification;
import com.hexotic.lib.ui.notificationbar.NotificationBar;
import com.hexotic.lib.ui.notificationbar.NotificationListener;
import com.hexotic.lib.ui.panels.HexPanel;

public class Test extends JFrame{

	public static void main(String[] args){
		new Test();
	}
	
	public Test(){
		this.setTitle("Test");
		this.setPreferredSize(new Dimension(500,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		NotificationBar notify = new NotificationBar();
		this.add(notify, BorderLayout.NORTH);
		softButtonTest();
		this.pack();
		this.setVisible(true);
		for(int i = 0; i < 4; i++){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Notification notification = new Notification(i, Notification.OK, "TESTING");
			notification.addNotificationListener(new NotificationListener(){
				@Override
				public void optionSelected(String option) {
					System.out.println(option);
				}
			});
			notify.showNotification(notification);
		}
	}
	
	public void softButtonTest(){
		
		/* Create A Custom Themed Panel using a theme from the theme factory */
		HexPanel panel = new HexPanel(ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT));
		
		
		/* For visual decoration, add some footer color */
		panel.setFooterVisible(true); // Derived from the theme loaded in the theme factory
		
		/* Set Layout to basic flow layout */
		panel.setLayout(new FlowLayout());
		
		/* Hexotic Library Softbutton gives you a good looking button with minimal effort */
		SoftButton button = new SoftButton("Full Params", new Color(0xff6868), Color.WHITE, new Font("Arial", Font.BOLD, 12));
		button.setArc(20); // The amount of roundness to add to the button
		panel.add(button);
		
		/* Another Soft button but with a basic constructor */
		SoftButton noParamsButton = new SoftButton("Test No Params");
		noParamsButton.setBackgroundColor(new Color(0x68b3ff));
		panel.add(noParamsButton);
		
		/* A bigger button, notice how the button text is automatically aligned */
		SoftButton bigButton = new SoftButton("Big Button");
		bigButton.setBackgroundColor(new Color(0x68ffb4));
		bigButton.setPreferredSize(new Dimension(200, 80));
		panel.add(bigButton);
		
		
		/* Create a basic slidey switch thing.  By default, the DEFAULT theme will be used.  You can change this */
		
		BasicSwitch sw = new BasicSwitch("Off", "On", 100, 30, 10);
		
		/* of course we need to know when the switch changes state, listeners and events are provided */
		sw.addSwitchListener(new SwitchListener(){
			@Override
			public void switchTriggered(SwitchEvent e) {
				System.out.println(e.getState());
			}
			
		});	
		
		sw.setState(true);
		panel.add(sw);
		
		this.add(panel);
	}
	
}
