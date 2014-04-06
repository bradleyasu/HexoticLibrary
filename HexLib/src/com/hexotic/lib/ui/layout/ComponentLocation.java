package com.hexotic.lib.ui.layout;

public class ComponentLocation {

	private int x, y;
	private int currentX = 0;
	private int currentY = 0;
	private int width, height;
	
	private boolean hasChanged = false;
	
	public ComponentLocation(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.currentX = x;
		this.currentY = y;
		this.width = width;
		this.height = height;
	}
	
	public ComponentLocation setX(int x){
		hasChanged = true;
		this.x = x;
		return this;
	}
	
	public ComponentLocation setY(int y){
		hasChanged = true;
		this.y = y;
		return this;
	}
	
	public ComponentLocation setWidth(int width){
		this.width = width;
		return this;
	}
	
	public ComponentLocation setHeight(int height){
		this.height = height;
		return this;
	}
	
	public ComponentLocation update(){
		hasChanged = false;
		if (x != currentX){
			if(x < currentX){
				currentX -= ((currentX-x)/2);
				hasChanged = true;
			}
			if(x > currentX){
				currentX += ((x-currentX)/2);
				hasChanged = true;
			}
		}
		if (y != currentY){
			if(y < currentY){
				currentY -= ((currentY-y)/2);
				hasChanged = true;
			}
			if(y > currentY){
				currentY += ((y-currentY)/2);
				hasChanged = true;
			}
		}
		return this;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getCurrentX(){
		return currentX;
	}
	
	public int getCurrentY(){
		return currentY;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean hasChanged(){
		return hasChanged;
	}
	
}
