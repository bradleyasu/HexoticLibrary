package com.hexotic.lib.aa.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
		this.setPreferredSize(new Dimension(40,1000));
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
		//back.setBackground(Color.GREEN);
		back.add(new JLabel("BACK PANEL"));
	
		ModernTextField field = new ModernTextField("", "Placeholder");
		field.setPreferredSize(new Dimension(400, 30));
		field.setFont(new Font("Arial", Font.BOLD, 16));
		back.add(field);
		back.setLayout(new BorderLayout());
		back.add(new TestPanel(), BorderLayout.CENTER);
		
		flipper = new FlipPanel(front, back);
		flipper.setDirection(FlipPanel.LEFT);
		
		this.add(flipper, BorderLayout.CENTER);
		
	}
	
	
	private class TestPanel extends JPanel {
		
		private ToxicProgress progress;
		
		public TestPanel() {
			progress = new ToxicProgress();
			progress.cycle();
			this.setPreferredSize(new Dimension(250,250));
			progress.setProgress(0);
			progress.showText(false);

			Thread t = new Thread(new Runnable(){
				public void run(){
					while(progress.getProgress() < 100){
						progress.setProgress(progress.getProgress()+2);
						try {
							Thread.sleep(1000);
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
			this.setBackground(Color.WHITE);
			
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

			progress.Draw(g, 10,10, getWidth()-20, getHeight()-20);
		}
		
	}
	
}
