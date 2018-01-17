public interface StringQueue {

	public boolean isEmpty();
	// Determines whether a queue is empty

	public void enqueue(String newItem) throws QueueException;
	// Adds newItem at the back of a queue. Throws
	// QueueException if the operation is not
	// successful

	public String dequeue() throws QueueException;
	// Retrieves and removes the front of a queue.
	// Throws QueueException if the operation is
	// not successful.

	public void dequeueAll();
	// Removes all items from a queue

	public String peek() throws QueueException;
	// Retrieves the front of a queue. Throws
	// QueueException if the retrieval is not
	// successful

}