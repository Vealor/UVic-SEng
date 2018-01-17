import java.util.Stack;

public class ExprTree
{
	private TreeNode root;
	//create an empty tree
	public ExprTree()
	{
		
	}
	
	/** expression contains the mathematic expression in post fix format
	  process the string array and create a binary expression tree
	  throw InvalidExprException if there is an error in the expression
	*/
	public void build(String[] expression)
	{
		Stack<TreeNode> s=new Stack<TreeNode>();
		double n=0;
		for (int i=0; i<expression.length; i++)
		{
			if(isNum(expression[i]))
			{
				s.push(new TreeNode(expression[i].trim()));
			}else if(isOperator(expression[i]))
			{
				if(s.empty())
					throw new InvalidExprException("Not enough operands.");
				TreeNode operand2=s.pop();
				if(s.empty())
					throw new InvalidExprException("Not enough operands.");
				TreeNode operand1=s.pop();
				TreeNode newNode=new TreeNode(expression[i].trim(),operand1,operand2);
				s.push(newNode);
			}else
			{
				throw new InvalidExprException("The string is not a number, nor an operator.");
			}
		}
		if(s.size()!=1)
			throw new InvalidExprException("Not enough operators.");
		root=s.pop();
	}
	
	//evaluate the expression tree and return the result
	public double evaluate()
	{
		return 0;
	}
	
	//evaluate the sub expression tree rooted at node and return the result
	private double evaluate(TreeNode node)throws InvalidExprException
	{
		return 0;
	}
	
	//return the string representation of the expression tree in inorder traversal
	public String toString()
	{
		return null;
	}
	
	//return the string representation of the expression tree in inorder traversal
	public String inorder()
	{
		return null;
	}
	
	//return the string representation of the sub expression tree rooted at node in inorder traversal
	private String inorder(TreeNode node)
	{
		return null;
	}
	
	//convert the string object str to a double, throw an exception if there is an error
	private double convert(String str) throws RuntimeException
	{
		return Double.parseDouble(str);
	}
	
	//return true if the string object str is a number, false otherwise
	private boolean isNum(String str)
	{
		try{
			Double.parseDouble(str);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	//return true if the string object str is an operator, false otherwise
	private boolean isOperator(String str)
	{
		str=str.trim();
		if(str.length()<1) return false;
		char c=str.charAt(0);
		switch (c)
		{
			case '+': case '-': case '*': case '/':return true;
		}
		return false;
	}
	
	//TreeNode for the expression tree, another way to define a TreeNode, better encapsulation
	private class TreeNode
	{
		String item;
		TreeNode left;
		TreeNode right;

		public TreeNode(String str)
		{
			item=str;
			left=null;
			right=null;
		}

		public TreeNode(String str, TreeNode l, TreeNode r)
		{
			item=str;
			left=l;
			right=r;
		}
	}
}