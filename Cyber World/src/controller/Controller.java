package controller;
import model.ChangeLog;
import model.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import components.Map;
import components.Person;
import components.Player;
import items.BigPuzzle;
import items.Cell;
import items.Gadgets;

public class Controller {
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();

	}
	private static final String SAVE_NAME = "CyberWorld.dat";
	private boolean playing;
	private Commands command;
	private Map map;
	private Person enemy; 
	private Player player;
	private Scanner scan = new Scanner(System.in);
	
	//initial welcome screen description
	private String gameDesc = 
			
			"\n\nThe Cybercity is spread over an area of 13 X 13 Blocks. You start at Location (5,5). The most famous Hacker\n"
			+ " Group called \'The Dark Army\' have hidden various Gadgets which can be used to gain unauthorised access to\n"
			+ "many systems. But these gadgets are protected by HACKBOXES which can only be unlocked by a true Hacker like you.\n"
			+ "Let's get going.";

	
	public Controller(){
		this.playing = true;
		this.player = new Player(this);
		this.map = new Map(this);
		this.enemy = new Player(this);
		this.command = new Commands(this);

	}
	//terminates the game.
	public void gameOver(){
		System.out.println("Your score was: " + this.player.getScore());
		System.out.println("Game Over.");
		this.player.setDead();
	}
	//initiates a new game
	public void newGame(){
		String name;
		System.out.println("\nHi Player! Let's get you setup. Please enter your name to continue. (type \"help\" if you're stuck.)");
		name = scan.nextLine();
		this.player.setName(name);
		System.out.println("\n\nHi " + this.player.getName() + ". You are all setup to get on your mission!");
		System.out.println(gameDesc);
		
		System.out.println(this.player.printLocation());
		
		System.out.println("\n\n\t\tPlease enter a command to Begin");
		
		while(this.player.isAlive()){
			this.command.takeCommand();
			this.command.function(this);
			System.out.println(this.player.printLocation());
		}
	}
	
	public boolean isPlayerAlive(){
		return this.player.isAlive();
	}
	
	//sets the position of player on the map over the movable cells which are free from fixed items.
	public void setPlayerPosition(int i, int j){
		if(!this.map.getLocation(i, j).isBlock() && !this.map.hasFixedItem(this.getMapLocation(i, j)))
			this.player.setLocation(this.map.getLocation(i, j));
		else if(this.map.hasFixedItem(this.getMapLocation(i, j)))
			System.out.println("You cannot move over a " + this.map.getFixedItem(this.getMapLocation(i, j)).getName());
		
		else
			System.out.println("There is a wall in that direction");
		this.changeDescription();
		this.showDescription();
	}
	
	public int getPlayerY(){
		return this.player.getY();
	}
	public int getPlayerX(){
		return this.player.getX();
	}
	
	public boolean gadgetThere(String name){
		return this.map.hasGadget(name);
	}
	
	public Cell getPlayerPosition(){
		return this.player.getLocation();
	}

	public void pick(String name) {
		
		if(this.map.isFixedItem(name)){
			if(this.isItemInRange(name))
				System.out.println("You can't pick" + name);
			else
				System.out.println("There is no " + name + " in reach");
		}
		
		else{
		
			if(!this.player.isFull()){
				
				if(this.gadgetThere(name)){
					if(this.map.hasPuzzle() && this.map.getPuzzle().isSolved() || !this.map.hasPuzzle()){
						this.player.pick(this.map.getGadget(name));
						this.map.removeGadget(this.map.getGadget(name));
						this.changeDescription();
						System.out.println(name + " picked.");
					}
					
					else{
						System.out.println("You're not qualified to receive this gadget");
					}
				}
				else{
					System.out.println("There is no " + name + " at this spot!");
				}
			}
			else
				System.out.println("You can't carry anymore stuff.");
		}
	}
	
	
	public void gadgetPick(Cell cell){
		this.player.pick(this.map.getGadget(cell));
		this.map.removeGadget(this.map.getGadget(player.getLocation()));
		System.out.println(this.map.getGadget(cell).getName() + " picked.");
	}

	public void useGadget(String name) {
		if(this.player.hasGadget(name))
			this.player.useGadget(name);
		else
			System.out.println("You're not carrying any " + name);
	}
	/*
	 * algorithm to move the enemy object closer by one step each time a incorrect answer is given for the puzzle
	 */
	public void moveEnemyNear(){
		
		if(this.player.getLocation() != this.enemy.getLocation()){

			int difference = this.enemy.getY() - this.player.getY();
			
			if(difference == 0){
				difference = this.enemy.getX() - this.player.getX();
				difference = difference/(Math.abs(difference));
				this.enemy.setLocation(this.map.getLocation((this.enemy.getX() - difference), this.enemy.getY()));
				/*
				 * make changes here to move enemy closer to player .change setposition function
				 */
			}
			
			else{
				difference = difference/(Math.abs(difference));
				this.enemy.setLocation(this.map.getLocation(this.enemy.getX(), (this.enemy.getY() - difference)));
			}
		}
		
		else{
			this.gameOver();
		}
			System.out.println(this.enemy.getLocation().printCell());
			this.player.changeScoreBy(-100);
	}
	//runs when a fixed item is only one block in front of you and access command is made
	public void useFixedItem(String name){
		if(this.map.hasFixedItem(name)){
			if(this.isItemInRange(name)){
				if(this.map.getFixedItem(name).isUsable())
					this.map.getFixedItem(name).access();
				else
					System.out.println("I cant apply that word here.");
			}
			else
				System.out.println("You're too away from " + name);
		}
		else
			System.out.println("There is no " + name + " here.");
	}
	//checks if the fixed is in range, i.e., only one block away from you.
	public boolean isItemInRange(String name){
		boolean isIteminRange = false;
		if(this.map.hasFixedItem(name)){
			int distX = this.map.getFixedItem(name).getLocation().getX() - this.getPlayerX();
			int distY = this.map.getFixedItem(name).getLocation().getY() - this.getPlayerY();
			
			if(distX == 0 && Math.abs(distY) == 1)
				isIteminRange = true;
		}
		return isIteminRange;
	}
	//returns the distance of the cop if he happens to be within a radius of 2 blocks
	public void find(){
		String totalDescription = "";
		int distX, distY;
		String directionX, directionY;
		distX = this.player.getX() - this.enemy.getX();
		distY = this.player.getY() - this.enemy.getY();
		directionX = distX < 0 ? "East" : "West";
		directionY = distY < 0 ? "South" : "North";
		
		if(distX < 2 && distY < 2)
			System.out.println("The Cop is " + Math.abs(distX) + " block(s) " + directionX + " and " + Math.abs(distY) + " block(s) " + directionY + " away from you!");
		
		for(int i = this.getPlayerX()-1; i <= this.getPlayerX()+1; i++){
			for(int j = this.getPlayerY()-1; j <= this.getPlayerY()+1; j++){
					totalDescription += (this.getMapLocation(i, j).getDescription() + "\n");
					totalDescription = totalDescription.replaceAll((this.getMapLocation(i, j).getDescription() + "\n"), "");
					//totalDescription += (this.getMapLocation(i, j).getDescription() + "\n");
			}
		}
		System.out.println(totalDescription);
	}
	
	public void changePlayerScore(int i){
		player.changeScoreBy(i);
	}
	public int getPlayerScore(){
		return this.player.getScore();
	}
	
	public boolean hasBigPuzzle(){
		return this.map.hasBPuzzle();
	}
	public BigPuzzle getBPuzzle(){
			return this.map.getBigPuzzle();
	}
	public void runPuzzle(){
		if(this.map.hasPuzzle())
			this.map.runPuzzle();
		else
			System.out.println("I can't apply that word here.");
	}
	
	public void playerThrow(String gadgetName){
		if(this.player.hasGadget(gadgetName)){
	
			this.map.addGadget(this.player.getGadget(gadgetName));
			System.out.println(this.map.getGadget(gadgetName).getName());
			this.map.getGadget().setLocation(this.getPlayerPosition());
			this.player.removeGadget(gadgetName);
			this.changeDescription();
		}
		else{
			System.out.println("You're not carrying any " + gadgetName);
		}
	}
	
	public void playerThrow(){
		Gadgets gad = this.player.getInventory().get(this.player.getInventory().size()-1);
		this.map.addGadget(gad);
		this.map.getGadget(gad.getName()).setLocation(this.getPlayerPosition());
		this.player.removeGadget(gad.getName());
	}
	
	public void hack(){
		if(this.map.hasBPuzzle())
				this.map.runBPuzzle();
		else
			System.out.println("There is no vulnerable system at this spot.");
	}

	public void setDoorUnlocked() {
		this.map.getBigPuzzle().setDoorUnlocked();
		
	}
	
	public void look(){
		if(this.getMapLocation(this.getPlayerX()-1, this.getPlayerY()).isBlock())
			System.out.println("There is a wall towards the west.");
		else if(this.getMapLocation(this.getPlayerX()+1, this.getPlayerY()).isBlock())
			System.out.println("There is a wall towards the east.");
		else if(this.getMapLocation(this.getPlayerX(), this.getPlayerY()+1).isBlock())
			System.out.println("There is a wall towards the south.");
		else if(this.getMapLocation(this.getPlayerX(), this.getPlayerY()-1).isBlock())
			System.out.println("There is a wall towards the north.");
	}
	
	public void help(){
		if(this.map.hasBPuzzle())
			this.map.getBigPuzzle().printDescription();
	}
	
	public void findVul(){
		if(this.map.hasBPuzzle()){
			this.map.getBigPuzzle().scanPuzzle();
		}
		else
			System.out.println("Scanner cannot find a vulnerablity");
	}
	
	public void scan(){
		String scanResult = null;
		if(this.map.hasBPuzzle()){
			scanResult = "There is a vulnerable system at this spot.";
		}
		else{
			for(int i = this.getPlayerX()-1; i <= this.player.getX()+1; i++){
				for(int j = this.getPlayerY()-1; j <= this.player.getY()+1; j++){
					if(this.map.hasBPuzzle(this.getMapLocation(i, j)))
						scanResult = "There is a Vulnerable System within a radius of 1 Block";
					else
						scanResult = "There is no open System in range";
						
				}
			}
		}
		System.out.println(scanResult);
	}
	
	public void printInv(){
		if(this.player.getCurrInv() == 0)
			System.out.println("You're not carrying anything!");
		else{
			System.out.println("You are carrying:");
			for (int i = 0; i < this.player.getInventory().size(); i ++){
				System.out.println("\t\t" + this.player.getInventory().get(i).getName());
			}
		}
	}
	
	
	public void scanHelp(){
		if(this.map.hasBPuzzle()){
			this.map.getBigPuzzle().printDescription();
		}
	}
	
	public void changeDescription(){
		this.map.buildDescription();
	}

	public void showDescription(){
		this.getPlayerPosition().printDescription();
	}
