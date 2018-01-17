/**
 * An ADT interface for a queue.
 * Queues allow FIFO (first-in, first-out) access to a collection of items.
 */
public interface Queue<T> 
{

	/** Add an item to the back of this queue. */
	void enqueue(T item);
	
	/** Remove an item from the front of this queue. */
	T dequeue() throws QueueEmptyException;	

	/** Are there no elements in this queue? */
	boolean isEmpty();
	
}
