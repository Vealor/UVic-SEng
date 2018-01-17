/*
 * a2tester.java
 *
 * A test program for assignment 2.
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
 * J. Corless, January 2014
 */
public class a2tester
{
	public static int  	testCount = 0;
	public static boolean  	testArraySolution = false;
	public static int 	stressTestSize = 20000;


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
		boolean 	passed;
		IntegerList 	l = createNewList();
		
		System.out.println("Basic testing of size, addFront, addBack, get");
		displayResults (l.size() == 0);

		l.addFront(10);
		displayResults (l.size() == 1);

		l.addBack(9);
		displayResults (l.size() == 2);

		l.addFront(7);
		displayResults (l.size() == 3);

		displayResults (l.get(0) == 7);

		displayResults (l.get(1) == 10);

		displayResults (l.get(2)== 9);
	}

	public static void addArray (int[] a, IntegerList l, boolean addBack)
	{
		for (int i=0;i<a.length;i++)
		{
			if (addBack)
				l.addBack(a[i]);
			else
				l.addFront(a[i]);
		}
	}

	public static void testTwo()
	{
		int[]		a1 = {7,1,18};
		IntegerList	l1 = createNewList();
		IntegerList	l2 = createNewList();

		System.out.println("Testing toString() and clear");
		displayResults(l1.toString().equals("{}"));
		
		addArray(a1,l1,true);
		addArray(a1,l2,false);

		displayResults(l2.toString().equals("{18,1,7}"));
		displayResults(l1.toString().equals("{7,1,18}"));
		l1.clear();
		displayResults(l1.toString().equals("{}") && l1.size() == 0);
		l2.clear();
		displayResults(l2.toString().equals("{}") && l2.size() == 0);
	}

	public static void testThree()
	{
		IntegerList	l1 = createNewList();
		
		System.out.println("More add, remove, get testing");
		for (int i = -10;i <= 10; i++)
		{
			l1.addFront(i);
		}
		for (int i = 10;i >= -10; i--)
		{
			l1.remove(i);
		}
		displayResults(l1.size() == 0);

		l1.addFront(9);
		l1.addFront(8);
		l1.addBack(12);

		displayResults((l1.get(0) == 8) && (l1.get(1) == 9) && (l1.get(2) == 12) && (l1.size() == 3));
		l1.remove(9);
		displayResults((l1.get(0) == 8) && (l1.get(1) == 12) && l1.size() == 2);
		l1.addBack(13);
		l1.addFront(14);

		l1.remove(14);
		l1.remove(13);
		displayResults((l1.get(0) == 8) && (l1.get(1) == 12) && (l1.size() == 2));
		l1.remove(8);
		l1.remove(12);
		displayResults(l1.size() == 0);	

	}

	public static void testFour()
	{
		int[]		a1 = {1,2,3};
		IntegerList	l1 = createNewList();

		System.out.println ("Testing remove.");
		addArray(a1,l1,true);
		l1.remove(1);
		displayResults( (l1.size() == 2) && (l1.get(0) == 2) && (l1.get(1) == 3));
		l1.addFront(1);
		l1.addBack(3);
		l1.addFront(3);
		l1.remove(3);
		displayResults( (l1.size() == 2) && (l1.get(0) == 1) && (l1.get(1) == 2));

		IntegerList l2 = createNewList();

		for (int i = 0;i<10;i++)
		{
			l2.addBack(10);
		}
		displayResults(l2.size() == 10);
		l2.remove(10);
		displayResults(l2.size() == 0);	

	}

	public static void testFive()
	{	
		IntegerList 	l1 = createNewList();

		System.out.println("Stress test.");

		for (int i=0;i<stressTestSize;i++)
		{
			l1.addFront(i);
			l1.addBack(i);
		}

		displayResults(l1.size() == stressTestSize*2);

		for (int i =0;i<stressTestSize;i++)
		{
			if ( l1.get(i) != ((stressTestSize-1) - i) )
			{
				displayResults(false);
			}

			if ( l1.get(i+stressTestSize) != i )
			{
				displayResults(false);
			}
		}

		for (int i=0;i<stressTestSize;i++)
		{
			l1.remove(i);
		}
		displayResults(l1.size() == 0 );
	}

	public static IntegerList createNewList()
	{
		if (testArraySolution)
		{
			return new IntegerArrayList();
		}
		else
		{
			return new IntegerLinkedList();
		}
	}

	public static void main (String[] args)
	{
		if (args.length != 0 && args[0].equals("array"))
		{
			testArraySolution=true;
		}

		testOne();
		testTwo();
		testThree();
		testFour();
		testFive();
	}
}
