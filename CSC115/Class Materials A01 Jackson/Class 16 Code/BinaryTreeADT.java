public interface BinaryTreeADT<T> {
	//createBinaryTree (rootItem, leftTree, rightTree);
	public void setRootItem(T newItem);
	public void attachLeft(BinaryNode<T> newItem) throws TreeException;
	public void attachRight(BinaryNode<T> newItem) throws TreeException;
	public void attachLeftSubtree(BinaryTree<T> leftTree) throws TreeException;
	public void attachRightSubtree(BinaryTree<T> rightTree) throws TreeException;
	public void detachLeftSubtree(BinaryNode<T> fromHere) throws TreeException;
	public void detachRightSubtree(BinaryNode<T> fromHere) throws TreeException;
}