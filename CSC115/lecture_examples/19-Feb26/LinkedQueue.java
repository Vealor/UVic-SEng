/*
 * 
 **** NOTE ******
 *
 * This is pseudo code that looks like Java
 * It doesn't actually compile.
 *
 * Left as an exercise for the student to
 * actually make it work.
 */

public class LinkedQueue<T> implements Queue<T>
{
	LinkedList<T> theList;

	public LinkedQueue()
	{
		theList = new LinkedList<T>();
	}

	public void enqueue(T element)
	{
		theList.addBack(element);
	}

	public T dequeue()
	{
		if (theList.size() != 0)
		{
			return theList.removeFront();
		}
		else
		{
			throw new QueueEmptyException();
		}
	}
}
