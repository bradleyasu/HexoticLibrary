package com.hexotic.lib.ui.notificationbar;

import java.util.HashMap;
import java.util.Map;

public class NotificationCenter {

	private static NotificationCenter instance = null;
	
	private Map<String, NotificationBar> bars = new HashMap<String, NotificationBar>();
	
	private NotificationCenter(){
		
	}
	
	public void registerNotificationBar(String name, NotificationBar bar){
		bars.put(name, bar);
	}
	
	public void sendNotification(String bar, Notification n){
		if(bars.containsKey(bar)){
			bars.get(bar).showNotification(n);
		}
	}
	
	public void closeNotification(String bar){
		if(bars.containsKey(bar)){
			bars.get(bar).setCollapsed(true);
		}
	}

	public static NotificationCenter getInstance(){
		if(instance == null){
			instance = new NotificationCenter();
		}
		return instance;
	}
	
	
}
