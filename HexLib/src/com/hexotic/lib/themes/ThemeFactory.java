package com.hexotic.lib.themes;

import java.util.HashMap;
import java.util.Map;

/**
 * ThemeFactory
 * 
 * Provides a standard method to theme your application without
 * having to manually save references in all of your objects
 * 
 * @author Bradley Sheets
 *
 */
public class ThemeFactory {

	private static ThemeFactory instance = null;
	
	public static final int DEFAULT = 0;
	public static final int DARK = 1;
	private Map<String, Theme> themes;
	
	private ThemeFactory(){
		themes = new HashMap<String, Theme>();
	}
	
	/**
	 * Get a pre-made skin from the theme factory
	 * 
	 * @param themeID ID of theme to get
	 * @return the queried theme
	 */
	public Theme getTheme(int themeID){
		Theme ret = null;
		switch (themeID){
		case ThemeFactory.DEFAULT:
			if(themes.containsKey("[DEFAULT]")){
				ret = themes.get("[DEFAULT]");
			} else {
				ret = new DefaultTheme();
				themes.put("[DEFAULT]", ret);
			}
			break;
		case ThemeFactory.DARK:
			if(themes.containsKey("[DARK]")){
				ret = themes.get("[DARK]");
			} else {
				ret = new DarkTheme();
				themes.put("[DARK]", ret);
			}
			break;
		}
		return ret;
	}
	
	/**
	 * Save a custom theme into the theme factory to be used later.
	 * 
	 * @param themeName Name of your custom theme
	 * @param theme The theme to store into the factory
	 * @throws ThemeException
	 */
	public void saveTheme(String themeName, Theme theme) throws ThemeException{
		if(themes.containsKey(themeName)){
			throw new ThemeException("Theme Already Exists");
		} else {
			themes.put(themeName, theme);
		}
	}
	
	/**
	 * Get a saved theme out of the ThemeFactory
	 * @param themeName Name of your custom theme that's previously been saved
	 * @return theme The saved custom theme
	 * @throws ThemeException 
	 */
	public Theme getTheme(String themeName) throws ThemeException{
		Theme ret = null;
		if(themes.containsKey(themeName)){
			ret = themes.get(themeName);
		} else {
			throw new ThemeException("Theme doesn't exist");
		}
		return ret;
	}
	
	public static ThemeFactory getInstance(){
		if (instance == null){
			instance = new ThemeFactory();
		}
		return instance;
	}
	
}
