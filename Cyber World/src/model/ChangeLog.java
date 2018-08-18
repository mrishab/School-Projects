package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
//stores and retrieves the moves entered by using the back command
public class ChangeLog {
	int index;
	private ArrayList<String> changes;
	private Controller controller;
	private String command, reverseCommand;
	private Commands reverse;
	
	private static String[][] commandPairs = {
			{"north", "south"},
			{"south", "north"},
			{"east", "west"},
			{"west", "east"},
			{"pick", "rev-pick"},
			{"throw", "rev-throw"},
	};
	
	public ChangeLog(Controller controller){
		
		this.controller = controller;
		this.index = 0;
		this.changes = new ArrayList<String>();
		
	}
	
	public void addLog(String commandName){
		for(int i = 0; i < commandPairs.length; i++){
			if(commandName.equals(commandPairs[i][0])){
				this.changes.add(this.index, commandName);
				this.index++;
				break;
			}
		}
	}
	
	public void reverseCommand(){
		if(index > 0){
			for(int i = 0; i < commandPairs.length; i++){
				if(this.changes.get(index - 1).equals(commandPairs[i][0])){
					this.reverseCommand = commandPairs[i][1];
					break;
				}
			}
			
			this.reverse = new Commands(this.controller, this.reverseCommand);
			this.index--;
		}
		else
			System.out.print("There is no more back!");
	}
	
	public static void save(BufferedWriter file, ChangeLog changeLog) throws IOException{
		for(String change: changeLog.changes){
			file.write(change + ',');
		}
	}
	
	public static void restore(BufferedReader reader, ChangeLog changeLog) throws IOException{
		String[] data = Controller.getDataAt(reader, 6);
		for(String change: data)
			changeLog.addLog(change);
		
	}
}
