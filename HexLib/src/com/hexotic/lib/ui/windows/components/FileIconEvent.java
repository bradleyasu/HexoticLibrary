package com.hexotic.lib.ui.windows.components;

import java.io.File;

public class FileIconEvent {

	private File file;
	private FileIcon source; 
	
	public FileIconEvent(File file, FileIcon source){
		this.file = file;
		this.source = source;
	}
	
	
	public FileIcon getSource(){
		return source;
	}
	
	public File getFile(){
		return file;
	}
	
}
