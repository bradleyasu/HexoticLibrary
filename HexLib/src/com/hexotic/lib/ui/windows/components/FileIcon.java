package com.hexotic.lib.ui.windows.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileIcon extends JPanel implements MouseListener, Comparable<FileIcon>{

	private String resource = "/resources/images/explorer/";
	private JLabel icon;
	private Color background = new Color(0,0,0,0);
	private Color highlight = new Color(0xdadada);
	private File file;
	
	private List<FileIconListener> listeners = new ArrayList<FileIconListener>();
	
	public FileIcon(File file){
		this.file = file;
		icon = new JLabel();
		this.setPreferredSize(new Dimension(98,98));
		icon.setIcon(new ImageIcon(getIcon("file")));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.add(icon);
		this.add(new JLabel(file.getName()));
		this.setBackground(background);
		this.addMouseListener(this);
	}
	
	private File getFile(){
		return file;
	}
	
	private void notifyListeners(){
		for(FileIconListener listener : listeners){
			listener.FileIconSelected(new FileIconEvent(getFile()));
		}
	}
	public void addFileIconListener(FileIconListener listener){
		listeners.add(listener);
	}
	
	private URL getIcon(String type){
		return this.getClass().getResource(resource+type+".png");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.setBackground(highlight);
		notifyListeners();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(FileIcon o) {
		if(getFile().getName() != null && o.getFile().getName() != null){
            return getFile().getName().compareTo(o.getFile().getName());
        }
		return 0;
	}
}
