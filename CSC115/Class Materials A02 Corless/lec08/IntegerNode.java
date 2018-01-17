public class IntegerNode
{
	IntegerNode	next;
	int		value;

	public IntegerNode()
	{
		next = null;
		value = -99999;
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
}
