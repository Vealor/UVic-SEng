/* CountInversions.java
   CSC 225 - Summer 2014
   Assignment 2 - Template for inversion counting
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java CountInversions
	
   To conveniently test the algorithm with a large input, create a 
   text file containing space-separated integer values and run the program with
	java CountInversions file.txt
   where file.txt is replaced by the name of the text file.

   B. Bird - 05/23/2014
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

//Do not change the name of the CountInversions class
public class CountInversions{
	/* CountInversions()
		Count and return the number of inversions in the input array A.
		
		The function may modify the input array A.

		Do not change the function signature (name/parameters).
	*/
	public static int CountInversions(int[] A){
		
		/* ... Your code here ... */	
		return 0;
	}

	/* main()
	   Contains code to test the CountInversions function. Nothing in this function 
	   will be marked. You are free to change the provided code to test your 
	   implementation, but only the contents of the CountInversions() function above 
	   will be considered during marking.
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
		
		int inversionCount = CountInversions(array);
		
		long endTime = System.currentTimeMillis();
		
		double totalTimeSeconds = (endTime-startTime)/1000.0;
		
		System.out.printf("Number of inversions: %d\n",inversionCount);
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}