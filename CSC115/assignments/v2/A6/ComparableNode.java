public class ComparableNode
{
	public Comparable element;
	public ComparableNode next;
	public ComparableNode prev;

	public ComparableNode()
	{
		this(null,null,null);
	}

	public ComparableNode(Comparable el)
	{
		this(el, null,null);
	}

	public ComparableNode(Comparable el, ComparableNode nxt, ComparableNode prv)
	{
		element = el;
		next = nxt;
		prev = prv;
	}
}

