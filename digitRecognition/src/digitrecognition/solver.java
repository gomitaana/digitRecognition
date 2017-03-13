package digitrecognition;

/**
 *
 * @author Raul
 */

import java.util.*;

public class solver {
	/*static int sudoku[][] = { 
			{ 2, 0, 0, 8, 0, 4, 0, 0, 6 }, 
			{ 0, 0, 6, 0, 0, 0, 5, 0, 0 }, 
			{ 0, 7, 4, 0, 0, 0, 9, 2, 0 }, 
			{ 3, 0, 0, 0, 4, 0, 0, 0, 7 }, 
			{ 0, 0, 0, 3, 0, 5, 0, 0, 0 }, 
			{ 4, 0, 0, 0, 6, 0, 0, 0, 9 }, 
			{ 0, 1, 9, 0, 0, 0, 7, 4, 0 }, 
			{ 0, 0, 8, 0, 0, 0, 2, 0, 0 }, 
			{ 5, 0, 0, 6, 0, 8, 0, 0, 1 } };*/
	
	/*static int sudoku[][] = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 1, 6, 4, 8, 2, 7 }, 
			{ 0, 0, 0, 3, 8, 7, 5, 9, 1 }, 
			{ 4, 1, 2, 8, 9, 6, 7, 3, 5 }, 
			{ 5, 7, 3, 2, 4, 1, 6, 8, 9 }, 
			{ 8, 9, 6, 7, 5, 3, 1, 4, 2 }, 
			{ 9, 3, 8, 4, 1, 5, 2, 7, 6 }, 
			{ 1, 4, 7, 6, 3, 2, 9, 5, 8 }, 
			{ 2, 6, 5, 9, 7, 8, 4, 1, 3 } };*/
			
	/*static int sudoku[][] = { 
			{ 0, 0, 1, 6, 3, 0, 0, 9, 0 }, 
			{ 9, 0, 0, 0, 0, 0, 4, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 8, 1, 0 }, 
			{ 0, 0, 8, 0, 0, 2, 3, 6, 0 }, 
			{ 0, 0, 0, 0, 0, 6, 0, 0, 0 }, 
			{ 0, 2, 5, 8, 0, 0, 0, 0, 0 }, 
			{ 2, 0, 0, 0, 9, 7, 0, 4, 0 }, 
			{ 0, 4, 9, 0, 0, 0, 5, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 8, 0, 0, 0 } };*/
	
	static int sudoku[][] = { 
			{ 7, 0, 1, 0, 2, 5, 0, 4, 0 }, 
			{ 0, 0, 5, 6, 1, 0, 2, 0, 0 }, 
			{ 6, 0, 0, 0, 9, 0, 0, 0, 0 }, 
			{ 0, 7, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 2, 0, 0, 0, 0, 3, 0 }, 
			{ 0, 0, 0, 0, 0, 8, 0, 0, 0 }, 
			{ 0, 0, 7, 0, 0, 0, 3, 9, 0 }, 
			{ 1, 0, 0, 9, 5, 0, 0, 0, 4 }, 
			{ 0, 0, 9, 0, 3, 2, 0, 1, 7 } };
	
	/*static int sudoku[][] = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };*/
	
	public List<Integer> initializeValues(node Node){
		List<Integer> values = new ArrayList<Integer>();
		//Check  row
		for (int r = 0; r < 9; r++) {
			if(sudoku[Node.row][r] > 0){
				values.add(sudoku[Node.row][r]);
			}	
		}
		
		//Check column
		for(int c = 0; c < 9; c++){
			if(sudoku[c][Node.col] > 0){
				values.add(sudoku[c][Node.col]);
			}
		}
		
		//Check inner box
		int xi = (Node.row /3)*3;
		int yi = (Node.col /3)*3;
		int xf = xi + 2;
		int yf = yi + 2;
		
		for(int x = xi; x <= xf; x++){
			for(int y = yi; y <= yf; y++){
				if(sudoku[x][y] > 0){
					values.add(sudoku[x][y]);
				}
			}
		}
		return values;
	}
	
	public boolean resolveSudoku(){
		
		node[][] mx_nodes = this.getMatrix();
		List<node> l_nodes= this.getPossibleN(mx_nodes);
		
		//If the list of nodes is empty we have reached the end
		if(l_nodes.isEmpty()){
			return true;
		}
		
		//Initialize Values
		for(int i =0; i<l_nodes.size(); i++){
			List<Integer> l_possibles = this.initializeValues(l_nodes.get(i));
			boolean update = l_nodes.get(i).updateValues(l_possibles);
			if(update == false){
				return false;
			}
		}
		
		//Sort based in the possible attribute and Get first node
		Collections.sort(l_nodes);
		node actual = l_nodes.get(0);
		//System.out.println("Actual: "+actual.row + " " + actual.col);
		
		//Get possible values of actual node
		List<Integer> n_possibles = actual.possibleValues();
		//System.out.println("Valores: "+ n_possibles);
		
		int i=0;
		while(i < n_possibles.size()){
			sudoku[actual.row][actual.col] = n_possibles.get(i);
			
			//Recursion for the rest of the sudoku
			boolean next = this.resolveSudoku();
			if(next == true){
				return true;
			}else{
				sudoku[actual.row][actual.col] = 0;
				i++;
			}
			
			
		}
		return false;
		
	}
	
	public List<node> getPossibleN(node[][] m_nodes){
		List<node> p_nodes = new ArrayList<node>();
		for(int i =0; i< m_nodes.length; i++){
			for(int j =0; j< m_nodes[i].length; j++){
				if(m_nodes[i][j] != null){
					p_nodes.add(m_nodes[i][j]);
				}
			}
		}
		return p_nodes;
	}
	
	public node[][] getMatrix(){
		node[][] mNodes = new node[9][9];
		for(int i =0; i< sudoku.length; i++){
			for(int j =0; j< sudoku[i].length; j++){
				if(sudoku[i][j] == 0){
					node n_node = new node(i, j);
					//List<Integer> possible =this.initializeValues(n_node);
					//boolean update = n_node.updateValues(possible);
					mNodes[i][j] = n_node;
				}else{
					mNodes[i][j] = null;
				}
			}
		}
		return mNodes;
	}
	
	public static void main(String[] args) {
		// this function is called just once, and it call the InitializeValues function for each node
		//It initialize the matrix of nodes, and the array of nodes we will be iterating
		
		//Once initialized execute the algorithm
		//1. order based in possible values (less to more)
		//2. assign value for first node, and update the rest
		//3. repeat steps for next node
		//Constraints: if in the update, one node or more have no more possible answers return to previous node
		
		for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++){
                if(sudoku[row][col]==0){
                    System.out.print("| ");
                }else{
                    System.out.print("|"+sudoku[row][col]);
                }
            }
           System.out.print("|");
           System.out.println();
 }
        System.out.println();
        System.out.println();
		
		boolean resolve = new solver().resolveSudoku();
		if(resolve){
			System.out.println("Resolved: ");
			
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++){
					System.out.print("|"+sudoku[row][col]);
				}
                                System.out.print("|");
				System.out.println();
		 	}
		}else{
			System.out.println("The sudoku cannot be resolved");
		}
	}
}
