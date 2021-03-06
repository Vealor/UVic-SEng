/* Choclate Tester 
 * chocolatetester.java
 *
 * A breaking program for IntegerArrayList.java
 *
 * Only the necessary test cases were implemented to show the two visible bugs.
 *
 * Jakob M Roberts
 * v00484900
 * CSC115
 *
 */
 
public class chocolatetester
{
	
	public static void chocolateOne()
	{
		IntegerList l = new IntegerArrayList();
		System.out.println("TEST ONE: Testing Clear Method" +
				   "\n------------------------------");
		System.out.println("Noted Problem: does not clear " + 
				     "list, only sets count to 0.");
		System.out.println("Testing...");
		System.out.println("Empty: " + l);
		l.addFront(25);
		l.addFront(7);
		l.addFront(31);
		l.addBack(3);
		l.addBack(53);
		l.addBack(27);
		System.out.println("Filled: " + l);
		System.out.println("Now Clearing");
		l.clear();
		System.out.println("Is Empty?: " + l);
		System.out.println("Yes, because of toString implementing 'count'");
		System.out.println("Can get 2nd element?: " + l.get(1));
		System.out.println("Problem!!" + 
				   "   Should not be able to get element if list " +
				   "is clear!");	
	}

	public static void chocolateTwo()
	{
		IntegerList l = new IntegerArrayList();
		System.out.println("\n\nTEST TWO: Testing GET Method" +
				   "\n------------------------------");
		System.out.println("Noted Problem: does not clear " + 
				     "list, only sets count to 0.");
		System.out.println("Testing...");
		System.out.println("Empty: " + l);
		//l.addFront(25);
		//l.addFront(7);
		//l.addFront(31);
		//l.addBack(3);
		//l.addBack(53);
		l.addBack(27);
		System.out.println("Filled: " + l);
		System.out.println(l.size());
		l.remove(27);
		System.out.println("Removed 27: " + l);
		System.out.println(l.size());
		System.out.println(l.get(0));
		
	}
	
	public static void main(String[] args)
	{
		chocolateOne();
		chocolateTwo();
		
		
		
	}
}
 
