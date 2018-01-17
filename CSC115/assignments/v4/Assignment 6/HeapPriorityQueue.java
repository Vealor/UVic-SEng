/*
 * HeapPriorityQueue.java
 *
 * CSC 115: Assignment 6 sample code.
 *
 * Comments from the sample solution:
 *
 * An implementation of a PriorityQueue using a heap.
 * based on the implementation in "Data Structures and Algorithms
 * in Java", by Goodrich and Tamassia
 * 
 * However, this implementation maintains the 
 * complete binary tree as a protected array, not a distinct class.
 *
 * I have also removed the Comparator from the code.  It is a
 * good practice to make a Priority Queue generic, but I think it
 * makes the code too complicated for our purposes.
 *
 * Instead, we will rely on the Comparable interface that is implemented
 * by Objects and their subclasses (although if you define your own class
 * you will need to implement the compareTo method to return something
 * meaningfull)
 *
 * I have also removed the Entry interface.  This priority queue only deals
 * with keys.
 */
 
public class HeapPriorityQueue implements PriorityQueue
{
	protected final static int DEFAULT_SIZE = 10000;
	
	/* This array is where you will store the elements in the heap */
	protected Comparable storage[];

	/* Keep track of the current number of elements in the heap */
	protected int currentSize;
			
	/* You do not need to change this constructor */
	public HeapPriorityQueue () 
	{
		this(DEFAULT_SIZE);
	}

	/* You do not need to change this constructor */
	public HeapPriorityQueue(int size)
	{
		storage = new Comparable[size + 1];
		currentSize = 0;
	}
	
	/*
	 * You need to change the implementation of every public method
 	 * below this comment.
	 *
	 */
	public int size ()
	{
		return -1;
	}
	
	public boolean isEmpty ( )
	{
		return size() == 0;
	}
	
	public Comparable removeMin () throws HeapEmptyException
	{
		return null;
	}
	
	public void insert ( Comparable k  ) throws HeapFullException
	{	
	}

	/* Your instructor's solution used the following helper methods
	 * 
	 * You do not need to use the same methods, but you may want to.
	 */
		
	/* 
	 * A new value has just been added to the bottom of the heap
	 * "bubble up" until it is in the correct position
	 */
	private void bubbleUp ( ) 
	{
	}

	/*
	 * Because of a removeMin operation, a value from the bottom
	 * of the heap has been moved to the root.
	 * 
	 * "bubble down" until it is in the right position
	 */
	private void bubbleDown() 
	{
	}	
	
	/*
	 * Swap the element at position p1 in the array with the element at 
	 * position p2
	 */
	private void swapElement ( int p1, int p2 )
	{
	}
	
	/*
	 * Return the index of the parent of the node at pos
	 */
	private int parent ( int pos )
	{
		return -1; // replace this with working code
	}
	
	/* 
	 * Return the index of the left child of the node at pos
	 */
	private int leftChild ( int pos )
	{
		return -1; // replace this with working code
	}
	
	/* 
	 * Return the index of the right child of the node at pos
	 */
	private int rightChild ( int pos )
	{	
		return -1; // replace this with working code
	}
	
	/*
	 * Given the current number of elements in the heap, does the
	 * node at pos have a left child?
	 *
	 * Note that all internal nodes have at least a left child.
	 *
	 */
	private boolean hasLeft ( int pos )
	{
		return false; // replace this with working code
	}

	/*
	 * Given the current number of elements in the heap, does the
	 * node at pos have a right child?
	 */	
	private boolean hasRight ( int pos )
	{
		return false; // replace this with working code
	}
}
