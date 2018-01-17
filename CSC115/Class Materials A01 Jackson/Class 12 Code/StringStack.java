public interface StringStack {
	public boolean isEmpty();
	// Determines whether a stack is empty.

	public void push(String newItem) throws StackException;
	// Adds newItem to the top of the stack.
	// Throws StackException if the insertion is
	// not successful.

	public String pop() throws StackException;
	// Retrieves and then removes the top of the stack.
	// Throws StackException if the deletion is not
	// successful.

	public void popAll();
	// Removes all items from the stack.

	public String peek() throws StackException;
	// Retrieves the top of the stack. Throws
	// StackException if the retrieval is not successful
}