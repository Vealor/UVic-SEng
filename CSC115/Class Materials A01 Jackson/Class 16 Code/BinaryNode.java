public class BinaryNode<T> {
	  private T item;
	  private BinaryNode<T> leftChild;
	  private BinaryNode<T> rightChild;

	  // constructors, accessors, and mutators

	  public BinaryNode() {
		  this.leftChild = null;
		  this.rightChild = null;
	  }

	  public BinaryNode(T newItem) {
		  this.item = newItem;
		  this.leftChild = null;
		  this.rightChild = null;
	  }

	  public BinaryNode(T newItem, BinaryNode<T> newleftChild, BinaryNode<T> newrightChild) {
	  		  this.item = newItem;
	  		  this.leftChild = newleftChild;
	  		  this.rightChild = newrightChild;
	  }

	  public T getItem() {
		  return this.item;
	  }

	  public void setItem(T newItem) {
		  this.item = newItem;
	  }

	  public BinaryNode<T> getLeftChild() {
		  return this.leftChild;
	  }

	  public void setLeftChild( BinaryNode<T> newLeftChild) {
		  this.leftChild = newLeftChild;
	  }
	  public BinaryNode<T> getRightChild() {
		  return this.rightChild;
	  }

	  public void setRightChild( BinaryNode<T> newRightChild) {
		  this.rightChild = newRightChild;
	  }

}
