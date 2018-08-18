package components;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
import items.BigPuzzle;
import items.Cell;
import items.FixedItems;
import items.Gadgets;
import items.Puzzle;
import model.UniqueRandom;
public class Map {
	
	private Controller controller;
	public static final int MAP_SIZE = 13;
	public final int numOfLocations = 4;
	private Cell[][] grid = new Cell[MAP_SIZE][MAP_SIZE];
	private Puzzle[] puzzle;
	private BigPuzzle[] bigPuzzles;
	private ArrayList <Gadgets> gadgets;
	private ArrayList <FixedItems> fixedItems;
	private String[] gadgetNames = {
			"Scanner","MAC-spoofer","Decryptor","Brute-forcer","Brute-forcer","Brute-forcer", "RFID"
	};

	private final int puzzleSize = gadgetNames.length;
	//Description list to assign information about four major areas of the map. Makes management easier instead of
	// spreading it throughout the class
	private static String[] descriptionList = {
			"\nYou are inside the Office's Reception Area.",
			"\nYou are inside the Boss' Office.",
			"\nYou are inside the Central Bank's Reception Area.",
			"\nYou are inside Central Bank's Cash Storage Area.",
			"\nYou are on the road.",
	};
	
	public Map(Controller controller){
		this.controller = controller;
		this.puzzle = new Puzzle[puzzleSize];
		this.bigPuzzles = new BigPuzzle[4];
		this.gadgets = new ArrayList<Gadgets>();
		this.fixedItems = new ArrayList<>();
		this.setGrid();
		this.buildMap();
		this.setPuzzlesAndGadgets();
		this.setBigPuzzles();
		this.buildDescription();
		
	}

