public interface Stack<T>
{
	/* Add a new item to the top of the
	 * stack.
	 */
	void push (T item);
	
	/* Remove the top item from the stack.
	 *
	 */ 
	T pop () throws StackEmptyException;
	
	/*
	 * Is the stack empty?
	 */
	boolean isEmpty();	

}
