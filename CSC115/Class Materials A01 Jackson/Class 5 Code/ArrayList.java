public class ArrayList implements IntegerList  {

	// attributes
	private int max = 5;
	private int[] myList;
	private int currentSize;

	// Constructor(s)
	public ArrayList() {
		myList = new int[max];
		currentSize = 0;
	}

	/*
	 * PURPOSE:
	 *   Add the element x to the front of the list.
	 *
	 * PRECONDITIONS:
	 *   None.
	 *
	 * Examples:
	 *
	 * If l is {1,2,3} and l.addFront(9) returns, then l is {9,1,2,3}.
	 * If l is {} and l.addFront(3) returns, then l is {3}.
	 */
	public void addFront (int x) {

		// When the array is full: make it bigger
		if (currentSize >= max)  {
			increaseMax();
		}
		// when currentSize > 0 move every element over by 1
		if (currentSize > 0 ) {
			for (int i = currentSize -1; i >= 0; i--) {
				myList[i+1] = myList[i];
			}
		}

		// add new item at front of list & increment currentSize
		myList[0] = x;
		currentSize++;

	}


	/*
	 * PURPOSE:
	 *   Add the element x to the back of the list.
	 *
	 * PRECONDITIONS:
	 *   None.
	 *
	 * Examples:
	 *
	 * If l is {1,2,3} and l.addBack(9) returns, then l is {1,2,3,9}.
	 * If l is {} and l.addBack(9) returns, then l is {9}.
	 */
	public void addBack (int x){
		// when currentSize < MAX
		if (currentSize >= max) {
			// otherwise make the array bigger:
			increaseMax();
		}
		// add new item to the back of the list & increase currentSize
		myList[currentSize++] = x;
	}

	/*
	 * PURPOSE:
	 *   Increase the size of the array that holds the elements of the list.
	 *    It adds 5 more locations.
	 *   This is a private method because it is only to be used inside ArrayList
	 *
	 * PRECONDITIONS:
	 *   The array is full.
	 *
	 * Examples:
	 * If the array is {1,2,3,4,5}, it will become {1,2,3,4,5,0,0,0,0}.
	 */
	private void increaseMax (){
		//  make the array bigger(by 5)!!
		max += 5;

		// make a new array
		int[] newList = new int[max];
		// copy the previous array into the new array
		for (int i = 0; i < currentSize; i++)
			newList[i] = myList[i];

		// the new list becomes the list:
		myList = newList;
	}

	/*
	 * PURPOSE:
	 *	Return the number of elements in the list
	 *
	 * PRECONDITIONS:
	 *	None.
	 *
	 * Examples:
	 *	If l is {7,13,22} l.size() returns 3
	 *	If l is {} l.size() returns 0
	 */
	public int size() {
		return currentSize;
	}

	/*
	 * PURPOSE:
	 *   Return the element at position pos in the list.
	 *
	 * PRECONDITIONS:
	 *	pos >= 0 and pos < l.size()
	 *
	 * Examples:
	 *	If l is {67,12,13} then l.get(0) returns 67
	 *	If l is	{67,12,13} then l.get(2) returns 13
	 *	If l is {92} then the result of l.get(2) is undefined.
	 *
	 */
	public int  get (int pos) {
		return myList[pos];
	}


	/*
	 * PURPOSE:
	 *	Return a string representation of the list
	 *
	 * PRECONDITIONS:
	 *	None.
	 *
	 * Examples:
	 *	If l is {1,2,3,4} then l.toString() returns "{1,2,3,4}"
	 *	If l is {} then l.toString() returns "{}"
	 *
	 */
	public String toString() {
		String returnValue = "{";

		for (int i = 0; i < currentSize; i++) {
			returnValue += myList[i] ;
			if (i != currentSize - 1) returnValue += ",";
		}

		returnValue +="}";

		return returnValue;
	}

}
