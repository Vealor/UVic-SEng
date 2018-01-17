import java.util.Stack;

public class ExprTree
{
	private TreeNode root;
	//create an empty tree
	public ExprTree()
	{
		root = null;
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
		return evaluate(root);
	}
	
	//evaluate the sub expression tree rooted at node and return the result
	private double evaluate(TreeNode node)throws InvalidExprException
	{
		if(isNum(node.item)){
			return convert(node.item);
		}
		if(isOperator(node.item)){
			double l = evaluate(node.left);
			double r = evaluate(node.right);
			char c = node.item.charAt(0);
			switch(c){
				case '+': 
					return l + r;
				case '-': 
					return l - r;
				case '*': 
					return l * r;
				case '/':
					return l / r;
				default:
					throw new InvalidExprException("Invalid Expression.");
			}
		}
		throw new InvalidExprException("Invalid Expression.");
	}
	
	//return the string representation of the expression tree in inorder traversal
	public String toString()
	{
		return inorder();
	}
	
	//return the string representation of the expression tree in inorder traversal
	public String inorder()
	{
		return inorder(root);
	}
	
	//return the string representation of the sub expression tree rooted at node in inorder traversal
	private String inorder(TreeNode node)
	{
		String str = "";
		if(node.left!=null)
			str += inorder(node.left);
		str += node.item + " ";
		if(node.right!=null)
			str += inorder(node.right);
		return str;
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
	
	//return a string representation of the shape of the tree, tilted 90 degrees
	public String printTree(){
		StringBuilder s = new StringBuilder("The shape of the tree is:\n");
		printTree(root,2,s);
		return s.toString();
	}
	
	//StringBuilder object "output" contains the shape of the tree, tilted 90 degrees
	private void printTree(TreeNode node, int indent,StringBuilder output){
		if(node!=null){
			printTree(node.right, indent+1, output);
			
			for(int i=0;i<(indent*2);i++){
				output.append(" ");
			}
			output.append(node.item);
			output.append("\n");
			
			printTree(node.left, indent+1, output);
		}
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