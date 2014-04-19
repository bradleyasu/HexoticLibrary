package com.hexotic.lib.ui.windows.components;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;

public class FileExplorer extends JPanel{

	private File selectedFile = null;
	private List<FileExplorerListener> listeners = new ArrayList<FileExplorerListener>();
	
	public FileExplorer(File root){
		this.setBackground(Color.WHITE);
		this.setLayout(new AnimatedGridLayout(true));
		setLocation(root);
	}
	
	public void setLocation(File location){
		this.removeAll();
		if(location.listFiles() != null){
			for(File file : location.listFiles()){
				FileIcon ico = new FileIcon(file);
				ico.addFileIconListener(new FileIconListener(){
					public void FileIconSelected(FileIconEvent e) {
						selectedFile = e.getFile();
						if(selectedFile.isDirectory() && selectedFile.canRead()){
							setLocation(selectedFile);
							notifyListeners();
						}
					}
				});
				this.add(ico);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void addFileExplorerListener(FileExplorerListener fel){
		listeners.add(fel);
	}
	
	private void notifyListeners(){
		for(FileExplorerListener l : listeners){
			l.rootChanged(selectedFile);
		}
	}
	
	public File getSelectedFile(){
		return selectedFile;
	}
}
