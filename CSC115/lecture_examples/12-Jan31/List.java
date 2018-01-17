/* Perfect example of list recursion */


class List{
	Node head;
	
	int sum ()
	{
		return sum (head);
	}
	
	int sum (Node n)
	{
		if (n == null)
			return 0;
		if (n.next == null)
			return n.value;
		else
			return n.value + sum(n.next); // keeps going and going
	}
}
