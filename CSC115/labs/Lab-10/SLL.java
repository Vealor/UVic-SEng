//derived from Lab 5, added SLLIterator implementation
import java.util.Iterator;
import java.util.NoSuchElementException;
public class SLL<T> implements GeneralList<T>,Iterable<T>
{
	private Node<T> head;
	private int count;
	
	public SLL()
	{
		head=null;
		count=0;
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
		head=new Node<T>(x,head);
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
		Node<T> newNode=new Node<T>(x);
		count++;
		//when it is empty
		if(head==null)
		{
			head=newNode;
			return;
		}
		Node<T> temp=head;
		while(temp.next!=null)
		{
			temp=temp.next;
		}
		temp.next=newNode;
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
		if(head==null)
			return null;//throw exception
		Node<T> temp=head;
		for (int i=0; i<pos; i++)
		{
			temp=temp.next;
		}
		return temp.item;
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
		head=null;
		count=0;
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
		if(head==null)
			return;
		Node<T> current=head;
		Node<T> previous=head;
		while(current!=null)
		{
			if (current.item.equals(value))
			{
				if (current==head)
				{
					head=current.next;
					previous=current.next;
				}else{
					previous.next=current.next;
				}
				current=current.next;
				count--;
			
			}else
			{
				previous=current;
				current=current.next;
			}
		}
	}
	
	//create an instance of SLLIterator and return it
	public Iterator<T> iterator()
	{
		return new SLLIterator();
	}
	
	//SLLIterator class implement java.util.Iterator interface
	//example of an inner class
	private class SLLIterator implements Iterator<T>
	{
		private Node<T> nextNode;
		/**
		  construct an iterator that points to the front of the list.
		*/
		public SLLIterator()
		{
			this.nextNode = SLL.this.head;
		}
		
		public boolean hasNext()
		{
			return this.nextNode != null;
		}
		/**
		  moves the iterator past the next item.
		  @return the traversed item.
		*/
		public T next()
		{
			if(hasNext()){
				T result = nextNode.item;
				nextNode = nextNode.next;
				return result;
			}
			throw new NoSuchElementException();
		}
		
		/**
		 method not supported
		 **/
		public void remove() throws UnsupportedOperationException
		{
			
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
		StringBuilder output=new StringBuilder("{");
		Node temp=head;
		while(temp!=null)
		{
			output.append(temp.item);
			if(temp.next!=null)
				output.append(',');
			temp=temp.next;
			
		}
		output.append('}');
		return output.toString();
	}
	
	public static void main(String[] args)
	{
		SLL<Integer> list=new SLL<Integer>();
		int[] input={1,2,3,4};
		//test addBack
		for (int i=0; i<input.length; i++)
		{
			list.addBack(input[i]);
		}
		
		System.out.println(list);
		//output the same list using an interator.
		
		Iterator<Integer> l = list.iterator();
		while(l.hasNext()){
			System.out.println(l.next());
		}
	}
	private class Node<T>
	{
		public T item;
		public Node<T> next;
		/**
		 Default constructor. Sets the item and next to null.
		*/
		public Node()
		{
			item=null;
			next=null;
		}
		/**
		 Constructor. Assigns the n to item, null to next.
		 @param n an object of type T is assigned to the instance variable item.
		*/
		public Node(T n)
		{
			item=n;
			next=null;
		}
		/**
		 Constructor. Assigns the n to item, nextNode to next.
		 @param n        an object of type T is assigned to the instance variable item.
		 @param nextNode an Node object assigned to the instance object next
		*/
		public Node(T n, Node<T> nextNode )
		{
			item=n;
			next=nextNode;
		}
		/**
		 Gets the value of item
		 @return the value of item
		*/
		public T getItem()
		{
			return item;
		}
		/**
		 Assign newItem to instance variable item
		 @param newItem   an object of type T is assigned to the instance variable item.
		*/
		public void setItem(T newItem)
		{
			item=newItem;
		}
		/**
		 Gets the object next.
		 @return instance object next
		*/
		public Node<T> getNext()
		{
			return next;
		}
		/**
		 Assign nextNode to instance variable next
		 @param nextNode   an Node object
		*/
		public void setNext(Node<T> nextNode)
		{
			next=nextNode;
		}
	}
}