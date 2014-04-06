package com.hexotic.lib.switches;

/**
 * SwitchEvent 
 * 
 * Contains the current state of a switch when
 * a user triggers the switch listener
 * 
 * @author Bradley Sheets
 *
 */
public class SwitchEvent{

	public static final int OFF = 0;
	public static final int ON = 1;
	
	private int state;
	
	public SwitchEvent(int state) {
		this.state = state;
	}
	
	/**
	 * Get the current state of the switch
	 * States include: SwitchEvent.ON and SwitchEvent.OFF

	 * @return Current state of the switch
	 */
	public int getState(){
		return state;
	}

}
