/* RangeSum.java
   CSC 226 - Fall 2014
   
   Template for the RangeSum data structure, which provides an interface
   to obtain the partial sums of ranges of elements in a collection
   of integers.
   
   The input for this problem is a sequence of values S and two indices i & j.
   After the data structure is initialized with the values in S, a query
   with indices i & j will return the sum of the i^th smallest through j^th 
   smallest values in S. The data structure is designed with the expectation
   that for a given sequence S, a large number of queries will be made, so the
   query step should be as fast as possible.
   
   For example, if the input sequence is
     2 9 4 1 6 10
   then the result of a query on indices (1,4) is
     2 + 4 + 6 + 9 = 21.
   
   The provided implementation uses a O(n log n) initialization step and a
   O(n) query step. The optimal implementation has a O(n log n) initialization
   step and a O(1) query step.

   B. Bird - 08/13/2014
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

public class RangeSum{

	
	/* Class Member Data */
	int[] sortedSequence;
	long[] partialSums;
	
	
	//Initialize the RangeSum structure with the given collection of values.
	public RangeSum(int[] values){
		sortedSequence = values.clone();
		Arrays.sort(sortedSequence);
		int n = values.length;
		partialSums = new long[n];
		partialSums[0] = sortedSequence[0];
		for(int i=1;i<n;i++){
			partialSums[i] = partialSums[i-1] + sortedSequence[i];
		}
	}
	
	//Compute the sum of indices rangeStart through rangeEnd (inclusive) in the sorted
	//representation of the values array passed into the constructor above.
	public long sum(int rangeStart, int rangeEnd){
		long s = 0;
		for (int i = rangeStart; i <= rangeEnd; i++){
			s += sortedSequence[i];
		}
		return s;
	}



	/* main()
	   Contains code to test the functions above.
	*/
	public static void main(String[] args){
		Scanner s;
		boolean interactiveMode = false;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			interactiveMode = true;
			s = new Scanner(System.in);
		}
		
		
		if (interactiveMode)
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		else
			System.out.printf("Reading input data from %s.\n",args[0]);
		
		
		Vector<Integer> inputVector = new Vector<Integer>();
		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);
		
		int[] inputValues = new int[inputVector.size()];
		
		for (int i = 0; i < inputValues.length; i++)
			inputValues[i] = inputVector.get(i);
			
		System.out.printf("Read %d values.\n",inputValues.length);
		

		long startTime, endTime;
		
		startTime = System.currentTimeMillis();
		
		RangeSum R = new RangeSum(inputValues);
		
		endTime = System.currentTimeMillis();
		double initializeTimeSeconds = (endTime-startTime)/1000.0;
		System.out.printf("Initialization Time (seconds): %.4f\n",initializeTimeSeconds);
		
		
		double totalQueryTimeSeconds = 0.0;
		
		while(true){
			if (interactiveMode)
				System.out.printf("Enter values for rangeStart and rangeEnd in the range [0, %d]: ", inputValues.length-1);
			
			int rangeStart, rangeEnd;
			if(!s.hasNextInt())
				break;
			if((rangeStart = s.nextInt()) < 0){
				System.out.printf("Invalid rangeStart value.\n");
				break;
			}
			
			if(!s.hasNextInt())
				break;
			if((rangeEnd = s.nextInt()) < 0){
				System.out.printf("Invalid rangeEnd value.\n");
				break;
			}
			
			if (rangeStart > rangeEnd || rangeEnd >= inputValues.length || rangeStart < 0){
				System.out.printf("Range values are out of range.\n");
				break;
			}
			
			
			startTime = System.currentTimeMillis();
			
			long sum = R.sum(rangeStart,rangeEnd);
			
			endTime = System.currentTimeMillis();
			double queryTimeSeconds = (endTime-startTime)/1000.0;
			totalQueryTimeSeconds += queryTimeSeconds;
		
			String startSuffix = (rangeStart%10 == 1)?"st":(rangeStart%10 == 2)?"nd":"th";
			String endSuffix = (rangeEnd%10 == 1)?"st":(rangeEnd%10 == 2)?"nd":"th";
			System.out.printf("Sum of %d%s smallest to %d%s smallest values: %d\n",rangeStart,startSuffix,rangeEnd,endSuffix,sum);
			System.out.printf("Query Time (seconds): %.4f\n",queryTimeSeconds);
		}
		System.out.printf("Total Query Time (seconds): %.4f\n",totalQueryTimeSeconds);
	}
}
