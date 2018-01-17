/*
 * IntegerNode.java
 *
 * An implementation of a node class for a doubly-linked list of integers.
 *
 * This implementation does not make the instance variables private because
 * Jason prefers to write things like:
 *
 *	n.next = null; 
 * 	n.prev.next = p;
 *
 * Rather than:
 *
 *	n.setNext(null);
 *	n.getPrev().setNext(p);
 * 
 * In this assignment, and on exams, you are free to use whichever 
 * method you find most comfortable.
 *
 * J. Corless, January 2014
 */

public class IntegerNode
{
	IntegerNode	next;
	IntegerNode	prev;
	int		value;

	public IntegerNode()
	{
		next = null;
		value = 0;
	}

	public IntegerNode (int val)
	{
		value = val;
		next = null;
	}

	public IntegerNode (int val, IntegerNode nxt)
	{
		value = val;
		next = nxt;
	}

	public IntegerNode getNext()
	{
		return next;
	}

	public IntegerNode getPrev()
	{
		return prev;
	}

	public int getValue()
	{
		return value;
	}

	public void setNext (IntegerNode nxt)
	{
		next = nxt;
	}

	public void setPrev (IntegerNode prv)
	{
		prev = prv;
	}
	
	public void setValue (int val)
	{
		value = val;
	}	
}

