/**
  <p>The Stack class represents a last-in-first-out(LIFO) data structure. It is implemented using the LinkedList class in the standard library. When a stack is first created, it is empty. To know which method to call, read the LinkedList api at http://docs.oracle.com/javase/7/docs/api/
  
  @author  Victoria Li
  @version %I%, %G%
  @since   1.0
*/
import java.util.LinkedList;


public class LabStack<T> implements Stack<T>
{
	private LinkedList<T> list;
	/**
	 Default constructor. Creates an empty Stack.
	*/
	public LabStack()
	{
		list = new LinkedList<T>();
	}
	
	/**
	 Check if the stack is empty.
	 @return true if the stack is empty, false otherwise.
	*/
	public boolean empty()
	{
		return list.isEmpty();
	}
	
	/**
	 Get the top element of the stack without remove it.
	 @return the top element of the stack.
	 @throws StackEmptyException if the stack is empty
	*/
	public T peek() throws StackEmptyException
	{
		if(empty()){
			throw new StackEmptyException("Sorry the stack is empty!");
		}else{
			return list.peek();
		}
	}
	
	/**
	 Get the top element of the stack and remove it.
	 @return the top element of the stack.
	 @throws StackEmptyException if the stack is empty
	*/
	public T pop() throws StackEmptyException
	{
		if(empty()){
			throw new StackEmptyException("Sorry the stack is empty!");
		}else{
			return list.pop();
		}
	}
	
	/**
	 Push the element onto the stack
	 @param element an object of type T is pushed to the top of the stack.
	*/
	public void push(T element)
	{
		list.push(element);
	}
	
	/**
	 Gets the number of elements in the stack
	 @return the number of elements in the stack
	*/
	public int size()
	{
		return list.size();
	}
	
	/**
	 Make the stack empty
	*/
	public void makeEmpty()
	{
		list.clear();
	}
	
	/**
	 Gets the elements in the stack
	 @return the string presentation of all the elements in the stack
	*/
	public String toString()
	{
		return list.toString();
	}
}