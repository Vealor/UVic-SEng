/* NetworkFlow.java
   CSC 226 - Fall 2014
   Assignment 4 - Max. Flow Template
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java NetworkFlow
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java NetworkFlow file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of directed graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the capacity of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   For network flow computation, the 'source' vertex will always be vertex 0 
   and the 'sink' vertex will always be vertex 1.
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 07/05/2014
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the NetworkFlow class
public class NetworkFlow{

	/* MaxFlow(G)
	   Given an adjacency matrix describing the structure of a graph and the 
	   capacities of its edges, return a matrix containing a maximum flow from
	   vertex 0 to vertex 1 of G.
	   In the returned matrix, the value of entry i,j should be the total flow
	   across the edge (i,j).
	*/
	static int[][] MaxFlow(int[][] G){
		int numVerts = G.length;
		int[][] flowNet = new int[numVerts][numVerts];
		boolean[] visited = new boolean[numVerts];
		while (dfs(G,flowNet,0,1, visited, Integer.MAX_VALUE)>0) {
			for (int i=0; i < numVerts; i++) 
				visited[i] = false;
		}
		
		for(int i=0;i<numVerts;i++){
			for(int j=0;j<numVerts;j++){
				if(flowNet[i][j] < 0){
					flowNet[i][j] = 0;
				}
			}
		}
		
		return flowNet;
		
	}
	private static int dfs(int[][] G, int[][] F, int s, int t, boolean[] visited, int min){
		int len = G.length;
		if (s==t) {
			return min;
		}
		visited[s] = true;
		for (int j=0; j < len; j++) {
			if (G[s][j] > 0 && !visited[j]) {
				int result = dfs(G, F, j, t, visited, getMin(min, G[s][j]));
				if (result > 0) {
					F[s][j] = F[s][j]+result;
					F[j][s] = F[j][s]-result;
					G[s][j] = G[s][j]-result;
					G[j][s] = G[j][s]+result;
					return result;
				}
			}
		}
		return 0;
	}
	
	private static int getMin(int m, int n){
		if(m<n){
			n=m;
		}
		return n;
	}
	
	
	/*--------------------------------------------------------------------*/
	public static boolean verifyFlow(int[][] G, int[][] flow){
		
		int n = G.length;
		
		//Test that the flow on each edge is less than its capacity.
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (flow[i][j] < 0 || flow[i][j] > G[i][j]){
					System.err.printf("ERROR: Flow from vertex %d to %d is out of bounds.\n",i,j);
					return false;
				}
			}
		}
		
		//Test that flow is conserved.
		int sourceOutput = 0;
		int sinkInput = 0;
		for (int j = 0; j < n; j++)
			sourceOutput += flow[0][j];
		for (int i = 0; i < n; i++)
			sinkInput += flow[i][1];
		
		if (sourceOutput != sinkInput){
			System.err.printf("ERROR: Flow leaving vertex 0 (%d) does not match flow entering vertex 1 (%d).\n",sourceOutput,sinkInput);
			return false;
		}
		
		for (int i = 2; i < n; i++){
			int totalIn = 0, totalOut = 0;
			for (int j = 0; j < n; j++){
				totalIn += flow[j][i];
				totalOut += flow[i][j];
			}
			if (totalOut != totalIn){
				System.err.printf("ERROR: Flow is not conserved for vertex %d (input = %d, output = %d).\n",i,totalIn,totalOut);
				return false;
			}
		}
		return true;
	}
	
	public static int totalFlowValue(int[][] flow){
		int n = flow.length;
		int sourceOutput = 0;
		for (int j = 0; j < n; j++)
			sourceOutput += flow[0][j];
		return sourceOutput;
	}
	
	/* main()
	   Contains code to test the MaxFlow function. You may modify the
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
			
			int[][] G2 = new int[n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					G2[i][j] = G[i][j];
			int[][] flow = MaxFlow(G2);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			if (flow == null || !verifyFlow(G,flow)){
				System.out.printf("Graph %d: Flow is invalid.\n",graphNum);
			}else{
				int value = totalFlowValue(flow);
				System.out.printf("Graph %d: Max Flow Value is %d\n",graphNum,value);
			}
				
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}
