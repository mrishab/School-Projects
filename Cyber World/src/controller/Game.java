package controller;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

	private Controller controller;
	private Scanner scan = new Scanner(System.in);
	private int choice;
	
	String screen2 = "\n\n\t\t\tRishab Manocha\n\t\t\t300.139.589\n\t\t\t"
			+ "COMP155 - AB1\n\n\t   Submitted to: Professor Paul Rushton";	
	
	public Game(){
		this.controller = new Controller();
	}
	
	public void start(){
		System.out.println("\t\t************Welcome to the CYBERWORLD************\n\n\n");
		
		System.out.println("1. New Game\t 2. About the Programmer");
		this.inputChoice();
		
		switch(this.choice){
		case 1:
			this.controller.newGame();
			break;
		case 2:
			System.out.println(screen2);
			break;
	/*	case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		case 6:
			
			break;*/
		}
		
	}
	
	public void inputChoice() throws InputMismatchException {
		
		do {
		    try {
		    	System.out.println("\n\nPlease enter the choice (1 - 2): ");
		    	this.choice = scan.nextInt();
		    } 
		    catch (InputMismatchException e) {
		        System.out.print("Invalid number ");
		    }
		    
		    scan.nextLine(); // clears the buffer
		} while(this.choice < 1 || this.choice > 6);
	}
	
}
