/*
 * SortedArrayList.java
 *
 * An implementation of the IntegerList interface that uses
 * an array as storage for elements.
 * 
 * This implementation automatically grows the storage array
 * when it is full.
 *
 * This results in O(N) performance for insert and O(lg(n))
 * for find.
 *
 * J. Corless, January 2014
 * Modified by Victoria Li, January 2014
 */

public class SortedArrayList 
{
	private static final int INITIAL_SIZE=1;

	private int[] storage;
	private int   count;

	public SortedArrayList ()
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
	 *   Add the element x to the list in an sorted order (incrementing order)
	 *
	 * PRECONDITIONS:
	 *   None.
	 * 
	 * Examples:
	 * 
	 * If l is {1,2,9} and l.insert(5) returns, then l is {1,2,5,9}.
	 * If l is {} and l.insert(3) returns, then l is {3}.
	 */
	public void insert (int x)
	{
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
	 *   search n in the array, if found, return the index
	 *   return -1 if not found (use binary search)
	 * 
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 *	If l is {6,12,13} then after l.find(12), 1 is returned.
	 *  If l is {6,12,13} then after l.find(78), -1 is returned.
	 *	If l is {} then after l.find(2), -1 is returned 
	 *
	 */
	public int find(int n)
	{
		return 0;
	}

	/* 
	 * PURPOSE:
	 *   search the integers storage[first] through storage[last] for
	 *   value by using a binary search.
	 * 
	 * PRECONDITIONS:
	 *	0<=first, last <count, array storage is sorted.
	 * 
	 * POSTCONDITIONS:
	 * If value is in the array storage, it returns the index of that value,
	 * otherwise, returns -1 if value is not in the array storage 
	 * Examples:
	 *	If l is {7,12,13} then after l.binarySearch(0,2,12), 1 is returned.
	 *  If l is {7,12,13} then after l.binarySearch(0,2,78), -1 is returned.
	 *	If l is {} then after l.binarySearch(0,-1,8), -1 is returned 
	 *
	 */
	private int binarySearch(int first, int last, int value)
	{
		return 0;
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
	
	public static void main(String[] args)
	{
		SortedArrayList list=new SortedArrayList();
		System.out.println("Search 12, index = "+list.find(12));
		list.insert(67);
		System.out.println(list);
		list.insert(12);
		System.out.println(list);
		list.insert(13);
		System.out.println(list);
		System.out.println(list.size());
		System.out.println("Search 12, index = "+list.find(12));
		System.out.println("Search 13, index = "+list.find(13));
		System.out.println("Search 67, index = "+list.find(67));
		System.out.println("Search 9, index = "+list.find(9));
		System.out.println("Search 132, index = "+list.find(132));
		System.out.println(list);
	}
}
