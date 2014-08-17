package com.hexotic.lib.aa.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;

import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.lib.themes.ThemeFactory;
import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.input.textfield.BasicTextField;
import com.hexotic.lib.ui.input.textfield.BubbleTextField;
import com.hexotic.lib.ui.input.textfield.ModernTextField;
import com.hexotic.lib.ui.notificationbar.NotificationBar;
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
	}
	
	public void softButtonTest(){
		
		
		/* Create A Custom Themed Panel using a theme from the theme factory */
		
		HexPanel panel = new HexPanel(ThemeFactory.getInstance().getTheme(ThemeFactory.DEFAULT));
		
		
		
		panel.setLayout(new FlowLayout());
		
		BasicTextField field = new BasicTextField();
		field.setPreferredSize(new Dimension(400, 20));
		panel.add(field);  
		
		
		BubbleTextField bubble= new BubbleTextField();
		bubble.setPreferredSize(new Dimension(400, 20));
		panel.add(bubble);  
		
		ModernTextField modern = new ModernTextField();
		modern.setPreferredSize(new Dimension(400, 20));
		panel.add(modern);
		
		SoftButton button = new SoftButton("Test Button", Color.BLACK, Color.WHITE, new Font("Helvetica", Font.BOLD, 12));
		button.setPreferredSize(new Dimension(100,25));
		panel.add(button);
		
		BasicSwitch basic = new BasicSwitch("OFF", "ON", 100, 25, 0);
		basic.setBackground(Color.GREEN);
		basic.setForeground(new Color(0x484848));
		basic.setPreferredSize(new Dimension(100, 20));
		panel.add(basic);
		this.add(panel);
	}
	
}
