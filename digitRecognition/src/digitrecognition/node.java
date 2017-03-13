package digitrecognition;
/**
 *
 * @author Raul
 */
import java.util.*;

public class node implements Comparable<node> {
	//Dimensions of the node
	int row;
	int col;
	int values[]={0,0,0,0,0,0,0,0,0};
	int total_posible; 
	
	public node(int row, int col){
		this.row = row;
		this.col = col;
		this.total_posible = 9;
	}
	
	public void printNode(){
		System.out.println("Position: " + row + ", " + col);
	}
	
	public boolean updateValues(List<Integer> value_asg){
		//First reset all values
		for(int i =0; i<this.values.length; i++){
			this.values[i]=0;
		}
		//Assign 1 to the values the node cannot use
		for(int i =0; i < value_asg.size(); i++ ){
			this.values[value_asg.get(i)-1] = 1;
		}
		
		//Update total_possible
		this.total_posible =0;
		for(int i =0; i<this.values.length; i++){
			if(this.values[i]== 0){
				this.total_posible += 1;
			}
		}
		
		//check if it has possible values
		for(int i =0; i<this.values.length; i++){
			if(this.values[i] == 0){
				return true;
			}
		}
		return false;
	}
	
	public void resetValues(){
		for(int i =0; i<this.values.length; i++){
			this.values[i]=0;
		}
	}
	
	public int[] getValues(){
		return this.values;
	}
	
	public int compareTo(node N)
	{
	     return(this.total_posible - N.total_posible);
	}
	
	public List<Integer> possibleValues(){
		List<Integer> possible = new ArrayList<Integer>();
		for(int i =0; i <this.values.length; i++){
			if(this.values[i] == 0){
				possible.add(i+1);
			}
		}
		//System.out.println("Possible:"+possible);
		return possible;
	}

	public boolean updateValue(int value){
		this.values[value-1]=1;
		
		for(int i =0; i<this.values.length; i++){
			if(this.values[i]==0){
				return true;
			}
		}
		
		this.values[value-1]=0;
		return false;
	}

	public void printValues(){
		for(int i = 0; i< this.values.length; i++){
			System.out.print(this.values[i]);
		}
		System.out.println("");
	}
}