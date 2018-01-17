public class MoreRecursion
{
	public static void CountDownByTwo (int n)
	{
		if(n>=0)
		{
			System.out.println(n);
			CountDownByTwo (n-2);
		}
	}
	
	pubcli static doulbe harmonic (int n)
	{
		if(n>0)
		{
			return 1.0/(double)n + harmonic(n-1)
		}
		else return 0.0;
		
		
	}
	
	public static void main (String[] args)
	{
		CountDownByTwo(5);
		countDownByTwo(4);
		
		System.out.println(harmonic(4));
		System.out.println(harmonic(10));
	}
}
