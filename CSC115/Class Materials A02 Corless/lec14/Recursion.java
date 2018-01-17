/*
 * Recursion.java
 *
 * Some sample recursive methods
 */

public class Recursion
{
	public static void printspaces (int number)
	{
		for (int i =0; i < number; i++)
		{
			System.out.print(" ");
		}
	}

/*
	The method below is changed from the following
	solution:

	public static int sum (int n)
	{
		int result;
		int rest;

		if (n == 1)
		{
			result = 1;
		}
		else
		{
			rest = sum (n-1);

			result =  n + rest;
		}		
		return result;
	}

	to include print statements that show the 
	recursion in action.	
*/

	public static int sum (int n, int indent)
	{
		int result;
		int rest;

		printspaces(indent);
		System.out.println("starting sum(" + n + ")");
		if (n == 1)
		{
			result = 1;
		}
		else
		{
			printspaces(indent);
			System.out.println("calling sum(" + (n - 1) + ")" );

			rest = sum(n-1, indent + 4);

			printspaces(indent);
			System.out.print("in sum(" + n);
			System.out.println(") : sum(" + (n - 1) + ") returned " + rest );
			
			result = n + rest;
		}
		printspaces(indent);
		System.out.println("finishing sum(" + n + ") result is " + result );
		return result;
	}

	public static int power (int base, int pow)
	{
		if (pow == 0)
		{
			return 1;
		}
		else
		{
			return base * power(base, pow -1);
		}
	}
	public static void main (String[] args)
	{
		System.out.println("sum(4) is : " + sum(4,0));
		System.out.println("power(2,4) is : " + power(2,4));
	}
}
