public class SingleLinkedList
{
	Node head;
	int  count;

	public SingleLinkedList()
	{
		head = null;
		count = 0;
	}

	public String toString()
	{
		String s = "undefined";
		return s;
	}

	public void addBack (int val)
	{

	}

	public void addFront (int val)
	{
		// This is written in the most descriptive
		// way possible.
		if (head == null)
		{
			Node n = new Node();
			n.value = val;
			n.next = null;

			head = n;	
		}
		else
		{
			Node n = new Node();
			n.value = val;

			n.next = head; // n.setNext(head)
			head = n;
		}

	}

	public int removeBack()
	{
		return -999;
	}

	public static void main (String[] args)
	{
		SingleLinkedList l = new SingleLinkedList();

		System.out.println(l);
		l.addFront(10);
		System.out.println(l);
		l.addFront(5);
		System.out.println(l);
		
		l = new SingleLinkedList();

		l.addBack(10);
		System.out.println(l);
		l.addBack(11);
		System.out.println(l);
		l.addBack(22);
		System.out.println(l);

		System.out.println(l.removeBack());
	}
}
