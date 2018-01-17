public class GenericsDemo
{
	public static void main (String[] args)
	{
		List<String> l1 = new LinkedList<String>();
		l1.add("hello");
		l1.add("world");
		
		System.out.println(l1);

		List<Integer> l2 = new LinkedList<Integer>();
		l2.add(10);
		l2.add(20);
		l2.add(30);
	
		System.out.println(l2);

		try
		{
			System.out.println("The element at pos 0 is: " + l2.elementAt(0));
			System.out.println("The element at pos 5 is: " + l2.elementAt(5));
			System.out.println("The element at pos 2 is: " + l2.elementAt(2));
		}
		catch (java.util.NoSuchElementException e)
		{
			System.out.println("An error occured: " + e);
		}		
		
	}
}
