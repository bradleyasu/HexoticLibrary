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
	private List<FileIcon> icons = new ArrayList<FileIcon>();
	
	public FileExplorer(File root){
		this.setBackground(Color.WHITE);
		this.setLayout(new AnimatedGridLayout(true));
		setLocation(root);
	}
	
	public void setLocation(File location){
		this.removeAll();
		icons.clear();
		if(location.listFiles() != null){
			for(File file : location.listFiles()){
				FileIcon ico = new FileIcon(file);
				icons.add(ico);
				ico.addFileIconListener(new FileIconListener(){
					public void FileIconSelected(FileIconEvent e) {
						for(FileIcon icon : icons){
							if(!icon.equals(e.getSource()))
								icon.setSelected(false);
						}
						selectedFile = e.getFile();
						if(selectedFile.isDirectory() && selectedFile.canRead()){
							setLocation(selectedFile);
						}
						notifyListeners();
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
			l.fileSelected(selectedFile);
		}
	}
	
	public File getSelectedFile(){
		return selectedFile;
	}
}
