package com.hexotic.lib.resource;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.hexotic.lib.exceptions.ResourceException;

public class Resources {

	private static Resources instance;
	private Map<String, Image> images;
	private ClassLoader cldr = this.getClass().getClassLoader();
	
	private Resources(){
		images = new HashMap<String, Image>();
	}
	
	public Image getImage(String resource) throws ResourceException{
		resource = resource.toLowerCase();
		Image image = null;
		URL iconPth = null;
		if(images.containsKey(resource)){ /* To avoid reading from disk over and over, we'll get it from RAM if we can */
			image = images.get(resource);
		} else {
			iconPth   = cldr.getResource("images/"+resource);
			if(iconPth == null){
				throw(new ResourceException("Class Loader Resource Not Found - "+cldr.getResource(".")));
			}
			image = new ImageIcon(iconPth).getImage();
				
		}
		if(image == null){
			throw(new ResourceException("Image Not Found: " + ((iconPth == null) ? "NULL" : iconPth.getPath())));
		}
		return image;
	}
	
	public Font getFont(String resource) throws FontFormatException, IOException{
		InputStream istream = cldr.getResourceAsStream("fonts/"+resource);
		Font myFont = Font.createFont(Font.TRUETYPE_FONT, istream);
		return myFont;
	}
	
	public static Resources getInstance(){
		if (instance == null){
			instance = new Resources();
		}
		return instance;		
	}
	
	public URL getDoc(String resource) {
		java.net.URL document = cldr.getResource("docs/"+resource);
		return document;
	}

	
	public URL getAudio(String resource) {
		java.net.URL audioUrl = cldr.getResource("audio/"+resource);
		return audioUrl;
	}
	
	  public void installFile(String exec, String outputPath) throws IOException
	  {
	    InputStream is = this.cldr.getResource("execs/" + exec).openStream();
	    
	    OutputStream os = new FileOutputStream(outputPath);
	    
	    byte[] b = new byte['?'];
	    int length;
	    while ((length = is.read(b)) != -1)
	    {
	      os.write(b, 0, length);
	    }
	    is.close();
	    os.close();
	  }
	
}
