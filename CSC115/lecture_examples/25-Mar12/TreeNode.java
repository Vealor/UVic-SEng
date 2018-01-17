public class TreeNode
{
	int		element;
	TreeNode	left;
	TreeNode	right;

	public TreeNode()
	{
		this(0,null,null);	
	}

	public TreeNode (int el)
	{
		this(el,null,null);
	}
	
	public TreeNode (int el, TreeNode l, TreeNode r)
	{
		element = el;	
		left = l;
		right = r;
	}

	int	height ()
	{
		int lh = 0;
		int rh = 0;

		if (left != null)
			lh = left.height();
		if (right != null)
			rh = right.height();

		if (lh > rh)
			return 1 + lh;
		else
			return 1 + rh;
	}
}
