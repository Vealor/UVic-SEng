public class BinarySearchTree
{
	TreeNode	root;

	public BinarySearchTree()
	{
		root = null;
	}

	public boolean	find (int element)
	{
		TreeNode p = root;
		for (;;)
		{
			if (p == null)
				return false;
			if (element < p.element)
				p = p.left;
			else if (element > p.element)
				p = p.right;
			else
				return true;
		}
	}

	public boolean insert (int element)
	{
		TreeNode n = new TreeNode(element);

		if (root == null)
		{
			root = n;
			return true;
		}

		TreeNode p = root;
		for (;;)
		{	
			if (element == p.element)
				return false;

			if (element < p.element)
			{
				if (p.left == null)
				{
					p.left = n;
					return true;
				}
				else
				{
					p = p.left;
				}
			}
			else
			{
				if (p.right == null)
				{
					p.right = n;
					return true;
				}
				else
				{
					p = p.right;
				}
			}
		}

	}

	private void doToString (TreeNode n, StringBuilder s)
	{
		if (n == null)
			return;
		doToString(n.left, s);
		s.append(n.element);
		s.append(" ");
		doToString(n.right, s);
	}

	public String	toString()
	{
		StringBuilder s = new StringBuilder("");
		doToString(root, s);
		return s.toString();
	}

	public int height()
	{
		if (root == null)
			return 0;
		else
			return root.height();
	}
	
	public static void main (String[] args)
	{
		BinarySearchTree t = new BinarySearchTree();

		if (args.length == 0)
		{
			
		}
		else
		{
			for (int i = 0; i < args.length; i++)
			{
				t.insert(Integer.parseInt(args[i]));						
			}
		}
		System.out.println("Tree height is : " + t.height());
		System.out.println(t);
	}
}
