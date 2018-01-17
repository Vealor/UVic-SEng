/*
 * IntegerArrayList.java
 *
 * An implementation of the IntegerList interface that uses
 * an array as storage for elements.
 * 
 * This implementation automatically grows the storage array
 * when it is full.
 *
 * This results in O(N) performance for both addBack and 
 * addFront.
 *
 * J. Corless, January 2014
 */

public class IntegerArrayList implements IntegerList
{
	private static final int INITIAL_SIZE=1;

	private int[] storage;
	private int   count;

	public IntegerArrayList ()
	{
		storage = new int[INITIAL_SIZE];
		count = 0;
	}

	private void growAndCopy()
	{
		int[] newstorage = new int[storage.length*2];
		for (int i=0;i<count;i++)
		{
			newstorage[i] = storage[i];
		}
		storage = newstorage;
	}

	/*
	 * PURPOSE:
	 *   Add the element x to the front of the list.
	 *
	 * PRECONDITIONS:
	 *   None.
	 * 
	 * Examples:
	 * 
	 * If l is {1,2,3} and l.addFront(9) returns, then l is {9,1,2,3}.
	 * If l is {} and l.addFront(3) returns, then l is {3}.
	 */
	public void addFront (int x)
	{
		if (count == storage.length)
		{
			growAndCopy();
		}
		for (int i=count; i > 0; i--)
		{
			storage[i] = storage[i-1];
		}
		storage[0] = x;
		count++;
	}


	/*
	 * PURPOSE:
	 *   Add the element x to the back of the list.
	 *
	 * PRECONDITIONS:
	 *   None.
	 * 
	 * Examples:
	 * 
	 * If l is {1,2,3} and l.addBack(9) returns, then l is {1,2,3,9}.
	 * If l is {} and l.addBack(9) returns, then l is {9}.
	 */	
	public void addBack (int x)
	{
		if (count == storage.length)
		{
			growAndCopy();
		}
		storage[count++] = x;
	}

	/*
	 * PURPOSE:
	 *	Return the number of elements in the list
	 *
	 * PRECONDITIONS:
	 *	None.
	 *
	 * Examples:
	 *	If l is {7,13,22} l.size() returns 3
	 *	If l is {} l.size() returns 0
	 */
	public int size()
	{
		return count;
	}
	
	/* 
	 * PURPOSE:
	 *   Return the element at position pos in the list.
	 * 
	 * PRECONDITIONS:
	 *	pos >= 0 and pos < l.size()
	 * 
	 * Examples:
	 *	If l is {67,12,13} then l.get(0) returns 67
	 *	If l is	{67,12,13} then l.get(2) returns 13
	 *	If l is {92} then the result of l.get(2) is undefined.
	 *
	 */
	public int  get (int pos)
	{
		// Note that there is no verification that pos is 
		// in range due to the preconditions.  
		return storage[pos];
	}
	
	/* 
	 * PURPOSE:
	 *   Remove all elements from the list.  After calling this
	 *   method on a list l, l.size() will return 0
	 * 
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 *	If l is {67,12,13} then after l.clear(), l is {}
	 *	If l is {} then after l.clear(), l is {}
	 *
	 */
	public void clear()
	{
		count = 0;
	}
	///////////////////////// PROBLEM doesn't clear list, only sets count to 0
	/* 
	 * PURPOSE:
	 *   Remove all instances of value from the list. 
	 * 
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 *	If l is {67,12,13,12} then after l.remove(12), l is {67,13}
	 *	If l is {1,2,3} then after l.remove(2), l is {1,3}
	 *	If l is {1,2,3} then after l.remove(99), l is {1,2,3}
	 */
	public void remove (int value)
	{
		for (int i = 0 ; i < count ; i++)
		{
			if (storage[i] == value)
			{
				System.out.println("outgoing: "+i);
				removeAt(i);
				i--;
			}
		}		
	}

	private void removeAt (int index)
	{
		System.out.println("index: "+index);
		for (int i = index; i < (count - 1); i++)////PROBLEM can't get last element
		{
			System.out.println("infor: "+i);
			storage[i] = storage[i + 1];
		}
		count--;
	}
	/*
	 * PURPOSE:
	 *	Return a string representation of the list
	 * 
	 * PRECONDITIONS:
	 *	None.
	 *
	 * Examples:
	 *	If l is {1,2,3,4} then l.toString() returns "{1,2,3,4}"
	 *	If l is {} then l.toString() returns "{}"
	 *
	 */
	public String toString()
	{
		String s = "{";
		for (int i=0;i<count;i++)
		{
			s+= storage[i];
			if (i != (count - 1))
			{
				s+= ",";
			}
		}
		s+= "}";
		return s;
	}

	/*
	 * Here you see the basic testing I did before moving on to
	 * the more detailed testing provided by a2tester.java
	 *
 	 * You can run this by typing:
	 * javac IntegerArrayList.java
 	 * java IntegerArrayList
	 *
	 */
	public static void main (String[] args)
	{
		IntegerList l = new IntegerArrayList();
		
		System.out.println(l);
		l.addFront(3);
		l.addFront(10);
		l.addFront(1);
		l.addFront(22);
		System.out.println(l);

		l.clear();
		
		l.addBack(3);
		l.addBack(10);
		l.addBack(1);
		l.addBack(22);
		System.out.println(l);
		
	}
}