//get data from the file at the specified line.
	public static String[] getDataAt(BufferedReader file, int num) throws IOException {
		String temp = new String();
		for(int i = 1; i <= num; i++)
			temp = null;
			temp = file.readLine();
		String[] data = temp.split(",");
		return data;
	}
	
	public Cell getMapLocation(int i, int j){
		return this.map.getLocation(i, j);
	}
	
	public void saveC(BufferedWriter file) throws IOException{
		Player.save(file, this.player);
		Map.save(file, this.map);
		ChangeLog.save(file,this.command.getChangeLog());
	}
	
	public void restoreC(BufferedReader file) throws IOException{
		Player.restore(file, this.player, this.map);
		Map.restore(file, this.map);
		ChangeLog.restore(file,this.command.getChangeLog());
		
		this.changeDescription();
	}
	
	public void save() {

	    // Create a file for saving the game.
	    try {
	      BufferedWriter file = new BufferedWriter(new FileWriter(SAVE_NAME));
	      this.saveC(file);
	      file.close();
	      
	      System.out.println("Game Saved");
	    }
	    catch (IOException e) {
	     System.out.println("Failed to save game");
	    }
	  }
	
	public void restore() {

	    // Obtain the game save file.
	    try {
	    	BufferedReader file = new BufferedReader(new FileReader(SAVE_NAME));
			this.restoreC(file);
			file.close();
			System.out.println("Game Loaded Successfully.");
	    }
	    catch (IOException e) {
	    	System.out.println("Failed to Load Saved Game.");
	    }
	  }
	
}
