public class SLL<T extends Comparable> implements GeneralList<T>
{
	private Node<T> head;
	private int count;
	
	public SLL()
	{
	}
	
	/*
	 * PURPOSE:
	 *	Add the element x to the front of the list.
	 *
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 * 
	 * If l is {1,2,3} and l.addFront(9) returns, then l is {9,1,2,3}.
	 * If l is {} and l.addFront(3) returns, then l is {3}.
	 */
	public void addFront (T x)
	{
		Node<T> p = new Node<T>(x);
		p.next = head;
		head = p;
		count++;
	}

	/*
	 * PURPOSE:
	 *	Add the element x to the back of the list.
	 *
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 * 
	 * If l is {1,2,3} and l.addBack(9) returns, then l is {1,2,3,9}.
	 * If l is {} and l.addBack(9) returns, then l is {9}.
	 */	
	public void addBack (T x)
	{
		Node<T> p = head;
		Node<T> n = new Node<T>(x);
		if(p==null){
			head = n;
		}else{
			while(p.next!=null){
				p = p.next;
			}
			p.next = n;
		}
		count++;
	}

	/*
	 * PURPOSE:
	 *	Return the number of elements in the list
	 *
	 * PRECONDITIONS:
	 *	None.
	 *
	 * Examples:
	 *	If l is {7,13,22} l.size() returns 3
	 *	If l is {} l.size() returns 0
	 */
	public int size()
	{
		return count;
	}
	
	/* 
	 * PURPOSE:
	 *	Return the element at position pos in the list.
	 * 
	 * PRECONDITIONS:
	 *	pos >= 0 and pos < l.size()
	 * 
	 * Examples:
	 *	If l is {67,12,13} then l.get(0) returns 67
	 *	If l is	{67,12,13} then l.get(2) returns 13
	 *	If l is {92} then the result of l.get(2) is undefined.
	 *
	 */
	public T  get (int pos)
	{
		Node<T> p = head;
		for(int i=0;i<pos;i++){
			p = p.next;
		}
		return p.item;
	}
	
	/* 
	 * PURPOSE:
	 *	Remove all elements from the list.  After calling this
	 *	method on a list l, l.size() will return 0
	 * 
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 *	If l is {67,12,13} then after l.clear(), l is {}
	 *	If l is {} then after l.clear(), l is {}
	 *
	 */
	public void clear()
	{
		head = null;
		count = 0;
	}

	/* 
	 * PURPOSE:
	 *	Remove the first item from the list. 
	 * 
	 * PRECONDITIONS:
	 *	The list is not empty.
	 * 
	 * Examples:
	 *	If l is {67,12,13,12} then after removeFront(), l is {12,13,12}
	 *  67 is returned.
	 */
	public T removeFront ()
	{
		T tmpVal = head.item;
		head = head.next;
		count--;
		return tmpVal;
	
	}

	/* 
	 * PURPOSE:
	 *	Remove all instances of value from the list. 
	 * 
	 * PRECONDITIONS:
	 *	None.
	 * 
	 * Examples:
	 *	If l is {67,12,13,12} then after l.remove(12), l is {67,13}
	 *	If l is {1,2,3} then after l.remove(2), l is {1,3}
	 *	If l is {1,2,3} then after l.remove(99), l is {1,2,3}
	 */
	public void remove (T value)
	{
		Node<T> p = head;
		if(p.item.equals(value)){
			head = p.next;
			count--;
		}
		while(p.next!=null){
			if(p.next.item.equals(value)){
				count--;
				p.next = p.next.next;				
			}else if(p.next!=null){
				p=p.next;
			}
		}
	}

	/*
	 * PURPOSE:
	 *	Return a string representation of the list
	 * 
	 * PRECONDITIONS:
	 *	None.
	 *
	 * Examples:
	 *	If l is {1,2,3,4} then l.toString() returns "{1,2,3,4}"
	 *	If l is {} then l.toString() returns "{}"
	 *
	 */
	public String toString()
	{
		String list = "{";
		Node<T> p = head;
		for(int i=0;i<count;i++){
			list += p.item;
			if(p.next!=null){
				list += ",";
			}
			p=p.next;
		}
		list += "}";
		return list;
	}
}