package components;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
import items.Cell;
import items.Gadgets;

public class Player extends Person {
	private int score, currInv;
	private boolean alive = true;
	private ArrayList <Gadgets> inventory;
	private final int INV_CAP = 3;

	public Player(Controller controller){
		super(controller);
		this.location = new Cell(5,5);
		this.currInv = 2;
		this.score = 0;
		this.inventory = new ArrayList<Gadgets>();
		this.inventory.add(new Gadgets("Scanner"));
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String printLocation(){
		return this.location.printCell();
	}
	
	public void setDead(){
		this.alive = false;
	}

	public boolean isAlive(){
		return this.alive;
	}
	//checks for the gadget in inventory
	public boolean hasGadget(String name){
		boolean hasGadget = false;
		for (Gadgets gadget: this.inventory){
			if(gadget.getName().equals(name)){
				hasGadget =  true;
				break;
			}
		}
		return hasGadget;
	}
	//use the gadget whose name is entered in the parameter
	public void useGadget(String name){
		for (Gadgets gadget: this.inventory)
			if(gadget.getName().equals(name)){
				gadget.use(this.controller);
				break;
			}
	}
	public boolean isFull(){
		return this.currInv >= INV_CAP;
	}
	//picks the gadget
	
	public void pick(Gadgets gadget){
		if(this.currInv <= INV_CAP){
			this.inventory.add(gadget);
			this.currInv++;
		}
		else
			System.out.println("All the 3 Pockets are full!");
		
	}
	public Gadgets getGadget(String gadgetName){
		Gadgets gad = null;
		for(Gadgets gadget: this.inventory){
			if(gadget.getName().equals(gadgetName)){
				gad = gadget;
			}
		}
		return gad;
	}
	public void removeGadget(String gadgetName){
		for(int i = 0; i < this.inventory.size(); i++){
			if(this.inventory.get(i).getName().equals(gadgetName)){
				this.inventory.remove(i);
				this.currInv--;
				break;
			}
		}
	}
	
	public void changeScoreBy(int i){
		this.score += i;
	}
	public int getScore(){
		return this.score;
	}
	
	public int getCurrInv(){
		return this.currInv;
	}
	private void setCurrInv(int currInv){
		this.currInv = currInv;
	}
	private void setScore(int score){
		this.score = score;
	}
	
	
	public static void save(BufferedWriter file, Player player) throws IOException{
		file.write(Integer.toString(player.getX()) + ',');
		file.write(Integer.toString(player.getY()) + ',');
		file.write(Integer.toString(player.getScore()) + ',');
		file.write(player.getName() + ',');
		file.newLine();
		
		for(int i = 0; i < player.inventory.size(); i++)
			file.write(player.inventory.get(i).getName() + ",");
		file.newLine();
	}
	
	public static void restore(BufferedReader file, Player player, Map map) throws IOException{
		String[] data = Controller.getDataAt(file, 1);
		
		player.setLocation(map.getLocation(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
		player.setScore(Integer.parseInt(data[2]));
		player.setName(data[3]);
		
		data = Controller.getDataAt(file, 2);
		for(int i = 1; i < data.length; i++)
			player.pick(new Gadgets(data[i]));
		player.setCurrInv(data.length);
	}
	
	public ArrayList<Gadgets> getInventory(){
		return this.inventory;
	}
	
}
