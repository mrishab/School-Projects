package items;

import java.util.Scanner;

import controller.Controller;

public class Items {
//common methods and memebers for the most of the items in the game
	protected Cell location;
	protected Controller controller;
	protected String name;
	protected static Scanner scan = new Scanner(System.in);
	
	public Cell getLocation(){
		return this.location;
	}
	public void setLocation(Cell cell){
		this.location = cell;
	}
	public String getName(){
		return this.name;
	}
	
	public int getX(){
		return this.location.getX();
	}
	
	public int getY(){
		return this.location.getY();
	}
	public void setX(int x){
		this.location.setX(x);
	}
	public void setY(int y){
		this.location.setY(y);
	}
}
