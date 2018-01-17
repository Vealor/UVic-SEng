/**
  Derived from page 582 of J. J. Prichard and F. M. Carrano, 3rd Edition.
*/
import java.util.Queue;
import java.util.LinkedList;

public class SBinaryST
{
	private STreeNode root;
	
	//default constructor, construct an empty string binary search tree
	public SBinaryST()
	{
		
	}
	
	//return true if the tree is empty, false otherwise
	public boolean isEmpty()
	{
		return true;
	}
	
	//make the tree empty
	public void makeEmpty()
	{
		
	}
	
	//return the string at root, throw a TreeException if it is empty
	public String getRootStr()
	{
		
		return null; 
	}
	
	//insert the string str into the binary search tree (in alphabetical order)
	public void insert(String str)
	{
		
	}
	
	//insert the string str into the binary search sub-tree rooted at node(in alphabetical order)
	private STreeNode insert(STreeNode node, String str)
	{
		
		return null;
	}
	
	//return a string representation of the shape of the tree, tilted 90 degrees
	public String printTree()
	{
		//optional
		return null;
	}
	
	//StringBuilder object "output" contains the shape of the tree, tilted 90 degrees
	private void printTree(STreeNode node, int indent,StringBuilder output)
	{
		//optional
	}
	
	//return the height of the tree. return 0 is the tree is null
	public int height()
	{
		return 0;
	}
	
	//return the height of the tree rooted at node. return 0 is the node is null
	private int height(STreeNode node)
	{
		
		return 0;
	}
	//return string representation of the tree, one level at a time
	public String levelOrderStr()
	{
		
		return null;
	}
	//return the in-order string representation of the tree rooted
	public String toString()
	{
		return inOrderStr();
	}
	
	//return the in-order string representation of the tree rooted
	public String inOrderStr()
	{
		
		return null;
	}
	
	//return the in-order string representation of the sub tree rooted at node
	private String inOrder(STreeNode node)
	{
		
		
		return null;
	}
	
	//return the post-order string representation of the tree
	public String postOrderStr()
	{
		
		return null;
	}
	
	//return the post-order string representation of the sub tree rooted at node
	private String postOrder(STreeNode node)
	{
		
		
		return null;
	}
	
	//return the pre-order string representation of the tree
	public String preOrderStr()
	{
		
		return null;
	}
	//return the pre-order string representation of the sub tree rooted at node
	private String preOrder(STreeNode node)
	{
		
		
		return null;
	}
	
	public static void main(String[] args)
	{
		SBinaryST tree=new SBinaryST();
		tree.insert("d");
		tree.insert("b");
		tree.insert("a");
		tree.insert("c");
		tree.insert("e");
		System.out.println("inorder: "+tree);
		System.out.println("pre: "+tree.preOrderStr());
		System.out.println("post: "+tree.postOrderStr());
		System.out.println("level: "+tree.levelOrderStr());
		System.out.println("height = "+tree.height());
		System.out.println(tree.printTree());
	}
}