//
// what.java
//
// What do you expect to happen when you run this program?
//
// What actually happens when you run this program?
//
// Can you explain what is going on?
//
public class what
{
	public static void main (String[] args)
	{
		int i = 0;
		int j = i + 1;

		while ( i < j)
		{
			i = i + 1;
			j = j + 1;
		}
		System.out.println(i);
		System.out.println(j);
	}
}
