package items;
//class for building maps, and location of other objects
public class Cell {
	private int x, y;
	private boolean block;
	private String description;
	
	public Cell(int x, int y){
		this.block = false;
		this.setX(x);
		this.setY(y);
	}
	public Cell getCell(){
		return this;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public String printCell(){
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
	public void addDescription(String description){
		this.description += description + " ";
	}
	public void addDescription(Gadgets gadget){
		this.description += "There is a " + gadget.getName() + " at this spot.\n";
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public void remDescription(String remove){
		this.description.replaceAll(remove, "");
	}
	public void remDescription(Gadgets gadget){
		this.description.replaceAll("There is a " + gadget.getName() + " at this spot.", "");
	}
	public void printDescription(){
		System.out.println(this.description);
	}
	public String getDescription(){
		return this.description;
	}
	
	public void setBlock(boolean b){
		this.block = b;
	}
	
	public boolean isBlock(){
		return this.block;
	}
	
}
