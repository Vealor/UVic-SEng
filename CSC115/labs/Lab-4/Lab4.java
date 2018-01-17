/**
  Learn recursion through examples
  P. 161 of your textbook
*/
public class Lab4
{
	/**
	 calculate the sum from 1 to n
	 @param n a positive number
	 @return the sum from 1 to n
	*/
	public static int sum(int n)
	{
		if (n==1)
		{
			return 1;
		}
		return n+sum(n-1);
	}
	
	/**
	 calculate the nth Fabonacci number
	 @param n a positive number
	 @return the nth Fabonacci number
	*/
	public static int fabonacci(int n)
	{
		if (n<=2)
		{
			return 1;
		}else{
			return fabonacci(n-1)+fabonacci(n-2);
		}
	}
	
	public static void main(String[] args)
	{
		int total=sum(5);
		System.out.println("The sum of 1 to 5 is: "+total);
		System.out.print("The first 8 Fabonacci numbers are: ");
		for(int i=1; i<8; i++)
		{
			System.out.print(fabonacci(i)+",");
		}
		System.out.println(fabonacci(8));
	}
}
