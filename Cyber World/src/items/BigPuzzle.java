package items;
import controller.Controller;

public class BigPuzzle extends Puzzle{
	
	private int count;
	private final int MAX_ATTEMPTS = 2;
	public final static int SIZE = 4;
	private String type, vulnerability, description, screen;

	private Cell unlockLocation;
	private Gadgets gadgetReq;
	//solution to the big puzzles on the map
	private String[] vulnerList = {
			"password",
			"3E:DF:68:B2:AK:RM",
			"cyberworld",
			"swipe"
	};
	
	public void scanPuzzle(){
		System.out.println("The system requires a ");
		switch(this.type){
		case "brute":
			System.out.print("password");
			break;
		case "mspoof": 
			System.out.print("unique device ID.");
			break;
		case "decryption":
			System.out.print("a decrypted string input");
			break;
		case "rfid":
			System.out.print("an RFID card.");
			break;
		}
	}

	private static String[] descriptionList = {
			"The entrance to the building requires authorisation. Such systems tend to have weak administration passwords,"
			+ " often left unchanged since installation. What could be the default password of a device?",
			
			"The vault only access password from an authorised device."
			+ " Wouldn't it be easy if you could fake your device's identify."
			+ " Furthermore, what information would be used by the system to establish the uniqueness of the device"
			+ "in the firstplace?",
			
			"This seems to be some sort of Caesar Cipher. It is a form of encryption in which every alphabet is moved"
			+ "by a certain fixed number of characters.",
			
			"The entrance to bank is authorized by swiping magnetic cards. Do you have any card?",
	};
	private static String[] display = {
			"\n\nAuthorisation Required\n---------------------\n|  Enter Password   |\n|   _____________   |\n---------------------",
			"-----------------------------\n|| Authentication Required ||\n||\t \t\t   ||\n||\t Connect Device\t   ||\n-----------------------------",
			"\n\nAuthorisation Required\n---------------------\n| Hint:  hdgjwbtwqi |\n|   _____________   |\n---------------------",
			"\n\nAuthorisation Required\n---------------------\n|    Swipe Card     |\n|   _____________   |\n---------------------",
	};
	
	public BigPuzzle(Controller controller, Cell cell, Cell unlock, String type){
		super(controller);
		this.count = 0;
		this.type = type;
		this.location = cell;
		this.unlockLocation = unlock;
		this.puzzleDefine();
		
	}
	public void increaseCount(){
		this.count++;
	}
	public int getCount(){
		return this.count;
	}
	
	public void puzzleEncounter(){
		String choice;
		System.out.println("You have " + (MAX_ATTEMPTS - this.count) + " safe attempts remaining.\n"
				+ "for this system. Make sure you've all the required tools.\n"
				+ "Are you sure about breaching this system? ");
		if(this.count >= MAX_ATTEMPTS){
			System.out.println("You can still go for it. But doing so will enable cops to find you even faster!");
		}
		choice = scan.next();
		choice = choice.toLowerCase();
		if(choice.equals("y") || choice.equals("yes")){
			System.out.println("\n\n\tRunning Exploit...");
			this.count++;
			this.play = true;
		}
		
		if(this.count >= MAX_ATTEMPTS){
			for(int i = 0; i < Math.abs((MAX_ATTEMPTS-this.count)); i++)
					this.controller.moveEnemyNear();
			}
			
		while(this.play){
			System.out.println(this.screen);
			System.out.println("Use the appropriate tool to hack into this system.");
			break;
			/*
			 * testing reasons for break
			 */
		}
	}
	
	public int getRemainingAttempts(){
		return MAX_ATTEMPTS - this.count;
	}

	public Gadgets getGadgetReq(){
		return this.gadgetReq;
	}
	public String getVulner(){
		return this.vulnerability;
	}
	
	//sets the values of puzzle object according to the type of the puzzle.
	public void puzzleDefine(){
		switch(this.type){
		
		case "brute":
			this.vulnerability = vulnerList[0];
			this.description = descriptionList[0];
			this.gadgetReq = new Gadgets("Brute-forcer");
			this.screen = display[0];
			
			break;
			
		case "mspoof":
			this.vulnerability = vulnerList[1];
			this.description = descriptionList[1];
			this.gadgetReq = new Gadgets("MAC-spoofer");
			this.screen = display[1];
			
			break;
			
		case "decryption":
			this.vulnerability = vulnerList[2];
			this.description = descriptionList[2];
			this.gadgetReq = new Gadgets("Decryptor");
			this.screen = display[2];
			break;

		case "rfid":
			this.description = descriptionList[3];
			this.vulnerability = vulnerList[3];
			this.gadgetReq = new Gadgets("RFID");
			this.screen = display[3];
			break;
		}
	}
	public void setDoorUnlocked() {
		this.unlockLocation.setBlock(false);
	}
	public void printDescription() {
		System.out.println(this.description);
	}

}
