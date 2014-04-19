package com.hexotic.lib.ui.windows.components;

import java.awt.Color;
import java.io.File;

import javax.swing.JPanel;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;

public class FileExplorer extends JPanel{

	private File selectedFile = null;
	
	public FileExplorer(File root){
		this.setBackground(Color.WHITE);
		this.setLayout(new AnimatedGridLayout(true));
		setLocation(root);
	}
	
	private void setLocation(File location){
		this.removeAll();
		if(location.listFiles() != null){
			for(File file : location.listFiles()){
				FileIcon ico = new FileIcon(file);
				ico.addFileIconListener(new FileIconListener(){
					public void FileIconSelected(FileIconEvent e) {
						selectedFile = e.getFile();
						if(selectedFile.isDirectory() && selectedFile.canRead()){
							setLocation(selectedFile);
						}
					}
				});
				this.add(ico);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void getSelectedFile(){
		
	}
}
