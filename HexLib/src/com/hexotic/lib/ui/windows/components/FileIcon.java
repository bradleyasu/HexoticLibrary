package com.hexotic.lib.ui.windows.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;
public class FileIcon extends JPanel implements MouseListener, Comparable<FileIcon>{

	private String resource = "/resources/images/explorer/";
	private JLabel icon;
	private JLabel title;
	private Color background = new Color(0,0,0,0);
	private Color highlight = new Color(0xdadada);
	private File file;
	
	private List<FileIconListener> listeners = new ArrayList<FileIconListener>();
	
	public FileIcon(File file){
		this.file = file;
		icon = new JLabel();
		this.setPreferredSize(new Dimension(84 ,84));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		icon.setIcon(new ImageIcon(getIcon("file")));
		if(file.isDirectory()){
			icon.setIcon(new ImageIcon(getIcon("folder")));
		}
		this.add(icon);
		
		title = new JLabel(file.getName());
		title.setPreferredSize(new Dimension(64,20));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setToolTipText(file.getName());
		
		this.add(title);
		this.setBackground(background);

		title.addMouseListener(this);
		icon.addMouseListener(this);
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
