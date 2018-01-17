public class MoreRecursion
{
	public static void CountDownByTwo (int n)
	{
		if (n >= 0)
		{
			System.out.println(n);
			CountDownByTwo (n-2);
		}
	}
	
	public static double harmonic (int n)
	{
		if (n == 1)
			return 1.0;
		else
			return 1.0/(double)n + harmonic (n-1);
	}
	public static void main (String[] args)	
	{
		CountDownByTwo(5);
		CountDownByTwo(4);

		System.out.println(harmonic(5));
		System.out.println(harmonic(10));
	}
}
