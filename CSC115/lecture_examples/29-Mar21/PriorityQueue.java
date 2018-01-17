/**
 * PriorityQueue.java
 *
 * A priority queue interface for objects that implement the
 * Comparable interface.
 *
 * This interface considers teh minimum value to the the highest priority.
 *
 */
 
public interface PriorityQueue{
	public void insert (Comparable key );
	public Comparable removeMin();
	public boolean isEmpty();
	public int size();
}




/*
Spaceships
 -> ability to order arbitrary object
 1 < 2 < 3 < 5
 "aaa" < "bbb" < "ccc"
 
 comparable:
 	public interface Comparable{
 		int compareTo(Object o);
 	}
 	
 	public class Spaceship implements Comparable{
 		int compareTo(Spaceship other){
 			//do something
 		}
 	}
