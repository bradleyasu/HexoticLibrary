package com.hexotic.lib.switches;

/**
 * SwitchListener
 * 
 * To be used with Switch Controls to detect which state
 * the switch is in
 * 
 * @author Bradley Sheets
 *
 */
public interface SwitchListener{
	
	/**
	 * The event when a switch has been clicked by the user
	 * The Switch event will contain the current state of the 
	 * switch
	 * @param e SwitchEvent containing current switch state
	 */
	public void switchTriggered(SwitchEvent e);
}
