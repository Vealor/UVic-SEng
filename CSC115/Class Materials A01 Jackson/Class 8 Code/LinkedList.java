// Look for: the **Recursive** sum method ! LJ

public class LinkedList implements IntegerList {

	private Node head;

	public LinkedList() {
		this.head = null;
	}


	//Determine whether a list is empty
	private boolean isEmpty() {
		if (this.head == null) return true;
		else return false;
	}

	//Determine the number of items in a list
	public int size() {

		if (isEmpty()) return 0;

		// So, it is not an empty list.  There is at least 1 node;
		int count = 1;
		Node curr = head;

		while ( curr.getNext() != null) {
			count++;
			curr = curr.getNext();
		}
		return count;
	}


	//Add an item at the beginning of the list
	public void addFront(int x) {

		if (isEmpty()) {
			Node theNode = new Node(x) ;
			head = theNode;
		}
		else {
			Node theNode = new Node(x, head);
			head = theNode;
		}
	}

	//Retrieve (get) the item at a given position in the list
	public int get(int pos)  {

		if (isEmpty()) return -1;

		Node curr = head;

		for (int i = 0; curr != null && i < pos; i++) {
			curr = curr.getNext();
		}

		if (curr == null) return -1;
		else return curr.getItem();

	}

	//Add an item at the end of the list
	public void addBack(int x) {

		//make the new node
		Node tempNode = new Node(x);

		if (isEmpty()) { // Empty list: add the one node to the head (and tail)
			head = tempNode;
		}

		else {
			// make curr that points at Nodes and will track along to the back (tail)
			Node curr = head;

			while (curr.getNext() != null) {
					curr = curr.getNext();
			}
			//then put the new node at the end
			curr.setNext(tempNode);
		}
	}


	//Remove the item at the end of the list
	public void removeTail() {
		// if the list is empty there is nothing to remove - do nothing!
		// otherwise:
		if (! isEmpty() ) {
			// make a curr that will find the tail - starting from the headbv
			Node curr = this.head;

			if (curr.getNext() == null) {
				//there is only one Node in the linked list
				//make the list into an empty one
				head = null;
			}
			else  {
				// set curr to the second node and add a backTracker, behind it
				Node backTracker = curr;
				curr = curr.getNext();

				// Then step through the list until curr points at the last Node
				//   and backTracker points at the second last node
				while (curr.getNext() != null) {
					backTracker = curr;
					curr = curr.getNext();
				}

				//Okay, now we can take off the last node by setting backTracker's next to null
				backTracker.setNext(null);
			}
		}
	}

	public int sum() {
		return sum(head);
	}

	public int sum(Node startAt) {

		if (startAt.getNext() == null) return startAt.getItem();
		else
			return sum(startAt.getNext()) + startAt.getItem();
	}

	public String toString() {
		String result = "";

		//Start a curr at the head
		Node curr = this.head;

		// as long as list is not empty add first item to result.
		if (! isEmpty() ) {
			result += curr.getItem();
		}

		//  track through all nodes until curr is null (end of list)

		while (curr.getNext() != null) {
			curr = curr.getNext();
			result += " ==> ";
			result += curr.getItem();
		}

		return result;
	}

	//Other methods that would be nice to write
		//Create an empty list
			//public void createEmptyList() {}
		//Remove the item at the beginning of the list
			//public void removeFront() {
		//Remove the item at the end of the list
			//public void removeBack() {

		//Add an item at a given position in the list
			//public void add(int item, int position) {}
		//Remove the item at a given position in the list
			//public void remove(int position) {}

}


