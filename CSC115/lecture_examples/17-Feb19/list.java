class list
{
	Node head;

	public void reverse()
	{
		head = reverseIt(head);
	}
	
	private Node reverseIt(Node list)
	{
		if (n==null||n.next==null){
			return list;
		}
		/// to get here, must have at least 2 elements in the list
		Node safeFront = list.next;
		list.next = null;
		Node rest = reverseIt(saveFront);
		/// assume reverseIt() works!
		saveme.next = list;
		return rest;		
	}

	public static void main(String[] args)
	{
		reverse();
	}
}
