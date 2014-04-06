package com.hexotic.lib.ui.notificationbar;

public class Notification {
	public static final int INFO = 0;
	public static final int WARN = 1;
	public static final int ERROR = 2;
	public static final int ACCEPT = 3;
	
	public static final int CLOSE = 0;
	public static final int YES_NO = 1;
	public static final int OK = 2;
	
	private int type = INFO;
	private int options = 0;
	private String message = "[]";
	
	public Notification(int type, int options, String message){
		this.type = type;
		this.options = options;
		this.message = message;
	}
	
	public int getType(){
		return type;
	}
	
	public int getOptions(){
		return options;
	}
	
	public String getMessage(){
		return message;
	}
}
