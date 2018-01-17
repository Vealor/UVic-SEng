/* AVLTree.java
   CSC 226 - Fall 2014
   Assignment 2 - Template for an AVL tree with String elements.
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java AVLTree
	
   Input data should consist of a list of strings to insert into the tree, one 
   per line, followed by the token "###" on a line by itself, followed by a 
   list of strings to search for, one per line.
	
   To conveniently test the algorithm with a large input, create
   a text file containing the input data and run the program with
	java AVLTree file.txt
   where file.txt is replaced by the name of the text file.

   B. Bird - 06/28/2014
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.io.File;

//Do not change the class name.
public class AVLTree{
	/* ************************* DEBUGGING OPTIONS ************************* */
	/*
	   These govern the behavior of the testing code in main().
	   Some tests are slow or annoying, so you may want to disable them.
	*/
	
	/* Tests for AVL properties */
	/* If these are set to true, the AVL properties are checked after every 
	   insertion/deletion. If the properties do not hold, an error message is
	   printed and the program halts. You should disable these tests until you
	   have implemented the basic BST functionality and are ready to start
	   writing AVL rebalance operations.
	   
	   Note that testing the AVL properties is a O(n) operation, so these
	   tests can dramatically increase the running time of the program.
	*/
	   
	public static final boolean TestAVLAfterEachInsertion = false;
	public static final boolean TestAVLAfterEachDeletion = false;
	
	
	/* Tests for binary search tree property */
	/* If these are set to true, the tree is verified to be a binary search
	   tree after every	insertion/deletion (but the AVL height property is not
	   checked). If the tree is not a binary search tree, an error message is
	   printed and the program halts. 
	   
	   If the AVL property tests are enabled with the variables above, the
	   variables below are ignored (since the AVL property tests also verify
	   that the tree is a binary search tree).
	   
	   Note that the binary search tree test is O(n), so the test will
	   dramatically increase the running time of the program.
	*/
	
	public static final boolean TestBSTAfterEachInsertion = false;
	public static final boolean TestBSTAfterEachDeletion = false;
	
	/* Threshold for printing the tree contents */
	/* If one of the tests above fails, the entire tree will be printed if its
	   size is at most this value
	*/
	public static final int PrintTreeSizeThreshold = 10;
	/* *********************** END DEBUGGING OPTIONS *********************** */

	private TreeNode root = null;
	/* getRoot()
	   Return a pointer to the root of the AVL tree (or null if the tree is 
	   empty)
	*/
	public TreeNode getRoot(){
		return root;
	}
	
	
	/* *********************** METHODS TO IMPLEMENT ************************ */
	
	/* insert(s)
	   Inserts a node containing the string s into the tree and returns a 
	   pointer to the inserted node.
	*/
	public TreeNode insert(String s){
		/* Your code here */
		return null;
	}	
	
	/* remove(node)
	   Given a TreeNode instance (which is assumed to be a node of the tree), 
	   delete it from the tree and restore the tree properties.
	*/
	public void remove(TreeNode node){
		/* Your code here */
	}
	
	
	/* ******************** END OF METHODS TO IMPLEMENT ******************** */
	
	/* find(s)
	   Searches for the string s in the tree. If a node containing s is found,
	   returns a pointer to that node. If s is not found in the tree, the return
	   value will be null.
	*/
	public TreeNode find(String s){
		TreeNode node = root;
		while (node != null){
			if (node.nodeValue.equals(s))
				return node;
			int c = s.compareTo(node.nodeValue);
			//If s < nodeValue, go left
			if (c < 0)
				node = node.leftChild;
			else
				node = node.rightChild;
		}
		return null;
	}

	
	
	/* getTreeSize()
	   Returns the total number of nodes in the tree.
	*/
	public int getTreeSize(){
		return getSubTreeSize(root);
	}
	
	/* getSubTreeSize(node)
	   Returns the total number of nodes in the subtree rooted at the provided 
	   node.
	*/
	public int getSubTreeSize(TreeNode node){
		if (node == null)
			return 0;
		return 1 + getSubTreeSize(node.leftChild) + getSubTreeSize(node.rightChild);
	}
	
	/* getTreeHeight()
	   Returns the height of the tree. A tree containing a single
	   node has height 0. If the tree is empty, the value -1 is returned.
	*/
	public int getTreeHeight(){
		return root.height;
	}
	
	/* printTreeRecursive(node, leftPrefix, nodePrefix, rightPrefix)
	   Takes a pointer to a tree node and strings containing the current indentation level
	   and prints an in-order traversal of the subtree rooted at the provided node.
	*/
	private void printTreeRecursive(TreeNode node, String leftPrefix, String nodePrefix, String rightPrefix){

		if (node.leftChild == null){
			System.out.println(leftPrefix + "    |-- (no left child)");
		}else{
			printTreeRecursive(node.leftChild, leftPrefix + "     ", leftPrefix + "    |--",leftPrefix + "    |" );
			System.out.println(leftPrefix  + "    |");
		}

		System.out.println(nodePrefix + node.nodeValue);
		if (node.rightChild == null){
			System.out.println(rightPrefix + "    |-- (no right child)");
		}else{
			System.out.println(rightPrefix + "    |");
			printTreeRecursive(node.rightChild, rightPrefix + "    |", rightPrefix + "    |--",rightPrefix + "     " );
		}
	}
	
	/* printTree()
	   Prints an in-order traversal of the tree.
	*/
	public void printTree(){
		System.out.println("----------");
		if (root == null){
			System.out.println("Tree is empty.");
		}else{
			printTreeRecursive(root,"","","");
		}
		System.out.println("----------");
	}
	
	
	/* TreeNode class */
	/* Do not change anything in the class definition below */
	/* If any contents of the TreeNode class are changed, your submission will
	   not be marked. */
	public static class TreeNode{
		//String value contained in this node
		public String nodeValue;
		//Pointers to left and right children of this node (or null
		//if the child is missing)
		public TreeNode leftChild, rightChild;
		//Pointer to the parent of this node (or null if this node
		//is the root of the tree
		public TreeNode parent;
		//Height of the subtree rooted at this node.
		//This value is only valid after the computeHeight() method
		//has been called on this node or one of its ancestors.
		public int height; 
		
		public TreeNode(String value){
			nodeValue = value;
			leftChild = rightChild = null;
			parent = null;
			height = 0;
		}
		
		/* recomputeHeight()
		   If the subtrees of this node have changed height, this method should
		   be called to adjust the height values of this node and its parents.
		   If the AVL properties hold, this method has O(log n) running time.
		*/
		public void recomputeHeight(){
			height = 0;
			if (leftChild != null){
				height = leftChild.height + 1;
			}
			if (rightChild != null){
				height = (rightChild.height + 1 > height)? rightChild.height + 1: height;
			}
			if (parent != null)
				parent.recomputeHeight();
		}
		
		
		/* Verification methods 
		   These are used by the testing code in main() to verify that the AVL
		   properties hold 
		*/
		
		//A field used by the verifyAVL process below.
		//This field will be overwritten during the verification process,
		//so your code should not use it for anything.
		private int vheight; 
		
		/* computeVHeight()
		   Set the vheight field for this node and all descendents.
		*/
		private void computeVHeight(){
			vheight = 0;
			if (leftChild != null){
				leftChild.computeVHeight();
				vheight = leftChild.vheight + 1;
			}
			if (rightChild != null){
				rightChild.computeVHeight();
				vheight = (rightChild.vheight + 1 > vheight)? rightChild.vheight + 1: vheight;
			}
		}
		
		/* verifyAVL()
		   Returns true if the subtree rooted at this node is an AVL tree.
		*/
		public boolean verifyAVL(){
			computeVHeight();
			
			ArrayDeque<TreeNode> S = new ArrayDeque<TreeNode>();
			
			TreeNode node, previousNode;
			node = this;
			while(node.leftChild != null){
				S.push(node);
				node = node.leftChild;
			}
			previousNode = node;
			while(!S.isEmpty()){
				node = S.pop();
				if (previousNode.nodeValue.compareTo(node.nodeValue) > 0)
					return false;
				int leftHeight = (node.leftChild == null)? -1: node.leftChild.vheight;
				int rightHeight = (node.rightChild == null)? -1: node.rightChild.vheight;
				if (leftHeight - rightHeight > 1 || rightHeight - leftHeight > 1)
					return false;
				previousNode = node;
				if (node.rightChild != null){
					node = node.rightChild;
					while (node.leftChild != null){
						S.push(node);
						node = node.leftChild;
					}
					S.push(node);
				}
			}
			return true;
		}
		
		/* verifyBST()
		   Returns true if the subtree rooted at this node is a binary search tree.
		*/
		public boolean verifyBST(){
			
			ArrayDeque<TreeNode> S = new ArrayDeque<TreeNode>();
			
			TreeNode node, previousNode;
			node = this;
			while(node.leftChild != null){
				S.push(node);
				node = node.leftChild;
			}
			previousNode = node;
			while(!S.isEmpty()){
				node = S.pop();
				if (previousNode.nodeValue.compareTo(node.nodeValue) > 0)
					return false;
				previousNode = node;
				if (node.rightChild != null){
					node = node.rightChild;
					while (node.leftChild != null){
						S.push(node);
						node = node.leftChild;
					}
					S.push(node);
				}
			}
			return true;
		}
		
	}
	/* TreeError(T, format, args)
	   Prints the provided error message, possibly printing the contents of the
	   provided tree, then exits the program.
	*/
	public static void TreeError(AVLTree T, String format, Object... args){
		System.out.print("ERROR: ");
		System.out.printf(format,args);
		if (T.getTreeSize() <= PrintTreeSizeThreshold){
			System.out.printf("Tree contents:\n");
			T.printTree();
		}
		System.exit(1);
	}

	/* main()
	   Contains code to test the tree methods. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
		Scanner s;
		boolean interactiveMode = false;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			interactiveMode = true;
			s = new Scanner(System.in);
		}
		s.useDelimiter("\n");
		if (interactiveMode){
			System.out.printf("Enter a list of strings to store in the tree, one per line.\n");
			System.out.printf("To end the list, enter '###'.\n");
		}else{
			System.out.printf("Reading tree values from %s.\n",args[0]);
		}
		
		Vector<String> insertValues = new Vector<String>();
		Vector<String> searchValues = new Vector<String>();		
		String nextWord;
		
		while(s.hasNext() && !(nextWord = s.next().trim()).equals("###"))
			insertValues.add(nextWord);
		System.out.printf("Read %d strings.\n",insertValues.size());
		
		if (interactiveMode){
			System.out.printf("Enter a list of strings to search for in the tree, one per line.\n");
			System.out.printf("To end the list, enter '###'.\n");
		}else{
			System.out.printf("Reading search values from %s.\n",args[0]);
		}	
		
		while(s.hasNext() && !(nextWord = s.next().trim()).equals("###"))
			searchValues.add(nextWord);
		System.out.printf("Read %d strings.\n",searchValues.size());
		
		AVLTree T = new AVLTree();

		long startTime, endTime;
		double totalTimeSeconds;
		
		startTime = System.currentTimeMillis();

		for(int i = 0; i < insertValues.size(); i++){
			String insertElement = insertValues.get(i);
			TreeNode insertedNode = T.insert(insertElement);
			if (insertedNode == null || insertedNode.nodeValue == null || !insertedNode.nodeValue.equals(insertElement))
				TreeError(T,"Inserting \"%s\": Returned node is invalid or does not contain correct value.\n",insertElement);


			if (TestAVLAfterEachInsertion && !T.getRoot().verifyAVL()){
				TreeError(T,"Inserting \"%s\": AVL properties do not hold after insertion.\n",insertElement);
			}else if (TestBSTAfterEachInsertion && !T.getRoot().verifyBST()){
				TreeError(T,"Inserting \"%s\": Tree is not a binary search tree after insertion.\n",insertElement);
			}
		}
		endTime = System.currentTimeMillis();
		totalTimeSeconds = (endTime-startTime)/1000.0;
		
		if (!T.getRoot().verifyBST()){
			TreeError(T,"Tree is not a binary search tree after all insertions.\n");
		}else if (!T.getRoot().verifyAVL()){
			TreeError(T,"AVL properties do not hold after all insertions.\n");
		}
		
		System.out.printf("Inserted %d elements.\n Total Time (seconds): %.2f\n\n",insertValues.size(),totalTimeSeconds);
		
		int treeSize = T.getTreeSize();
		int treeHeight = T.getTreeHeight();
		if (treeSize <= PrintTreeSizeThreshold){
			System.out.printf("Tree contents:\n");
			T.printTree();
		}
		System.out.printf("Tree contains %d nodes and has height %d.\n",treeSize,treeHeight);
		
		
		int foundCount = 0;
		int notFoundCount = 0;
		startTime = System.currentTimeMillis();

		for(int i = 0; i < searchValues.size(); i++){
			String searchElement = searchValues.get(i);
			TreeNode foundNode = T.find(searchElement);
			if (foundNode == null)
				notFoundCount++;
			else
				foundCount++;
			if (foundNode != null && !foundNode.nodeValue.equals(searchElement)){
				TreeError(T,"Search for \"%s\": Returned node does not contain search string.\n",searchElement);

			}
				
			//If the element was found, delete the found node from the tree.
			if (foundNode != null){
				T.remove(foundNode);
				//After removal, try searching for the node again (such a search should now be unsuccessful)
				foundNode = T.find(searchElement);
				if (foundNode != null){
					TreeError(T,"Deleting \"%s\": Element is still contained in tree after deletion.\n",searchElement);

				}
				if (TestAVLAfterEachDeletion && !T.getRoot().verifyAVL()){
					TreeError(T,"Deleting \"%s\": AVL properties do not hold after deletion.\n",searchElement);
				}else if (TestBSTAfterEachDeletion && !T.getRoot().verifyBST()){
					TreeError(T,"Deleting \"%s\": Tree is not a binary search tree after deletion.\n",searchElement);
				}
			}
		}
		endTime = System.currentTimeMillis();
		totalTimeSeconds = (endTime-startTime)/1000.0;
		
		if (T.getRoot() != null && !T.getRoot().verifyBST()){
			TreeError(T,"Tree is not a binary search tree after all deletions.\n");
		}else if (T.getRoot() != null && !T.getRoot().verifyAVL()){
			TreeError(T,"AVL properties do not hold after all deletions.\n");
		}
		
		System.out.printf("Searched for %d items (%d found and deleted, %d not found).\nTotal Time (seconds): %.2f\n",
							searchValues.size(),foundCount,notFoundCount,totalTimeSeconds);
	}
}