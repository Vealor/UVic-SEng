/*
 * a6tester.java
 *
 * A test program for assignment 6.
 *
 * While this program includes many test cases, it was still developed in an ad-hoc
 * manner.  In situations where the code under test is required to work correctly
 * at all times, more care would need to be taken to ensure the test cases 
 * provided good code coverage and that all 'edge cases' were, in fact,
 * tested.
 *
 * When your code has failed one of the test cases, you should look at the what the
 * tester is doing prior to failing the test.  Try to determine which of your methods
 * has the bug in it.  Just because the tester is reporting that you failed the result
 * of a particular method (i.e. get) doesn't mean the bug is in the get method.
 *
 * J. Corless, March 2014
 */

import java.util.Random;

public class a6tester
{
	public static int  	testCount = 0;
	public static boolean  	testHeapSolution = true;

	public static void displayResults (boolean passed)
	{
		/* There is some magic going on here getting the line number 
		 * Borrowed from:
		 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
		 *
		 * Once we've discussed Exceptions in more detail this won't be required.		 
		*/
		if (passed)
		{
			System.out.println ("Passed test: " + testCount);
		}
		else
		{
			System.out.println ("Failed test: " + testCount + " at line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.exit(1);
		}
		testCount++;
	}

	
	public static void testOne ()
	{
		PriorityQueue 	q = createNewPriorityQueue();
		
		System.out.println("Basic testing of size, isEmpty");
		displayResults (q.size() == 0);
		
		displayResults (q.isEmpty());

		q.insert(10);
		displayResults (q.size() == 1);
		displayResults (!q.isEmpty());
		
		q.insert(9);
		displayResults (q.size() == 2);

		q.insert(7);
		displayResults (q.size() == 3);

	}

	public static void addArray (int[] a, PriorityQueue q)
	{
		for (int i=0;i<a.length;i++)
		{
			q.insert(a[i]);
		}
	}

	public static void testTwo()
	{
		PriorityQueue q = createNewPriorityQueue();
		
		System.out.println("Basic testing of removeMin");
		q.insert(1);
		q.insert(2);
		q.insert(3);

		displayResults(((Integer)q.removeMin()).intValue() == 1);
		displayResults(q.size() == 2);
		displayResults(((Integer)q.removeMin()).intValue() == 2);
		displayResults(q.size() == 1);
		displayResults(((Integer)q.removeMin()).intValue() == 3);
		displayResults(q.isEmpty());

		q = createNewPriorityQueue();
		q.insert(3);
		q.insert(2);
		q.insert(1);

		displayResults(((Integer)q.removeMin()).intValue() == 1);
		displayResults(q.size() == 2);
		displayResults(((Integer)q.removeMin()).intValue() == 2);
		displayResults(q.size() == 1);
		displayResults(((Integer)q.removeMin()).intValue() == 3);
		displayResults(q.isEmpty());
		
	}

	public static void testThree()
	{
		PriorityQueue q = createNewPriorityQueue();
		
		System.out.println("Testing of removeMin with Strings");
		q.insert("abc");
		q.insert("def");
		q.insert("ghi");

		displayResults(((String)q.removeMin()).equals("abc"));
		displayResults(q.size() == 2);
		displayResults(((String)q.removeMin()).equals("def"));
		displayResults(q.size() == 1);
		displayResults(((String)q.removeMin()).equals("ghi"));
		displayResults(q.isEmpty());

		q = createNewPriorityQueue();
		q.insert("ghi");
		q.insert("def");
		q.insert("abc");

		displayResults(((String)q.removeMin()).equals("abc"));
		displayResults(q.size() == 2);
		displayResults(((String)q.removeMin()).equals("def"));
		displayResults(q.size() == 1);
		displayResults(((String)q.removeMin()).equals("ghi"));
		displayResults(q.isEmpty());

	}

	public static void testFour()
	{
		PriorityQueue q = createNewPriorityQueue();
		
		System.out.println("Testing duplicates.");
		q.insert(2);
		q.insert(5);
		q.insert(5);
		q.insert(10);
		q.insert(1);

		displayResults(((Integer)q.removeMin()).intValue() == 1);
		q.insert(2);
		q.insert(1);
		q.insert(2);
		q.insert(5);
		q.insert(1);
		q.insert(5);
		q.insert(10);

		displayResults(((Integer)q.removeMin()).intValue() == 1);
		displayResults(((Integer)q.removeMin()).intValue() == 1);

		displayResults(((Integer)q.removeMin()).intValue() == 2);
		displayResults(((Integer)q.removeMin()).intValue() == 2);
		displayResults(((Integer)q.removeMin()).intValue() == 2);

		displayResults(((Integer)q.removeMin()).intValue() == 5);
		displayResults(((Integer)q.removeMin()).intValue() == 5);
		displayResults(((Integer)q.removeMin()).intValue() == 5);
		displayResults(((Integer)q.removeMin()).intValue() == 5);
		

		displayResults(((Integer)q.removeMin()).intValue() == 10);
		displayResults(((Integer)q.removeMin()).intValue() == 10);

		displayResults(q.isEmpty());
	}


	public static void testFive()
	{	
		PriorityQueue q = createNewPriorityQueue();
		System.out.println("Testing insert mixed with removeMin.");
	
		q.insert(10);
		q.insert(5);
		q.insert(7);
		q.insert(2);
		
		displayResults(((Integer)q.removeMin()).intValue() == 2);
		q.insert(4);
		displayResults(((Integer)q.removeMin()).intValue() == 4);
		q.insert(100);
		q.insert(9);
		q.insert(11);
		displayResults(((Integer)q.removeMin()).intValue() == 5);
		displayResults(((Integer)q.removeMin()).intValue() == 7);

		displayResults(((Integer)q.removeMin()).intValue() == 9);
		q.insert(6);
		displayResults(((Integer)q.removeMin()).intValue() == 6);		
		displayResults(q.size() == 3);	
	}

	public static boolean testRandomArray (int count)
	{
		// These tests are effectively sorting the 
		// random values
		//
		// For the heap, this is O (n log n)
		// For the linked list, this is O (n^2)
		PriorityQueue q = createNewPriorityQueue(count);

		System.out.println("Testing size: " + count);
		Random r = new Random();

		for ( int i = 0; i < count; i++ )
		{
			int val = r.nextInt(1000000);
			q.insert (val);
		}
		
		int oldVal = -1;
		
		while (!q.isEmpty() )
		{
			int val = (int)((Integer)q.removeMin()).intValue(); // or a bug
			
			if ( oldVal > val )
				return false;
			oldVal = val;
		}
		return true;

	}

	public static void stressTest()
	{
		System.out.println("Stress Tests.");
		displayResults(testRandomArray(100));
		displayResults(testRandomArray(10000));
		displayResults(testRandomArray(100000));
	
		// This takes too long using the linked list.
		if (testHeapSolution)
			displayResults(testRandomArray(1000000));
	}

	public static PriorityQueue createNewPriorityQueue()
	{
		if (testHeapSolution)
		{
			return new HeapPriorityQueue();
		}
		else
		{
			return new LinkedPriorityQueue();
		}
	}

	public static PriorityQueue createNewPriorityQueue(int size)
	{
		if (testHeapSolution)
		{
			return new HeapPriorityQueue(size);
		}
		else
		{
			return new LinkedPriorityQueue();
		}
	}

	public static void main (String[] args)
	{
		if (args.length != 0 && args[0].equals("linked"))
		{
			testHeapSolution=false;
		}

		System.out.println("Testing " + (testHeapSolution ? "Heap" : "Linked" ) + " implementation.");
		testOne();
		testTwo();
		testThree();
		testFour();
		testFive();
		stressTest();
	}
}
