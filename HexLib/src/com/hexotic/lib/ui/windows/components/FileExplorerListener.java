package com.hexotic.lib.ui.windows.components;

import java.io.File;

public interface FileExplorerListener {

	public void rootChanged(File file);
	
	public void fileSelected(File file);
}
