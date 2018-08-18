package components;

import controller.Controller;
import items.Items;
//just for creating an enemy object
public class Person extends Items{
	protected Controller controller;
	
	public Person(Controller controller) {
		this.controller = controller;
	}
}
