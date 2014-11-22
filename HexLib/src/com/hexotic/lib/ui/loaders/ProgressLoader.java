package com.hexotic.lib.ui.loaders;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import com.hexotic.lib.resource.Drawable;

public abstract class ProgressLoader implements Drawable{

	/* By Default, the progress is 0% */
	private double progress = 0;
	private boolean showText = true;
	
	protected Timer timer;
	
	private List<ProgressListener> listeners;
	
	public ProgressLoader (){
		listeners = new ArrayList<ProgressListener>();
	}
	
	public void setProgress(double progress) {
		if (progress > 100) {
			progress = 100;
		}
		if (progress < 0) {
			progress = 0;
		}
		this.progress = progress;
	}

	public double getProgress() {
		return this.progress;
	}
	
	
	public void showText(boolean showText){
		this.showText = showText;
	}
	
	public boolean isShowingText() {
		return this.showText;
	}
	
	
	public void addProgressListener(ProgressListener listener){
		listeners.add(listener);
	}
	
	protected void notifyListeners() {
		for(ProgressListener listener: listeners){
			listener.progressUpdated(progress);
		}
	}
	
	
}
