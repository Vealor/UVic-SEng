/**
  Derived from page 582 of J. J. Prichard and F. M. Carrano, 3rd Edition.
*/
import java.util.Queue;
import java.util.LinkedList;

public class SBinaryST
{
	private STreeNode root;
	
	//default constructor, construct an empty string binary search tree
	public SBinaryST(){
		root = null;
	}
	
	//return true if the tree is empty, false otherwise
	public boolean isEmpty(){
		if(root == null){
			return true;
		}
		return false;
	}
	
	//make the tree empty
	public void makeEmpty(){
		root = null;
		
	}
	
	//return the string at root, throw a TreeException if it is empty
	public String getRootStr(){
		if (isEmpty()){
			throw new TreeException("Tree is empty. Root cannot be accessed!");
		}
		return root.item; 
	}
	
	//insert the string str into the binary search tree (in alphabetical order)
	public void insert(String str){
		if(isEmpty()){
			root = new STreeNode(str);
		}else{
		insert(root, str);
		}
	}
	
	//insert the string str into the binary search sub-tree rooted at node(in alphabetical order)
	private STreeNode insert(STreeNode node, String str){
		if(isEmpty()){
			root = node;
		}else{
			if(0 > str.compareTo(node.item)){
				if(node.left == null){
					node.left = new STreeNode(str);
				}else{
					insert(node.left, str);
				}
			}else if(0 < str.compareTo(node.item)){
				if(node.right == null){
					node.right = new STreeNode(str);
				}else{
					insert(node.right, str);
				}
			}else{
				throw new TreeException("Duplicate string, cannot add to Tree.");
			}
		}
		return null;
	}
	
	//return a string representation of the shape of the tree, tilted 90 degrees
	public String printTree(){
		StringBuilder s = new StringBuilder("The shape of the tree is:\n");
		printTree(root,2,s);
		return s.toString();
	}
	
	//StringBuilder object "output" contains the shape of the tree, tilted 90 degrees
	private void printTree(STreeNode node, int indent,StringBuilder output){
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
	
	//return the height of the tree. return 0 is the tree is null
	public int height(){
		return height(root);
	}
	
	//return the height of the tree rooted at node. return 0 is the node is null
	private int height(STreeNode node){
		int l = 0;
		int r = 0;
		if(node == null){
			return 0;
		}
		if(node.left!=null)
			l = height(node.left);
		if(node.right!=null)
			r = height(node.right);
		return 1 + ((l>r)? l : r);
	}
	//return string representation of the tree, one level at a time
	public String levelOrderStr(){
		Queue<STreeNode> q = new LinkedList<STreeNode>();
		if (isEmpty()){
				return "{}";
		}
		q.add(root);
		String s = "";
		while(!q.isEmpty()){
			STreeNode temp = q.remove();
			s += temp.item  + ", ";
			if(temp.left != null)
				q.add(temp.left);
			if(temp.right != null)
				q.add(temp.right);
		}
		return "{" + s.substring(0, s.length()-2) + "}";
	}
	//return the in-order string representation of the tree rooted
	public String toString()
	{
		return inOrderStr();
	}
	
	//return the in-order string representation of the tree rooted
	public String inOrderStr(){
		String s = "";
		if(!isEmpty()){
			s += inOrder(root);
			s = s.substring(0, s.length()-2);
		}
		return "{" + s + "}";
	}
	
	//return the in-order string representation of the sub tree rooted at node
	private String inOrder(STreeNode node){
		String s = "";
		if(node.left != null)
			s += inOrder(node.left);
		s += node.item + ", ";
		if (node.right != null)
			s += inOrder(node.right);
		return s;
	}
	
	//return the post-order string representation of the tree
	public String postOrderStr(){
		String s = "";
		if(!isEmpty()){
			s += postOrder(root);
			s = s.substring(0, s.length()-2);
		}
		return "{" + s + "}";
	}
	
	//return the post-order string representation of the sub tree rooted at node
	private String postOrder(STreeNode node)
	{
		String s = "";
		if(node.left != null)
			s += postOrder(node.left);
		if (node.right != null)
			s += postOrder(node.right);
		s += node.item + ", ";
		return s;
	}
	
	//return the pre-order string representation of the tree
	public String preOrderStr(){
		String s = "";
		if(!isEmpty()){
			s += preOrder(root);
			s = s.substring(0, s.length()-2);
		}
		return "{" + s + "}";
	}
	//return the pre-order string representation of the sub tree rooted at node
	private String preOrder(STreeNode node){
		String s = "";
		s += node.item + ", ";
		if(node.left != null)
			s += preOrder(node.left);
		if (node.right != null)
			s += preOrder(node.right);
		return s;
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