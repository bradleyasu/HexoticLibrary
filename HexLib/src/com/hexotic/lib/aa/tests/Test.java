package com.hexotic.lib.aa.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;

import com.hexotic.lib.actionfx.ActionFX;
import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.lib.switches.SwitchEvent;
import com.hexotic.lib.switches.SwitchListener;
import com.hexotic.lib.ui.buttons.SoftButton;

public class Test extends JFrame{

	public static void main(String[] args){
		new Test();
	}
	
	public Test(){
		this.setTitle("Test");
		this.setPreferredSize(new Dimension(500,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		softButtonTest();
		this.pack();
		this.setVisible(true);
	}
	
	public void softButtonTest(){
		SoftButton button = new SoftButton("Martin", new Color(0xffffe0), Color.WHITE, new Font("Arial", Font.BOLD, 12));
		button.setArc(50);
		this.add(button);
		
		this.add(new SoftButton("Test No Params"));
		
		BasicSwitch sw = new BasicSwitch("Off", "On", 100, 30, 10);
		sw.addSwitchListener(new SwitchListener(){
			@Override
			public void switchTriggered(SwitchEvent e) {
				System.out.println(e.getState());
			}
			
		});
		
		sw.setBackground( new Color(0xff6868));
		sw.setForeground( new Color(0xff6868));
		
		this.add(sw);
	}
	
}
