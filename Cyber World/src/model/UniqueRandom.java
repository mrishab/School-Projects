package model;

import java.util.ArrayList;
import java.util.Collections;
// to avoid duplicate random numbers.
public class UniqueRandom{
	private ArrayList <Integer> list;
	
	public UniqueRandom(int num){
		this.list = new ArrayList<Integer>();
		this.list.clear();
		
    	for (int i = 0; i < num; i++){
    		this.list.add(new Integer(i));
    		}
	}
	
    public int nextInt(){
    	int temp;
        Collections.shuffle(this.list);
        temp = this.list.get(0);
        this.list.remove(0);
        return temp;
     }
}
