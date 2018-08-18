package items;

import java.util.Scanner;

import controller.Controller;
import model.UniqueRandom;

public class Puzzle extends Items {
	protected Controller controller;
	private boolean solved;
	protected boolean play = false;
	private String puzzle, correctAnswer;
	private int ans;
	protected static Scanner scan = new Scanner(System.in);
	private String[] options;
	private static String[][] puzzleList = {
			{"What is a Linux ", " OS", "App", "Script", "Weapon"},
			{"Scanning is performed in which phase of a pen test?", "Pre-attack", "Attack","Post-attack", "Reconnaissance"},
			{"What will an open port return from an ACK scan?", "RST", "SYN/ACK", "FIN", "Nothing"},
			{"What is the preferred communications method used with systems on a bot-net?", "IRC", "E-mail", "ICMP", "TFTP"},
			{"Which of the following best describes a distributed denial-of-service attack?", "A DoS carried out by multiple systems", "A DoS against similar systems in different target networks", "A DoS against multiple systems across an enterprise network", "A DoS against an entire subnet, affecting multiple systems"},
			{"What is the attack called “evil twin”?", "Rogue access point", "MAC spoofing", "ARP poisoning", "Session hijacking"},
			{"What is another term for turning off the SSID broadcast?", "SSID cloaking","SSID stealth", " SSID unicast", "SSID Secv"},
			{"What is the maximum length of an SSID?", "Thirty-two characters", "Sixteen characters", "Sixty-four characters", "Eight characters"},
			{"Which wireless mode connects machines directly to one another, without the use of an access point?", "Ad hoc", "Infrastructure", "Point to point", "BSS"},
			{"Forensic ToolKit (FTK) is a ?", "Software", "Hardware", "Both Hardware as well as Software", "None"},
			{"EXPLOITS DEPEND ON ", "ALL OF THE ABOVE", "LAN", "SOFTWARE CONFIGURATION ", "OS AND THEIR CONFIGURATION"},
			{"FAT stands for", "File allocation tables", "Forensic analysis tool", "File allocation transfer ", "Format allocation test "},
			{"Registry key can be altered directly with ? ", "Registry Editor", "Control Panel", "Key Editor", "Control Editor "},
			{"CDMA stand for ","Code division multiple access", "Code data multiple access ", "None of the above ", "Code data management authority ", ""},
			{"At which layer of the OSI communication model dose bridge operate?", "Datalink", "Transport", "Network", "Physical "},
			{"Forensic Examiners are interested in following file because these contain portions of all documents and other materials a user produce while using the computer ? ", "Swap ", "Temporary ", "Slack ", "Unallocated "},
			{"Power-On Passwords are function of ", "Computer Hardware ", "Operating System Software ", "Application Software ", "Peripherals "},
			{"Digital certificates are used in the IPSec connection, What type of network infrastructure device issue digital certificate? ", "CA ", "SA", "SECP ", "ACS "},
			
	};
	
	public Puzzle(Controller controller){
		this.controller = controller;
		this.options = new String[4];
		this.solved = false;
		this.setPuzzle();
	}
	
	public void puzzleEncouter(){
		String choice;
		System.out.println("Hi mate. Up for a challenge, again?(Y/N)");
		choice = scan.next();
	
		if (choice.toLowerCase().equals("yes") || choice.toLowerCase().equals("y")){
			this.play = true;
			System.out.println("You can quit if you want to come back later.");
			while(!this.isSolved() && this.play){
				this.setPuzzle();
				this.displayPuzzle();
				this.getAns();
			}
		}
	}
	
	public void setPuzzle(){
		UniqueRandom rand = new UniqueRandom(puzzleList.length);
		int col = rand.nextInt();
		this.puzzle = puzzleList[col][0];
		this.correctAnswer = puzzleList[col][1];
		this.makeMcq(col);
	}
	
	public void makeMcq(int pNum){
		int col, pSize = puzzleList[0].length;
		UniqueRandom rand = new UniqueRandom(pSize-1);
		for (int i = 0; i < (pSize - 1); i++){
			col = rand.nextInt() + 1;
			this.options[i] = puzzleList[pNum][col];
		}
		
	}
	public void displayPuzzle(){
		for(int i = 0; i < 30; i++)
			System.out.print('*');
		System.out.println("\n" + this.puzzle);
		System.out.println("\nChoose the Correct Option:");
		for(int i = 0; i < 2; i++){
			
			System.out.printf(String.format("%-30s", (1+ i) + " " + this.options[i]));
			System.out.printf(String.format("%-30s", (3+ i) + " " + this.options[i+2]));
			System.out.println("");
		}
	}
	
	public void getAns(){
		
		String reply = scan.next();
		if(reply.toLowerCase().equals("quit") || reply.toLowerCase().equals("q")){
			System.out.println("Be Careful. This brings the COP close");
			this.controller.moveEnemyNear();
			this.controller.changePlayerScore(-20);
			this.play = false;
		}
		
		else{
			do{
			try{
				this.ans = Integer.parseInt(reply);
				
			}
			catch (NumberFormatException e){
				System.out.println("Please make a valid choice: ");
				reply = scan.nextLine();
				
			}
			}
			while(this.ans > this.options.length || this.ans <= 0 && this.controller.isPlayerAlive());
			
			if(this.options[this.ans-1] == this.correctAnswer){
				System.out.println("Location Unlocked");
				this.controller.changePlayerScore(150);
				this.solved = true;
				this.controller.changeDescription();
			}
			else{
				//bring enemy closer somehow.
				this.controller.moveEnemyNear();
				this.controller.changePlayerScore(-10);
				System.out.println("Oops! That's not the correct answer!");
				this.setPuzzle();
				this.displayPuzzle();
				this.getAns();
			}
		}
		
		
	}
	
	public void setSolved(boolean b){
		this.solved = b;
	}
	
	
	public boolean isSolved(){
		return this.solved;
	}
}