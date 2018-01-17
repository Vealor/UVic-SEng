public class Node
{
	int value;
	Node next;

	public Node () {
		value = 0;
		next = null;
	}

	public Node (int val) {
		value = val;
		next = null;
	}

	public void setNext (Node n) {
		next = n;
	}
	public void setValue(int v) {
		value = v;
	}
	
	public Node getNext() {
		return next;
	}
	public int getValue() {
		return value;
	}
}
