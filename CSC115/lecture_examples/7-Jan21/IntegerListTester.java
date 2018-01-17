/*
 * IntegerListTester.java
 *
 */
public class IntegerListTester
{
	public static int  testCount = 0;

	public static void displayResults (boolean passed)
	{
		/* There is some magic going on here getting the line number 
		 * Borrowed from:
		 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
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
		IntegerList 	l = new IntegerArrayList();
		
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
		IntegerList	l1 = new IntegerArrayList();
		IntegerList	l2 = new IntegerArrayList();

		System.out.println("Testing toString() and clear");		
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
		IntegerList	l1 = new IntegerArrayList();
		
		System.out.println("Testing remove.");
		for (int i = -10;i <= 10; i++)
		{
			l1.addFront(i);
		}
		for (int i = 10;i >= -10.0; i--)
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
		l1.addBack(14);

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
		IntegerList	l1 = new IntegerArrayList();

		System.out.println ("Testing edge cases.");
		addArray(a1,l1,true);
		l1.remove(1);
		displayResults( (l1.size() == 2) && (l1.get(0) == 2) && (l1.get(1) == 3));
		l1.addFront(1);
		l1.remove(3);
		displayResults( (l1.size() == 2) && (l1.get(0) == 1) && (l1.get(1) == 2));

		IntegerList l2 = new IntegerArrayList();

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
		IntegerList 	l1 = new IntegerArrayList();
		boolean		pass = true;

		System.out.println("Stress test.");

		for (int i=0;i<10000;i++)
		{
			l1.addFront(i);
			l1.addBack(i);
		}

		if (l1.size() != 20000)
		{
			pass = false;
		}

		for (int i =0;i<10000;i++)
		{
			if ( l1.get(i) != (9999 - i) )
			{
				pass = false;
				break;
			}
			if ( l1.get(i+10000) != i )
			{
				pass = false;
				break;
			}
		}

		if (pass)
		{
			for (int i=0;i<10000;i++)
			{
				l1.remove(i);
			}
			if (l1.size() != 0 )
			{
				pass = false;
			}
		}
		displayResults (pass);
	}
	public static void main (String[] args)
	{
		testOne();
		testTwo();
		testThree();
		testFour();
		testFive();
	}
}
