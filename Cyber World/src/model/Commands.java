package model;

import java.util.Scanner;

import controller.Controller;

public class Commands {
	//takes string input as command and executes the instructions
	private Scanner input = new Scanner(System.in);
	private ChangeLog changeLog;
	private String command, parameter;
	
	public Commands(Controller controller){
		this.changeLog = new ChangeLog(controller);
		this.parameter = new String();
	}
	
	public Commands(Controller controller, String commandName){
		this.parameter = new String();
		this.command = commandName;
		this.function(controller);
		System.out.println(controller.getPlayerPosition().printCell());
		
	}
	public ChangeLog getChangeLog(){
		return this.changeLog;
	}
	public void takeCommand(){
		this.command = input.next();
		this.changeLog.addLog(this.command);
		}
	public String getCommandName(){
		return this.command;
	}

	public void function(Controller controller){
		switch(this.command.toLowerCase()){
		case "west":
			controller.setPlayerPosition(controller.getPlayerX()-1, controller.getPlayerY());
			break;
		case "east":
			controller.setPlayerPosition(controller.getPlayerX()+1, controller.getPlayerY());
			break;
		case "north":
			controller.setPlayerPosition(controller.getPlayerX(), controller.getPlayerY()-1);
			break;
		case "south":
			controller.setPlayerPosition(controller.getPlayerX(), controller.getPlayerY()+1);
			break;

		case "move":
			System.out.println("Move where? (Specify Direction)");
			this.takeCommand();
			break;
		
		case "pick":

			this.parameter = input.next();
				controller.pick(this.parameter);			
			break;
		case "access":
			this.parameter = input.next();
			controller.useFixedItem(this.parameter);
			break;
		case "back":
			this.changeLog.reverseCommand();
			controller.changeDescription();
			break;
		
		case "look":
			controller.look();
			controller.getPlayerPosition().printDescription();
			break;
			
		case "inventory":
			controller.printInv();
			break;
			
		case "open":
		case "hackbox":
			controller.runPuzzle();
			break;

		case "use":
			String gadget;
			gadget = input.next();
			gadget = gadget.substring(0, 1).toUpperCase() + gadget.substring(1);
			controller.useGadget(gadget);
			break;
		case "find":
			controller.find();
			controller.changePlayerScore(-5);
			break;
		case "score":
			System.out.println("*****Current Score*****\n\n\t   " + controller.getPlayerScore() + 
					"\n\n***********************");
			break;
		case "throw":
			this.parameter = this.input.next();
			controller.playerThrow(this.parameter);
			break;
		case "hack":
			controller.hack();
			break;
		case "save":
			controller.save();
			break;
		case "restore":
			controller.restore();
			break;
		case "rev-throw":
			controller.gadgetPick(controller.getPlayerPosition());
			
			break;
		case "rev-pick":
			controller.playerThrow();
			break;
			
		case "help":
			System.out.println("What would you need help with?:\n command, solution, fixeditems, hackbox, "
					+ "gadgets, brute-forcer, mac-spoofer, rfid, decryptor");
			this.parameter = input.next();
			System.out.println(help(this.parameter));
			break;
		
		default:
			System.out.println("Invalid Command");	
		}	
	}
	
	private String help(String parameter){
		String help = "";
		switch(parameter.toLowerCase()){
		case "gadgets":
		case "gadget":
			help = "Gadgets are the tools that you can collect by going around the City. They are hidden in hack boxes\n"
					+ " which need to be unlocked by answering the questions. Once you unlock the hackbox, enter \"pick\"\n"
					+ " command followed by [gadget name] (case-sensitive) to pick the gadget. Since, you cannot carry all\n"
					+ "gadgets you can throw the ones you don't need. You can always come back later\n\n"
					+ "A gadget can be used by running a \"use\" command followed by [gadget name]. All gadgets can take \n"
					+ "three parameters: \n\n"
					+ "\"help\": Gives brief information about the gadget.\n"
					+ "\"find\": Helps in finding vulnerability in a system\n"
					+ "\"[value]\": This is the parameter you put in as a value to hack a system\n";
			break;
		case "brute-forcer":
			help =  "Brute-forcer: Brute Forcer can be used to gain unauthorised entry into a system with weak password\n"
					+ "configurations. The real effort to guess or figuring out the password lies with you. A brute-forcer \n"
					+ "gives you the privilege to atleast attempt to hack into the system";
			break;
		case "mac-spoofer":
			help = "You can use a MAC-spoofer to impersonate someone by knowing their MAC-address. MAC-address is a \n"
					+ "HEX formatted 12 character (like 3E:DR:FR:RM:12:A2) unique code of a device, like PC. \n";
			break;
		case"rfid":
			help = "RFID are magnetic cards which can be used for gaining entry into a system which gets unlock by swiping\n"
					+ "RFID cards on them.";
			break;
		case "hackbox":
			help = "Hackboxes are mystery boxes which are spread over the City road. They can be located next to each other\n"
					+ " or sometimes at the extreme corners. Whenever you encounter one, initiate \"open\" command to unlock\n"
					+ "them. Keep your attempts to minimum as each failed attempt brings the cops closer to you.";
			break;
		case "decryptor":
			help = "Decryptor can be used to break a cipher code by inputting the correct decrypted code. When you encounter an\n"
					+ "encrypted message, you can try to manually decrypt it based on the type of it's encryption and then use the\n"
					+ "decrypted value as parameter in decryptor to find if you were correct.";
			break;
		case "commands":
		case "command":
			help = "Movement: North, South, East, West\nInteraction: Pick/Throw: Pick/Throw a pickable object\n\n"
					+ "Access: Access a fixed usable item.\nLook: explore your current position.\n\n"
					+ "Find: Explore the surroundings in a radius of one block.\n\n"
					+ "Use: followed by [gadget name] to use that gadget.\n\n"
					+ "Hack: Hack into any open vulnerable system in range.\n\n"
					+ "Open: open a hackbox at the current position\n\n"
					+ "Save/Restore: Save/Restore the game at any point.\n\n"
					+ "Score/Inventory: View score/inventory\n\n";
			break;
		case "solution":
			help = "Vulnerability: Whenever you are in range of a vulnerable network, run hack command to see the system. Once\n"
					+ "you are in the hack mode, use the Scanner with parameter \"help\" and \"find\" to detect the type of vulnerability\n"
					+ "Then use the appropriate gadget to get a hint to find that vulnerability. After you've solved the problem. Use the\n"
					+ "gadget with your developed solution as parameter to break into the system.\n\n"
					+ "Hackboxes: Use open command to attemp to unlock the Hackboxes. After unlocking them, use the Look command to see which gadget\n"
					+ "is there underneath it and then pick that gadget to continue with your mission."; 
			break;
						
		case "fixeditem":
		case "fixeditems":
			help = "There are various immovable items like Sofa, Desk, Closet, Table, etc that you will encounter inside\n"
					+ "the buildings. You cannot pick, move or walk over them. You need to make your way out through them."
					+ "Some of the Fixed items like Desktop or a Vault can be accessed by using an \"access\" command.\n";
			break;
			default:
				help = "No information about that. Please make a choice from above options.";
		}
		return help;
		
	}
}
