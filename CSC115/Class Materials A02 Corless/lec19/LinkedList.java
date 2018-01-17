public class LinkedList<T> implements List<T>
{       	
	private Node<T>		head;
	private int		count;
	
	public LinkedList() {
		head = null;
		count = 0;
		
	}
	
	public void add (T element) {
		Node<T> n = new Node<T>();
		n.element = element;
		n.next = head;
		head = n;
		count++;
	}
	
	public T elementAt (int index)
	{
		if (index < 0 || index >= size())
			throw new java.util.NoSuchElementException();
		Node<T> p = head;
		
		for (int i=0;i<index;i++)
		{
			p = p.next;
		}
		return p.element;
	}
	
	public int size()
	{
		return count;
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}

	public String toString()
	{
		Node<T>	p = head;
		String s = "{";
		while (p != null)
		{
			s += p.element;
			if (p.next != null)
				s+= ",";

			p = p.next;
		}
		s+="}";
		return s;
	}
}
