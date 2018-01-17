/**
 * ArrayStack.java
 *
 * An implementation of the Stack ADT using an array.
 *
 */
public class ArrayStack<T> implements Stack<T>
{
	private static final int INITIAL_SIZE = 10;
	
	/** 
	 * position is the index of the next
	 * free space in the stack.
	 */
	private	int position;
	
	// This is a not-recommended way of getting
	// around the restriction on using arrays
	// of generic types.
	//
	// The recommended practice is to use
	// ArrayList<T> in the standard 
	// library provides all the services you need.  
	//
	// Sometimes the standard library is not available
	// or appropriate (mostly in memory or processor 
	// constrained environments which can use a subset
	// of the Java services).  
	private Object[] storage;
	
	public ArrayStack()
	{
		position = 0;
		storage = new Object[INITIAL_SIZE];
	}
	
	public int size()
	{
		return position;
	}
	public boolean empty()
	{
		return size() == 0;
	}

	private void growArray()
	{
		Object[] bigger = new Object[storage.length*2];
		for (int i=0;i<position;i++)
			bigger[i] = storage[i];
		storage = bigger;
	}	

	public void push (T o)
	{
		if (position == storage.length)
		{
			growArray();
		}
		storage[position++] = o;
	}
	
	public T pop() throws StackEmptyException
	{
		if (empty())
		{
			throw new StackEmptyException("Stack empty");
		}
		// This line is generating a warning 
		T val = (T)storage[--position];
		return val;
	}
	
	public T peek() throws StackEmptyException
	{
		if (empty())
			throw new StackEmptyException("Stack empty");
		// This line is generating a warning 
		return (T)storage[position-1];
	}
	
	public void makeEmpty()
	{
		// This probably isn't required.
		for (int i = 0; i<position;i++)
		{
			storage[i] = null;
		}
		position = 0;
	}
}

