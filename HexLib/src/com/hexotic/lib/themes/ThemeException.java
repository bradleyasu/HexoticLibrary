package com.hexotic.lib.themes;

public class ThemeException extends Exception{

	private static final long serialVersionUID = 3288136785563874632L;
	private String exception;
	
	public ThemeException(String exception){
		this.exception = exception;
	}
	
	public String getThemeExceptioin(){
		return exception;
	}
}
