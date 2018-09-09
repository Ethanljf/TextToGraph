/*Ethan Foss 
  December 12th, 2017
  This program stores values from a given text file into a graph represented by a 2D array.
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
public class A5{
	public static void main(String[] args)throws IOException{

		Scanner kb = new Scanner(System.in);
		System.out.println("Enter the file name you wish to read from:");
		String name = kb.next();

		File file = new File(name);
		Scanner inputFile = new Scanner(file);

		boolean first = true;
		int[][] adjMatrix = new int[1][1];
		int edges = 0;

		//Stores the appropriate values into the adjacency matrix based on the values in the text file
		while(inputFile.hasNext()){
			if(first==true){
				first = false;
				edges = Integer.parseInt(inputFile.nextLine());
				adjMatrix = new int[edges][edges];
			} else {
				String vert1 = inputFile.next();
				String vert2 = inputFile.next();
				int x = vert1.charAt(0)-65;
				int y = vert2.charAt(0)-65;
				adjMatrix[x][y] = 1;
				adjMatrix[y][x] = 1;
			}
		}
		inputFile.close();

		//Displays all data in the Adjacency Matrix
		System.out.println("Adjacency Matrix\n");
		System.out.print(" ");

		for(int i=1; i<=edges; i++){
			System.out.print(" " + i);
		}

		for(int i=1; i<=edges; i++){
			for(int z=1; z<=edges; z++){
				if(z==1){
					System.out.println("");
					System.out.print(i + " ");
					System.out.print(adjMatrix[z-1][i-1] + " ");
				} else {
					System.out.print(adjMatrix[z-1][i-1] + " ");
				}
			}
		}

		//Prompts the user to select a search method
		System.out.println("\nWould you like to traverse the graph by depth first or breadth first search (D/B)?");

		String choice = kb.next().toLowerCase();

		//If the user selected a breadth-first search
		if(choice.equals("b")){
			ArrayList<Integer> queue = new ArrayList<Integer>();
			ArrayList<Integer> result = new ArrayList<Integer>();
			queue.add(1);

			//While the queue is not empty, dequeue a vertex from the queue to the result list and add all of the dequeued vertices neighbours to the queue
			while(!queue.isEmpty()){
				result.add(queue.get(0));
				queue.remove(0);
				for(int i=0; i<edges; i++){
					if(adjMatrix[result.get(result.size()-1)-1][i] == 1){
						if(!result.contains(i+1) && !queue.contains(i+1)){
							queue.add(i+1);
						}
					}
				}
			}
			//Print the result
			System.out.println(result);

		//If the user selected a depth-first search
		} else if(choice.equals("d")){
			ArrayList<Integer> result = new ArrayList<Integer>();
			result.add(1);
			boolean deadEnd = true;
			int cur = result.size()-1;
			int decrement = 1;

			//While not all vertices are in the result list
			while(result.size()<edges){

				//Add a neighbour of the previously added vertex to the list
				for(int i=0; i<edges; i++){
					if(adjMatrix[result.get(cur)-1][i] == 1){
						if(!result.contains(i+1)){
							result.add(i+1);
							i=edges;
							deadEnd = false;
							cur = result.size()-1;
						}
					}
					//If a dead end has been reached, move to the previous vertex
					if(i==edges && deadEnd == true){
						decrement++;
						cur = result.size()-decrement;
					}
				}

				//Add the final vertex to the list
				if(result.size() == edges-1){
					for(int i=1; i<=edges; i++){
						if(!result.contains(i)){
							result.add(i);
						}
					}
				}
			}
			//Print the result
			System.out.println(result);
		}

	}
}