public class Node<T>
{
	T 		element;
	Node<T> 	next;
	
	public Node()
	{
		this(null,null);
	}

	public Node (T el)
	{
		this(el,null);
	}
	
	public Node (T el, Node<T> nxt)
	{
		element = el;
		next = nxt;
	}

	public Node<T> getNext()
	{
		return next;
	}
	
	public void setNext (Node<T> nxt)
	{
		next = nxt;
	}

	public T getElement ()
	{
		return element;
	}
	
	public void setElement(T el)
	{
		element = el;
	}
}
