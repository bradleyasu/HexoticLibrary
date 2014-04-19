package com.hexotic.lib.ui.windows.components;

import java.io.File;

public class FileIconEvent {

	private File file;
	
	public FileIconEvent(File file){
		this.file = file;
	}
	
	public File getFile(){
		return file;
	}
	
}
