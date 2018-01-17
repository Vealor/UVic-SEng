public class foo
{
	public static int sum(int n)
	{
		int result;
		int rest;
		
		if (n==1){
			result = 1;
		}else{
			rest = sum(n-1);
			result = n + rest;
		}
		return result;
	}
	
	public static int power(int n, int p){
		if(p==0)
			return 1;
		else
			return n * power(n, p-1);
	}
	
	public static int fibonacci(int n){
		if(n==0 || n==1)
			return 1;
		else
			return n + fibonacci
	
	public static void main(String[] args)
	{
		System.out.println(sum(3));
		System.out.println(power(2, 4));
		
		
	}
}
