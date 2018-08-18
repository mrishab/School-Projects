package items;

import controller.Controller;

public class FixedItems extends Items{
	final int MAX_ATTEMPTS = 3;
	private int attempts;
	private boolean usable;
	private boolean fixed;
	private String display, access, accessScreen, errorScreen, accessCode;

	//display screen when accessing a fixed object
	private static String[] useList = {
			"---------------------------\n||\t  Login    \t||\n||\t\t\t||\n||\t_________\t||\n---"
			+ "------------------------",
			"---------------------------\n||\t   Welcome      ||\n||\tUser: Kendall\t||\n|| MAC-3"
			+ "E:DF:68:B2:AK:RM||\n---------------------------\n Making a note of this information might be"
			+ "\n in getting access to the hidden money!.",
			"-----------------------------\n|| Authentication Required ||\n||\t \t\t   ||\n||   "
			+ "Enter Admin Password  ||\n-----------------------------",
			"-----------------------------\n||         Welcome         ||\n||      User:  Kendall "
			+ "    ||\n||      Access Granted     ||\n-----------------------------\n\nMoney $2.3 billion",
			
	};
	private static String[] errorList = {
			"--------------------------\n||\t  ERROR    \t||\n||\t\t\t||\n||\t!!!!!!!!!!\t||\n--------------------------",
			"-----------------------------\n||\t \t\t   ||\n||  Authentication Failed  ||\n||\t \t  \t   ||\n-----------------------------",
			
	};
	
	
	public FixedItems(String name, Cell cell, boolean b, Controller controller){
		this.controller = controller;
		this.name = name;
		this.location = cell;
		this.fixed = true;
		this.usable = b;
		this.attempts = 0;
		this.itemDefine();
		
	}
	
	public boolean isFixed(){
		return this.fixed;
	}

	public boolean isUsable(){
		return this.usable;
	}
	//sets the object state and behaviour on the basis of their name
	private void itemDefine(){
		switch(this.name){
		case "Desktop":
			this.display = useList[0];
			this.accessScreen = useList[1];
			this.errorScreen = errorList[0];
			this.access = "12345";
			break;
		case "Vault":
			this.display = useList[2];
			this.accessScreen = useList[3];
			this.errorScreen = errorList[1];
			this.access = "admin".toLowerCase();
			break;
		}
	}
//executes when a player tries ot interact with a fixed item
	public void access(){
		int remAttempts = MAX_ATTEMPTS - this.attempts;
		if(this.usable){
			if(this.attempts < MAX_ATTEMPTS){
				System.out.println(this.display);
				do{
					this.attempts++;
					this.accessCode = scan.next();
					
					if(this.accessCode.equals(this.access)){
						System.out.println(this.accessScreen);
						if(this.name.equals("Vault")){
							System.out.println("Congratulations. You've completed all the missions.\n\n");
							controller.gameOver();
						}
						break;
					}
					else if(this.accessCode.toLowerCase().equals("quit"))
						break;
					
					else{
						if(this.attempts == MAX_ATTEMPTS)
							System.out.println("This is your LAST CHANCE!");
						System.out.println("Attempts remaining: " + remAttempts);
						System.out.println(this.errorScreen);
					}
				}
				while(this.attempts < MAX_ATTEMPTS);
			}
			
				
			
		}
		
		else
			System.out.println("I can't access a " + this.name);
	}
}
