package com.hexotic.lib.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Provides basic sound effects and allows for custom sound effects
 * 
 * @author Bradley Sheets
 *
 */
public class SoundFX {

	public static final int ERROR_FX 	= 0;
	public static final int NOTIFY_FX 	= 1;
	public static final int WARN_FX 	= 2;
	public static final int INFO_FX 	= 3;
	public static final int SUCCESS_FX  = 4;
	
	private static final String resource = "resources/audio/";
	
	private SoundFX(){	/* Singleton and static referecnes */}

	/**
	 * Plays a predefined library sound effect
	 * 
	 * @param soundFX
	 * 			Sound effect to play
	 */
	public static void play(int soundFX){
		switch (soundFX){
		case ERROR_FX:
			playSound(resource + "error.wav");
			break;
		case NOTIFY_FX:
			playSound(resource + "notify.wav");
			break;
		case WARN_FX:
			playSound(resource + "warn.wav");
			break;
		case INFO_FX:
			playSound(resource + "info.wav");
			break;
		case SUCCESS_FX:
			playSound(resource + "success.wav");
			break;
		}
	}
	
	/**
	 * Plays a sound from a local resource.  Intended for
	 * use with short audio clips.  Use AudioPlayer for
	 * extended audio functionalities
	 * @param resource
	 * 		
	 */
	public static void play(String resource){
		playSound(resource);
	}
	
	
	private static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					SoundFX fx = new SoundFX();
					ClassLoader cldr = fx.getClass().getClassLoader();
					Clip clip = AudioSystem.getClip();
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(cldr.getResourceAsStream(url));
			        clip.open(inputStream);
			        clip.start(); 
			      } catch (Exception e) { /* SoundFX Failed.  Play Nothing. */ }
			    }
			}).start();
	}
}
