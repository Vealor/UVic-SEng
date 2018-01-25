/* PairSum.java
   CSC 225 - Summer 2014
   
   Template for the PairSum225 problem, which takes an array A and returns
	- true if there are two elements of A which add to 225
	- false otherwise
   
   The provided code for the problem (in the PairSum225 function below) implements
   a O(n^2) algorithm. A simple O( n*log(n) ) algorithm also exists, and the optimal
   algorithm is O(n).

   B. Bird - 04/30/2014
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

public class PairSum{
	/* PairSum225()
		The input array A will contain non-negative integers. The function
		will search the array A for a pair of elements which sum to 225.
		If such a pair is found, return true. Otherwise, return false.
		The function may modify the array A.
		Do not change the function signature (name/parameters).
	*/
	public static boolean PairSum225(int[] A){
		
		int n = A.length;

		//Use nested loops to check every pair of values in A
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (A[i] + A[j] == 225)
					return true;
			}
		}
		
		return false;
	}

	/* main()
	   Contains code to test the PairSum225 function. 
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
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();
		
		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);
		
		int[] array = new int[inputVector.size()];
		
		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);
		
		
		long startTime = System.currentTimeMillis();
		
		boolean pairExists = PairSum225(array);
		
		long endTime = System.currentTimeMillis();
		
		double totalTimeSeconds = (endTime-startTime)/1000.0;
		
		System.out.printf("Array %s a pair of values which add to 225.\n",pairExists? "contains":"does not contain");
		System.out.printf("Total Time (seconds): %.4f\n",totalTimeSeconds);
	}
}
