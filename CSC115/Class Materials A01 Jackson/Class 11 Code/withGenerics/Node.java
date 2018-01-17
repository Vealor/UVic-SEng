public class Node<T> {
	  private T item;
	  private Node<T> next;
	  // constructors, accessors, and mutators

	  public Node() {
		  this.next = null;
	  }

	  public Node(T newItem) {
		  this.item = newItem;
		  this.next = null;
	  }

	  public Node(T newItem, Node<T> newNext) {
	  		  this.item = newItem;
	  		  this.next = newNext;
	  }

	  public T getItem() {
		  return this.item;
	  }

	  public void setItem(T newItem) {
		  this.item = newItem;
	  }

	  public Node<T> getNext() {
		  return this.next;
	  }

	  public void setNext( Node<T> newNext) {
		  this.next = newNext;
	  }
}
