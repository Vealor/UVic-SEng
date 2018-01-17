/*
 * PriorityQueue.java
 *
 * A priority queue interface for objects that implement the 
 * Comparable interface.
 * 
 * This interface considers the minimum value to be the highest
 * priority.
 * 
 */
 
 public interface PriorityQueue
 {
 	public void insert ( Comparable key );
	public Comparable removeMin ();
	public boolean isEmpty();			
	public int	size ();

 }
 