	public Cell getLocation(int i, int j){
		return this.grid[i][j];
	}
	//initialises Cells
	private void setGrid(){
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				this.grid[i][j] = new Cell(i, j);
				this.grid[i][j].setDescription("This is Cell " + grid[i][j].printCell());
			}
		}
	}
	/*
	 * distributes gadgets and puzzles randomly over the map.
	 */
	public void setPuzzlesAndGadgets(){
		int randRow, randCol;
		UniqueRandom rand = new UniqueRandom(MAP_SIZE - 1);
		for(int i = 0; i < this.puzzleSize; i++){
			this.puzzle[i] = new Puzzle(this.controller);
			this.gadgets.add(new Gadgets(this.gadgetNames[i]));
			
			do{
			randCol = rand.nextInt();
			randRow = (int) (Math.random()*3) + 5;
			}
			while(this.grid[randRow][randCol].isBlock());
			
			this.puzzle[i].setLocation(this.grid[randRow][randCol]);
			this.gadgets.get(i).setLocation(this.grid[randRow][randCol]);		
		}
	}
	//accessor method for returning puzzle at current player cell
	public Puzzle getPuzzle(){
		Puzzle puzz = null;
		for(int k = 0; k < this.puzzleSize; k++){
			if(this.controller.getPlayerPosition() == this.puzzle[k].getLocation())
				puzz =  this.puzzle[k];
		}		
		return puzz;
	}
	//overloaded get puzzle method for getting puzzle at specified cell
	public Puzzle getPuzzle(Cell cell){
		Puzzle puzz = null;
		for(int k = 0; k < this.puzzleSize; k++){
			if(cell == this.puzzle[k].getLocation())
				puzz =  this.puzzle[k];
		}		
		return puzz;
	}
	//checks for if there is a puzzle at the current player position
	public boolean hasPuzzle(){
		boolean hasPuzzle = false;
		for(int k = 0; k < this.puzzleSize; k++){
			if(this.controller.getPlayerPosition() == this.puzzle[k].getLocation()){
				hasPuzzle = true;
				break;
			}
		}
		return hasPuzzle;
	}
	//overloaded method to check for puzzle at specified cell
	public boolean hasPuzzle(Cell cell){
		boolean hasPuzzle = false;
		for(int k = 0; k < this.puzzleSize; k++){
			if(this.puzzle[k].getLocation() == cell){
				hasPuzzle = true;
				break;
			}
		}
		return hasPuzzle;
	}
	//checks for presence of gadget ar specified cell
	public Gadgets gadgetHere(Cell cell){
		Gadgets gad = null;
		for(Gadgets gadget: this.gadgets){
			if(gadget.getLocation() == cell)
				return gadget;
		}
		return gad;
	}
	//executed the big puzzle when player arrives at the cell
	public void runBPuzzle(){
		for(BigPuzzle puzz: this.bigPuzzles){
			if(this.controller.getPlayerPosition() == puzz.getLocation()){
				if(!puzz.isSolved())
					puzz.puzzleEncounter();
				else
					System.out.println("You already have access to this location.");
				break;
			}
		}
	}

	//execute hackbox puzzles when player arrives at the cell
	public void runPuzzle(){
		for(Puzzle puzz: this.puzzle){
			if(this.controller.getPlayerPosition() == puzz.getLocation()){
				if(!puzz.isSolved())
					puzz.puzzleEncouter();
				else
					System.out.println("This is an unlocked Location");
				break;
			}
		}
	}
		
	//search for the the specified gadget at the players current position
	public boolean hasGadget(String name){
		boolean hasGadget = false;
		for(Gadgets gadget:this.gadgets){
			if(gadget.getName().equals(name) && gadget.getLocation() == this.controller.getPlayerPosition()){
				hasGadget = true;
			}//Multiple looped printing happening here.
		}
		return hasGadget;
	}
	
	//search for any gadget at the given cell
	public boolean hasGadget(Cell cell){
		boolean hasGadget = false;
		for(Gadgets gadget:this.gadgets){
			if(gadget.getLocation() == cell){
				hasGadget = true;
			}
		}
		return hasGadget;
	}
	
	
	public void removeGadget(Gadgets gadget){
				this.gadgets.remove(gadget);
			
	}
	
	//return gadget at the specified cell
	public Gadgets getGadget(Cell cell){
		Gadgets gad = new Gadgets("");
		for(Gadgets gadget:this.gadgets){
			if(gadget.getLocation() == cell){
				gad = gadget;
			}
		}
		return gad;
	}
	//return gadgets of specified name
	public Gadgets getGadget(String name){
		Gadgets gad = null;
		for(Gadgets gadget:this.gadgets){
			if(gadget.getName().equals(name)){
				gad = gadget;
			}
		}
		return gad;
	}
	
	//return the gadget that was last added to the map
	public Gadgets getGadget(){
		return this.gadgets.get(this.gadgets.size()-1);
	}
	//checks if the item is immovable
	public boolean isFixedItem(String name){
		boolean isFixedItem = false;
		for (FixedItems item: this.fixedItems){
			if(item.getName().equals(name)){
				isFixedItem = true;
				break;
			}
		}
		return isFixedItem;
	}
	
	public FixedItems getItem(String name){
		FixedItems fixed = null;
		for (FixedItems item: this.fixedItems){
			if(item.getName().equals(name)){
				fixed = item;
				break;
			}
		}
		return fixed;
	}

	//sets the boudaries, walls on the map and desciption for cells.
	public void buildMap(){
		int divMap = (MAP_SIZE-1)/3;
		int index = 0;
		for (int i = 0; i < MAP_SIZE; i++){
			this.grid[0][i].setBlock(true);
			this.grid[MAP_SIZE-1][i].setBlock(true);
			this.grid[i][0].setBlock(true);
			this.grid[i][MAP_SIZE-1].setBlock(true);
			
			this.grid[divMap][i].setBlock(true);
			this.grid[2*divMap][i].setBlock(true);
		}
		divMap = (MAP_SIZE - 1)/2;
		for(int j = 1; j <= 3; j++){
			this.grid[j][divMap].setBlock(true);
			this.grid[j+8][divMap].setBlock(true);
		}
		
		for(int k = 0; k <= 8; k+=8)
			for(int l = 0; l <= 3; l+=3){
				index++;
				for (int i = 1+k; i <= 3+k; i++)
					for(int j = 1+l; j <= 3+l ; j++)
						this.grid[i][j].addDescription(descriptionList[index]);
			}
	}
	
	//distributes big puzzles as well as the fixed items on predetermined cells.
	public void setBigPuzzles(){

		Cell[] unlockLocations = {
				this.getLocation(4,1),
				this.getLocation(2,(MAP_SIZE-1)/2),
				this.getLocation(8,11),
				this.getLocation(10,(MAP_SIZE-1)/2)
		};
		Cell[] bigPuzzleLocations = {
				this.getLocation(5,1),
				this.getLocation(2,(MAP_SIZE-1)/2 - 1),
				this.getLocation(7,11),
				this.getLocation(10,(MAP_SIZE-1)/2 + 1)	
		};
		Cell[]fixedItemLoc = {

				this.getLocation(3, 10),
				this.getLocation(2, 12),
				this.getLocation(1, 10),
				this.getLocation(2, 8),
				this.getLocation(12, 10),
				this.getLocation(11, 10),
				this.getLocation(9, 8),
				this.getLocation(9, 10),
				this.getLocation(1, 4),
				this.getLocation(9, 2),
		};
		this.bigPuzzles[0] = new BigPuzzle(this.controller,bigPuzzleLocations[0],unlockLocations[0], "brute");
		this.bigPuzzles[1] = new BigPuzzle(this.controller,bigPuzzleLocations[1],unlockLocations[1], "decryption");
		this.bigPuzzles[2] = new BigPuzzle(this.controller,bigPuzzleLocations[2],unlockLocations[2], "rfid");
		this.bigPuzzles[3] = new BigPuzzle(this.controller,bigPuzzleLocations[3],unlockLocations[3], "mspoof");

		this.fixedItems.add(new FixedItems("Desk", fixedItemLoc[0], false, this.controller));
		this.fixedItems.add(new FixedItems("Desktop", fixedItemLoc[1], true, this.controller));
		this.fixedItems.add(new FixedItems("Safe", fixedItemLoc[2],false, this.controller));
		this.fixedItems.add(new FixedItems("Chair", fixedItemLoc[3],false, this.controller));
		this.fixedItems.add(new FixedItems("Sofa", fixedItemLoc[4],false, this.controller));
		this.fixedItems.add(new FixedItems("Table", fixedItemLoc[5],false, this.controller));
		this.fixedItems.add(new FixedItems("ATM", fixedItemLoc[6],true, this.controller));
		this.fixedItems.add(new FixedItems("Lamp", fixedItemLoc[7],false, this.controller));
		this.fixedItems.add(new FixedItems("Closet", fixedItemLoc[8],false, this.controller));
		this.fixedItems.add(new FixedItems("Vault", fixedItemLoc[9],true, this.controller));
	}
	/*
	 * overloaded functions for checking the presence of puzzle on the map and accessing them.
	 */
	public boolean hasBPuzzle(){
		boolean hasPuzzle = false;
		for(BigPuzzle puzzle: this.bigPuzzles)
			if(puzzle.getLocation() == controller.getPlayerPosition())
				hasPuzzle = true;
		return hasPuzzle;
	}
	public boolean hasBPuzzle(Cell cell){
		boolean hasPuzzle = false;
		for(BigPuzzle puzzle: this.bigPuzzles)
			if(puzzle.getLocation() == cell)
				hasPuzzle = true;
		return hasPuzzle;
	}
	
	public BigPuzzle getBigPuzzle(){
		BigPuzzle puzz = null;
		for(BigPuzzle puzzle: this.bigPuzzles){
			if(this.controller.getPlayerPosition() == puzzle.getLocation()){
				puzz = puzzle;
				break;
			}
		}
		return puzz;
	}
	public BigPuzzle getBigPuzzle(int num){
		return this.bigPuzzles[num];
	}
	
	public void addGadget(Gadgets gadget){
		this.gadgets.add(gadget);
	}
	private Puzzle getPuzzle(int i){
		return this.puzzle[i];
	}
	
	public static void save(BufferedWriter file, Map map) throws IOException{
		for(int i = 0; i < map.puzzleSize; i++){
			file.write(Integer.toString(map.getPuzzle(i).getLocation().getX()) + ',');
			file.write(Integer.toString(map.getPuzzle(i).getLocation().getY()) + ',');
			file.write(Boolean.toString(map.getPuzzle(i).isSolved()) + ',');
		}
		file.newLine();
		
		for(int i = 0; i < map.gadgets.size(); i++){
			file.write(map.gadgets.get(i).getName() + ',');
			file.write(Integer.toString(map.gadgets.get(i).getLocation().getX()) + ',');
			file.write(Integer.toString(map.gadgets.get(i).getLocation().getY()) + ',');
		}
		file.newLine();
		
		for(int i = 0; i < map.bigPuzzles.length; i++){
			file.write(Boolean.toString(map.getBigPuzzle(i).isSolved()) + ',');
		}
		file.newLine();
		
	}
	
	public FixedItems getFixedItem(String name){
		FixedItems fixed = null;
		for(FixedItems item: this.fixedItems)
			if(item.getName().equals(name))
				fixed = item;
		return fixed;
	}
	
	public FixedItems getFixedItem(Cell cell){
		FixedItems fixed = null;
		for(FixedItems item: this.fixedItems)
			if(item.getLocation() == cell)
				fixed = item;
		return fixed;
		
	}
	public FixedItems getFixedItem(){
		FixedItems fixed = null;
		for(FixedItems item: this.fixedItems)
			if(item.getLocation() == controller.getPlayerPosition())
				fixed = item;
		return fixed;
	}
	
	public boolean hasFixedItem(Cell cell){
		boolean hasFixedItem = false;
		for(FixedItems item: this.fixedItems)
			if(item.getLocation() == cell)
				hasFixedItem = true;
		return hasFixedItem;
	}
	public boolean hasFixedItem(){
		boolean hasFixedItem = false;
		for(FixedItems item: this.fixedItems)
			if(item.getLocation() == controller.getPlayerPosition())
				hasFixedItem = true;
		return hasFixedItem;
	}
	public boolean hasFixedItem(String name){
		boolean hasFixedItem = false;
		for(FixedItems item: this.fixedItems)
			if(item.getName().equals(name))
				hasFixedItem = true;
		return hasFixedItem;
	}
	
	public static void restore(BufferedReader file, Map map) throws IOException{
		String data[] = Controller.getDataAt(file, 3);
		map.puzzle = new Puzzle[map.puzzleSize];
		map.gadgets = new ArrayList<Gadgets>();
		
		int dataIndex = 0;
		for(int i = 0; i < map.puzzleSize; i++){
			map.puzzle[i] = new Puzzle(map.controller);

			map.puzzle[i].setLocation(map.getLocation(Integer.parseInt(data[dataIndex++]),Integer.parseInt(data[dataIndex++])));
			map.puzzle[i].setSolved(Boolean.parseBoolean(data[dataIndex++]));
		}
		dataIndex = 0;
		data = Controller.getDataAt(file, 4);
		for(int i = 0; i < (data.length/3); i++){
			map.gadgets.add(new Gadgets(data[dataIndex++]));
			map.gadgets.get(i).setLocation(map.getLocation(Integer.parseInt(data[dataIndex++]), Integer.parseInt(data[dataIndex++])));
		}
		
		dataIndex = 0;
		data = Controller.getDataAt(file, 5);
		for(int i = 0; i < data.length; i++){
			map.bigPuzzles[i].setSolved(Boolean.parseBoolean(data[dataIndex++]));
			if(map.bigPuzzles[i].isSolved())
				map.bigPuzzles[i].setDoorUnlocked();
		}
	}
	//method to update the description of each cell each time a command is made. Used to assisst find and look command
	// to get information about the surroundings
	public void buildDescription(){
		int index = -1;
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j  < MAP_SIZE; j++){
				this.getLocation(i, j).setDescription("");
				if(this.hasBPuzzle(this.getLocation(i, j)))
					this.getLocation(i, j).setDescription("There is a vulnerable network in range ");
				if(this.hasPuzzle(this.getLocation(i, j)) && !this.getPuzzle(this.getLocation(i, j)).isSolved())
					this.getLocation(i, j).addDescription("\nThere is a HackBox at (" + i + ", " + j + ").\n" );
				if(this.hasPuzzle(this.getLocation(i, j)) && this.getPuzzle(this.getLocation(i, j)).isSolved())
					this.getLocation(i, j).remDescription(("\nThere is a HackBox at (" + i + ", " + j + ").\n"));
				if(this.hasGadget(this.getLocation(i, j)) && this.hasPuzzle(this.getLocation(i, j)))
					if(this.getPuzzle(this.getLocation(i, j)).isSolved())
						this.getLocation(i, j).addDescription(this.gadgetHere(this.getLocation(i, j)));
				
				if(this.hasGadget(this.getLocation(i, j)) && !this.hasPuzzle(this.getLocation(i, j)))
					for(int a = 0; a <this.gadgets.size(); a++)
						if(this.gadgets.get(a).getLocation() == this.getLocation(i, j))
							this.getLocation(i, j).addDescription(this.gadgets.get(a));
				if(this.hasFixedItem(this.getLocation(i, j))){
					this.getLocation(i, j).remDescription("\nThere is a " + this.getFixedItem(this.getLocation(i, j)).getName() + " at (" + i + ", " + j +").\n");
					this.getLocation(i, j).addDescription("\nThere is a " + this.getFixedItem(this.getLocation(i, j)).getName() + " at (" + i + ", " + j +").\n");
				}

				//if(!this.hasGadget(this.getLocation(i, j)) && this.getPuzzle(this.getLocation(i, j)).isSolved())
					//this.getLocation(i, j).remDescription(this.gadgetHere(this.getLocation(i, j)));
			}
		}
		for(int k = 0; k <= 8; k+=8)
			for(int l = 0; l <= (MAP_SIZE - 1)/2; l+=(MAP_SIZE - 1)/2){
				index++;
				for (int i = 1+k; i <= 3+k; i++)
					for(int j = 1+l; j <= 6+l ; j++){
						this.grid[i][j].remDescription(descriptionList[index]);
						this.grid[i][j].addDescription(descriptionList[index]);
					}

			}
		for(int i = 5; i < 8; i++)
			for(int j = 1; j < MAP_SIZE - 1; j++){
				this.grid[i][j].remDescription(descriptionList[4]);
				this.getLocation(i, j).addDescription(descriptionList[4]);
				if(i == 5 && j > 1){
					this.getLocation(i, j).remDescription("To your west is the Office Building.\nIt's entrance"
							+ " might be somewhere in the north.");
					this.getLocation(i, j).addDescription("To your west is the Office Building.\nIt's entrance"
							+ " might be somewhere in the north.");
				}
				if(i == 7 && j < 11){
					this.getLocation(i, j).remDescription("To your east is the Office Building.\nIt's entrance"
							+ " might be somewhere in the south.");
					this.getLocation(i, j).addDescription("To your east is the Office Building.\nIt's entrance"
							+ " might be somewhere in the south.");
				}
				if(j == 6 && i < 8 && i > 4){
					this.getLocation(i, j).remDescription("You've entered the NorthState Area. Far north is an entrance\n"
							+ "to a big corporate office building");
					this.getLocation(i, j).addDescription("You've entered the NorthState Area. Far north is an entrance\n"
							+ "to a big corporate office building");
				}
				if(j == 7 && i < 8 && i > 4){
					this.getLocation(i, j).remDescription("You've entered the SouthState Area. Far south is an entrance\n"
							+ "to a Banking Instituion");
					this.getLocation(i, j).addDescription("You've entered the SouthState Area. Far south is an entrance\n"
							+ "to a Banking Instituion");
				}
			}
		this.buildStory();
	}
	//set the description of certain special locations exclusively.
		private void buildStory(){

			Cell[] unlockLocations = {
					this.getLocation(4,1),
					this.getLocation(2,(MAP_SIZE-1)/2),
					this.getLocation(8,11),
					this.getLocation(10,(MAP_SIZE-1)/2)
			};
			Cell[] bigPuzzleLocations = {
					this.getLocation(5,1),
					this.getLocation(2,(MAP_SIZE-1)/2 - 1),
					this.getLocation(7,11),
					this.getLocation(10,(MAP_SIZE-1)/2 + 1)	
			};

			unlockLocations[0].addDescription("You're standing at the entrance of the Office building."
					+ "\nIn front is the Reception Area. There is a closet inside.\nFrom the reception area, there is a"
					+ "locked door that lead to your ex boss' cabin.");
			unlockLocations[1].addDescription("You're standing at the entrance of the Kendall's office. He is currently not there\n"
					+ "There is a vault, desk, chair and a dekstop in the room. It might be useful to hands on his PC.");
			unlockLocations[2].addDescription("You're standing at the entrance of Central Bank's reception area. The bank is closed\n"
					+ "at this time of the day. Maybe, we can sneak in and explore the bank's secret areas. There is furniture\n"
					+ "in the room. Make your way through it to the secret entrance that goes to the bank's vault.");
			unlockLocations[3].addDescription("You're standing at the entrance of the bank's room. There is vault in the room.\n"
					+ "Maybe we can try and access it. Hardly anyone visits this area. They might be liberal with setting a\n"
					+ "strong password for the vault.");

				bigPuzzleLocations[0].addDescription("To your west is the entrance to the Office building, locked by a password\n"
						+ "protection system. Exploit the system to gain access to the building.");
				bigPuzzleLocations[1].addDescription("To your south is the entrance to Kendall's office. The door is locked and\n"
						+ "displays some sort of random alphabetical message. Hack into the system to gain acces to his cabin.");
				bigPuzzleLocations[2].addDescription("To your east is the entrance to the Bank building. The entrance can be gained\n"
						+ "by using an RFID card at the entrance.");
				bigPuzzleLocations[3].addDescription("To your north is an entrance to some unknown location in bank. It seems to\n"
						+ "hidden from general public. It might be interesting to find out what's behind in there.");
		}


}
