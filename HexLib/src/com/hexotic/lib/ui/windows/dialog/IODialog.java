package com.hexotic.lib.ui.windows.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hexotic.lib.ui.windows.components.FileExplorer;

public class IODialog extends JDialog implements ActionListener{
	
	public static void main(String[] args){
		new IODialog(null, "test", "ign");
	}
	
	public IODialog(JFrame parent, String title, String message) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize(); 
			Point p = parent.getLocation(); 
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		
		
		init();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private void init(){
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(600, 380));
		container.setLayout(new BorderLayout());
		
		/* TOP */
		JPanel navigation = new JPanel();
		navigation.setBackground(Color.RED);
		navigation.setPreferredSize(new Dimension(600, 50));
		container.add(navigation, BorderLayout.NORTH);
		
		/* SELECTION */
		FileExplorer explorer = new FileExplorer(new File("C:\\"));
		JScrollPane scroller = new JScrollPane(explorer);
		container.add(scroller, BorderLayout.CENTER);
		/* BOTTOM */
		JPanel options = new JPanel();
		options.setBackground(Color.BLUE);
		options.setPreferredSize(new Dimension(600, 80));
		container.add(options, BorderLayout.SOUTH);
		
		getContentPane().add(container, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		dispose();
	}
		    
		    

}
