public class LinkedPriorityQueue implements PriorityQueue
{
	ComparableNode	head;
	int		count;

	public LinkedPriorityQueue ()
	{
		head = null;
		count = 0;
	}

 	public void insert ( Comparable key )
	{
		ComparableNode n = new ComparableNode(key);
		n.next = head;
		if (head != null)
		{
			head.prev = n;
		}

		head = n;
		count ++;
	}

	public Comparable removeMin ()
	{
		if (count == 0)
			throw new HeapEmptyException();
	
		ComparableNode min = head;
		ComparableNode p = head;

		while (p != null)
		{
			if (p.element.compareTo(min.element) < 0)
			{
				min = p;
			}
			p = p.next;
		}
	
		Comparable val = min.element;
		removeNode(min);

		return val;
	}

	private void removeNode (ComparableNode p)
	{
		if (p == head)
		{
			head = head.next;
			if (head != null)
			{				
				head.prev = null;
			}
		}
		else
		{
			p.prev.next = p.next;
			if (p.next != null)
				p.next.prev = p.prev;
		}
		count--;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}
			
	public int	size ()
	{
		return count;
	}
}

