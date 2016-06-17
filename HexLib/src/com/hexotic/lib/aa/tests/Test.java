package com.hexotic.lib.aa.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.input.textfield.ModernTextField;
import com.hexotic.lib.ui.loaders.ProgressListener;
import com.hexotic.lib.ui.loaders.ToxicProgress;
import com.hexotic.lib.ui.panels.FlipPanel;

public class Test extends JFrame{
	private FlipPanel flipper;
	public static void main(String[] args){
		new Test();
	}
	
	public Test(){
		this.setTitle("Test");
		this.setPreferredSize(new Dimension(800,1000));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		this.setLayout(new BorderLayout());
		flipPanelTest();
		
		SoftButton button = new SoftButton("FLIP");
		button.setArc(2);
		button.setBackgroundColor(new Color(0x212121));
		button.setForegroundColor(new Color(0xdfdfdf));  
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				flipper.flip();
			}
		});
		
		this.add(button, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
	
	public void flipPanelTest(){
		JPanel front = new JPanel();
		front.setBackground(Color.RED);
		front.setLayout(new BorderLayout());
		front.add(new JLabel("FRONT PANEL"), BorderLayout.NORTH);
		front.add(new JEditorPane(), BorderLayout.CENTER);
		
		JPanel back = new JPanel();
		back.add(new JLabel("BACK PANEL"));
		ModernTextField field = new ModernTextField("", "Placeholder");
		field.setPreferredSize(new Dimension(400, 30));
		field.setFont(new Font("Arial", Font.BOLD, 16));
		back.setLayout(new FlowLayout());
		back.add(field);
		back.add(new TestPanel());
		
		flipper = new FlipPanel(front, back);
		flipper.setDirection(FlipPanel.LEFT);
		
		this.add(flipper, BorderLayout.CENTER);
		
	}
	
	
	private class TestPanel extends JPanel {
		
		private ToxicProgress progress;
		
		public TestPanel() {
			progress = new ToxicProgress();
			progress.cycle();
			this.setPreferredSize(new Dimension(50,250));
			progress.setProgress(0);
			progress.showText(false);
			progress.setColor(new Color(0x8a0707));
			
			Thread t = new Thread(new Runnable(){
				public void run(){
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(progress.getProgress() < 99){
						progress.setProgress(progress.getProgress()+1);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
			progress.addProgressListener(new ProgressListener(){

				@Override
				public void progressUpdated(double progress) {
					update();
				}
				
			});
			
		}
		
		private void update(){
			this.revalidate();
			this.repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			progress.Draw(g, 0,0, getWidth(), getHeight());
		}
		
	}
	
}
