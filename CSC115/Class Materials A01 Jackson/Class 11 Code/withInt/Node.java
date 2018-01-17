public class Node {
	  private int item;
	  private Node next;
	  // constructors, accessors, and mutators

	  public Node() {
		  this.next = null;
	  }

	  public Node(int newItem) {
		  this.item = newItem;
		  this.next = null;
	  }

	  public Node(int newItem, Node newNext) {
	  		  this.item = newItem;
	  		  this.next = newNext;
	  }

	  public int getItem() {
		  return this.item;
	  }

	  public void setItem(int newItem) {
		  this.item = newItem;
	  }

	  public Node getNext() {
		  return this.next;
	  }

	  public void setNext( Node newNext) {
		  this.next = newNext;
	  }
}
