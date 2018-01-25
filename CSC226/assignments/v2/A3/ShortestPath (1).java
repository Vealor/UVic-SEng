/* ShortestPath.java
   CSC 226 - Fall 2014
   Assignment 3 - Template for Dijkstra's Algorithm
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPath
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the ShortestPath class
public class ShortestPath{

	/* ShortestPath(G)
		Given an adjacency matrix for graph G, return the total weight
		of a minimum weight path from vertex 0 to vertex 1.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	
	static int ShortestPath(int[][] G){
		int numVerts = G.length;
		int totalWeight = 0;
		int curNode = 0;
		boolean[] visited = new boolean[numVerts];
		boolean noloop = false;
		
		/*
		//use visited and the heap array to determine the smallest next
		//unvisited node then use that in the while loop
		//if it finishes with -1 then we know everything is visited
		scan the array for the linking node
		
		to find the next weight required
		use the weight of the current node + the weight of the distance
		to the adjacent nodes and check if they improve or get worse
		*/
		
		VertexHeap theVert = new VertexHeap(numVerts);
		theVert.heap[1].weight = 0;
		while(curNode >= 0){
			//PUT IN VALUES OF NEXT NODES
			//go through all possible links
			for(int i = 0; i <numVerts ;i++){
				//if a link exists
				if(G[curNode][i] >0){
					//check what vert in heap is correct one
					for(int j = 1;j<=numVerts;j++){
						if(theVert.heap[j].v == i){
							for(int k = 1;k<=numVerts;k++){
								if(theVert.heap[k].v == curNode){
									if(G[curNode][i] + theVert.heap[k].weight < theVert.heap[j].weight){
										theVert.adjust(i,G[curNode][i] + theVert.heap[k].weight);
									}
								}
							}
						}
					}
				}
			}
			visited[curNode] = true;
			//GET NEXT NODE
			//if finish with -1 then all nodes visited
			int minNode = -1;
			for(int i = 1; i<=numVerts;i++){
				if(visited[theVert.heap[i].v] == false && theVert.heap[i].weight != Integer.MAX_VALUE){
					if(minNode < 0 || 
					   theVert.heap[i].weight<theVert.heap[minNode].weight){
						minNode = i;
					}
				}
			}
			if(minNode !=-1){
				curNode = theVert.heap[minNode].v;
			}else{
				curNode = minNode;
			}
		}
		for(int i = 1;i<=numVerts;i++){
			if(theVert.heap[i].v == 1){
				totalWeight = theVert.heap[i].weight;
			}
		}
		return totalWeight;
	}
		
	/* main()
	   Contains code to test the ShortestPath function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			int totalWeight = ShortestPath(G);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}