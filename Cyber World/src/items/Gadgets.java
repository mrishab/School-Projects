package items;

import controller.Controller;

public class Gadgets extends Items{
	private int timesUsable;
	private String description, parameter;
	//describes gadget functions
	private static String[] descriptionList = {
			"MAC-Spoofer is a tool you can use to spoof an already known MAC-Address. It is an effective tool in a Impersonating another individual",
			"There can be many vulnerabilities in a system. A Scanner can help you identify one and help make you make a choice on how to exploit it.",
			"Every secret message is encrypted with a secret key. A Decryptor can help you identify that key.",
			"RFID Cards are data coded cards and mainly used for the purpose of identification by swiping them along an device.",
			"When nothing else works, use a brute forcer to randomly think of a password. It will test every password",
	};
	
	public Gadgets(String name){
		this.name = name;
		this.gadgetDefine();
		
	}

	//executes when a gadget is used
	public void use(Controller controller){

			System.out.println("Please enter the parameter for " + this.name);
			this.parameter = scan.next();
			if(this.timesUsable > 0){
			this.timesUsable--;
			if(!this.name.equals("Scanner")){
				//checks the parameter for the gadgets, except Scanner gadget
				switch (this.parameter){
				case "help":
					System.out.println(this.description);
					break;
				case "find":
					this.gadgetHelp(controller);
					break;
				default:
					if(this.parameter.equals(controller.getBPuzzle().getVulner())){
						System.out.println("\n\n\t\tACCESS GRANTED");
						controller.getBPuzzle().setSolved(true);
						controller.changeDescription();
						controller.setDoorUnlocked();
					}
					else{
						System.out.println("\n\n\t\tACCESS DENIED");
						this.use(controller);
					}
				}
			}
			else{
				//checks the parameter for the Scanner Gadget
				switch (this.parameter){
				case "find":
					controller.findVul();
					break;
				case "scan":
					controller.scan();
					break;
				case "help":
					controller.scanHelp();
					break;
					
				default:
						System.out.println("Invalid Parameter");
				}
			}
		}	
		else
			System.out.println("You have exhausted your " + this.name);
	}
		//set gadget object state on the basis of their names
	public void gadgetDefine(){
		switch(this.getName()){
		
		case "MAC-spoofer":
			this.description = descriptionList[0];
			this.timesUsable = 4;
			break;
		case "Scanner":
			this.description = descriptionList[1];
			this.timesUsable = 4;
			break;
		case "Decryptor":
			this.description = descriptionList[2];
			this.timesUsable = 3;
			break;
		case "RFID":
			this.description = descriptionList[3];
			this.timesUsable = 2;
			break;
		
		case "Brute-forcer":
			this.description = descriptionList[4];
			this.timesUsable = 4;
			break;
		
		}	
	}	
	//method to find vulnerability of the big puzzle
	public void gadgetHelp(Controller controller){
		if(controller.hasBigPuzzle()){
			
			String gadgetReq = controller.getBPuzzle().getGadgetReq().getName();
			
			if(gadgetReq.equals(this.name)){
				switch(this.name){
					case "Brute-forcer":
							controller.getBPuzzle().getGadgetReq().getName();
							System.out.println(this.name + " has determined that the initial character of the access code is \'" + 
							controller.getBPuzzle().getVulner().substring(0, 1) + "\' and the it's length is " + 
							controller.getBPuzzle().getVulner().length() + " characters." );
						
						break;
						
					case "MAC-spoofer":
							System.out.println(this.name + " has determined that the initial character of the access code is \'" + 
							controller.getBPuzzle().getVulner().substring(0, 1) + "\' and has " + 
							controller.getBPuzzle().getVulner().length() + " HEX characters." );
							break;
							
					case "Decryptor":
							System.out.println(this.name + " has determined that Caesar Cipher has shift value less than 10");
						break;
						
					case "RFID":
							System.out.println(this.name + " can only be swiped on this machine");
				}
			}
			else
				System.out.println("This gadget is not compatible with the System in range");
			
		}
		else
			System.out.println("There is no System to retrieve data.");
	}

}
