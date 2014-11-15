package com.hexotic.lib.ui.panels;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FlipPanel extends JPanel {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	private static final String FRONT = "FRONT";
	private static final String BACK = "BACK";
	private static final String ANIMATION = "ANIMATION";

	private AnimationPanel animationPanel;

	private JPanel front;
	private JPanel back;

	private CardLayout layout;

	// Defaults
	private int direction = UP;
	private boolean flipped = false;

	public FlipPanel(JPanel front, JPanel back) {
		this.front = front;
		this.back = back;
		this.animationPanel = new AnimationPanel();

		layout = new CardLayout();
		this.setLayout(layout);

		this.add(front, FRONT);
		this.add(animationPanel, ANIMATION);
		this.add(back, BACK);

		this.setPreferredSize(new Dimension(front.getWidth(), front.getHeight()));

	}

	/**
	 * Flip the panel to front or back. This will trigger the flipping animation
	 */
	public void flip() {
		front.setVisible(false);
		back.setVisible(false);

		layout.show(this, ANIMATION);
		animationPanel.animate();
	}

	private void animationFinished(){
		if (isFlipped()) {
			layout.show(this, BACK);
		} else {
			layout.show(this, FRONT);
		}
	}
	
	public boolean isFlipped() {
		return this.flipped;
	}

	public JPanel getVisiblePanel() {
		JPanel visible = front;
		if (isFlipped()) {
			visible = back;
			;
		}
		return visible;
	}

	/**
	 * Sets the animation direction. Available options are LEFT, RIGHT, UP, and
	 * DOWN
	 * 
	 * @param direction
	 *            Animation Direction
	 */
	public void setDirection(int direction) {
		if (direction >= 0 && direction <= 4) {
			this.direction = direction;
		}
	}

	class AnimationPanel extends JPanel {
		private BufferedImage frontImage;
		private BufferedImage backImage;

		private Timer timer;

		private int x;
		private int y;
		private int destx = 0;
		private int desty = 0;
		private int scale = 4;

		private int delay = 50;

		public AnimationPanel() {
			timer = new Timer(delay, createListener());
		}

		public BufferedImage createImage(JPanel panel) {
			BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			panel.print(g);
			return bi;
		}

		private void setup() {
			backImage = createImage(back);
			frontImage = createImage(front);
			if (isFlipped()) {
				switch (direction) {
				case LEFT:
					destx = getWidth();
					break;
				case RIGHT:
					destx = -getWidth();
					break;
				case UP:
					desty = getHeight();
					break;
				case DOWN:
					desty = -getHeight();
					break;
				}
				x = 0;
				y = 0;
			} else {
				switch (direction) {
				case LEFT:
					x = getWidth();
					break;
				case RIGHT:
					x = -getWidth();
					break;
				case UP:
					y = getHeight();
					break;
				case DOWN:
					y = -getHeight();
					break;
				}
				destx = 0;
				desty = 0;
			}
		}

		public void animate() {
			setup();
			timer.start();
		}

		private ActionListener createListener() {
			ActionListener action = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
						if (x != destx || y != desty) {
							if (x > destx) {
								x -= (x - destx) <= scale ? 1 : ((x - destx) / scale);
							} else if (x < destx) {
								x += (destx - x) <= scale ? 1 : ((destx - x) / scale);
							}
							if (y > desty) {
								y -= (y - desty) <= scale ? 1 : ((y - desty) / scale);
							} else if (y < desty) {
								y += (desty - y)  <= scale ? 1 : ((desty - y) / scale);
							}
						} else {
							timer.stop();
							flipped = !flipped;
							animationFinished();
						}
					revalidate();
					repaint();
				}
			};
			return action;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			backImage = createImage(back);
			frontImage = createImage(front);

			g2d.drawImage(frontImage, 0, 0, null);
			g2d.drawImage(backImage, x, y, null);

		}
	}

}
