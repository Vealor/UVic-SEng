class Node 
{
	int value;
	Node next;
	
	int sum()
	{
		if (next == null)
			return value;
		else
			return value + next.sum();
	}
}


class List
{
	Node head;
	int sum {
		if ( head != null)
			retuen head.sum()
		else return 0;
	}
}

