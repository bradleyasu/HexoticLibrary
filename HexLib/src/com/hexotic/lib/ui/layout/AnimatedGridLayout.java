package com.hexotic.lib.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Timer;

/**
 * AnimatedGridLayout
 * 
 * Provides a 'grid'/'flow' layout. Intended for use for organizing Panels for a
 * file or item view. The times can be comparable and ordered automatically.
 * 
 * @author Bradley Sheets
 * 
 */
public class AnimatedGridLayout implements LayoutManager2, ActionListener {
	private int vgap = 5;
	private int hgap = 5;
	private int minWidth = 0, minHeight = 0;
	private int preferredWidth = 0, preferredHeight = 0;
	private boolean sizeUnknown = true;
	private int refreshRate = 40;
	private ComponentLocation lastLocation = null;
	private boolean working = false;
	
	private Timer timer = new Timer(refreshRate, this);

	private Map<Component, ComponentLocation> components;

	/**
	 * Creates an AnimatedGridLayout Default attributes will be applied where
	 * hgap is 5, vgap is 5, and ordered is false
	 */
	public AnimatedGridLayout() {
		this(5, 5, false);
	}

	/**
	 * Creates an AnimatedGridLayout
	 * 
	 * @param vgap
	 *            Vertical space between components
	 * @param hgap
	 *            Horizontal space between components
	 */
	public AnimatedGridLayout(int vgap, int hgap) {
		this(vgap, hgap, false);
	}

	/**
	 * Creates an AnimatedGridLayout
	 * 
	 * @param ordered
	 *            If the components are comparable, set to true to order
	 */
	public AnimatedGridLayout(boolean ordered) {
		this(5, 5, ordered);
	}

	/**
	 * Creates an AnimatedGridLayout
	 * 
	 * @param vgap
	 *            Vertical space between components
	 * @param hgap
	 *            Horizontal space between components
	 * @param ordered
	 *            If the components are comparable, set to true to order
	 */
	public AnimatedGridLayout(int vgap, int hgap, boolean ordered) {
		timer.setInitialDelay(refreshRate);
		timer.start(); 		
		this.vgap = vgap;
		this.hgap = hgap;
		if (ordered) {
			components = Collections
					.synchronizedMap(new TreeMap<Component, ComponentLocation>());
		} else {
			components = Collections
					.synchronizedMap(new HashMap<Component, ComponentLocation>());
		}
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	@Override
	public void layoutContainer(Container parent) {
		if(parent.getWidth() <= minWidth){
			return;
		}
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		// int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int x = hgap;
		int y = vgap;
		if (sizeUnknown) {
			setSizes(parent);
		}
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component comp = parent.getComponent(i);
			Dimension d = comp.getPreferredSize();
			int[] next = nextXY(d.width, d.height, maxWidth);
			if (!components.containsKey(comp) && comp.isVisible()) {
				lastLocation = new ComponentLocation(next[0], next[1], d.width,
						d.height);
				if (!working) { /*
								 * If the UI is being updated, we'll add it next
								 * time around
								 */
					components.put(parent.getComponent(i), lastLocation);
				}
			}
		}

		int compCount = 0;
		int rowCount = 0;
		int colCount = 0;
		List<ComponentLocation> prevStac = new ArrayList<ComponentLocation>();

		for (Component comp : components.keySet()) {
			
			if (comp.isVisible()) {
				if (x + components.get(comp).getWidth() + hgap >= maxWidth) {
					x = hgap;
					components.get(comp).setX(x);
					rowCount++;
					colCount = compCount / rowCount;
				}
				if (rowCount > 0) {
					if (prevStac.size() != compCount - colCount) {
						y = prevStac.get(compCount - colCount).getHeight()
								+ vgap
								+ prevStac.get(compCount - colCount).getY();
						components.get(comp).setY(y);
					}
				}
				Dimension d = comp.getPreferredSize();
				components.get(comp).setX(x).setY(y).setWidth(d.width)
						.setHeight(d.height);
				x += components.get(comp).getWidth() + hgap;
				compCount++;
				prevStac.add(components.get(comp));
			}
		}
		prevStac.clear();
	}

	private int[] nextXY(int width, int height, int max) {
		int[] next = { hgap, vgap };

		if (lastLocation != null) {
			next[0] = lastLocation.getX() + hgap + lastLocation.getWidth();
			if (next[0] > max) {
				next[0] = hgap;
			}
			next[1] = 0;
		}
		return next;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width = minWidth + insets.left + insets.right;
		dim.height = minHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		setSizes(parent);
		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		// TODO Auto-generated method stub
		comp.revalidate();
		comp.repaint();

	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setSizes(Container parent) {
		int nComps = parent.getComponentCount();
		Dimension d = null;

		// Reset preferred/minimum width and height.
		preferredWidth = 0;
		preferredHeight = 0;
		minWidth = 0;
		minHeight = 0;

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				d = c.getPreferredSize();

				if (i > 0) {
					if (d.width > preferredWidth) {
						preferredWidth = d.width + hgap;
					}
					if (d.height + c.getY() + vgap > preferredHeight) {
						preferredHeight = d.height + c.getY() + vgap;
					}
				} else {
					if (d.width > preferredWidth) {
						preferredWidth = d.width + hgap;
					}
					if (d.height > preferredHeight) {
						preferredHeight = d.height + vgap;
					}
				}

				minWidth = preferredWidth;
				minHeight = preferredHeight;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
			working = true;
			for (Component comp : components.keySet()) {
				if (comp.isVisible()) {
					ComponentLocation loc = components.get(comp).update();
					comp.setBounds(loc.getCurrentX(),
							loc.getCurrentY(), loc.getWidth(),
							loc.getHeight());
					comp.revalidate();
					comp.repaint();
				}
			}
			working = false;
	}
}
