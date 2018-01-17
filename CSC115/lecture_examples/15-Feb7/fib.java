

/////////////////
/*
F(0)=1
F(1)=1
Fn=F(n-2)+F(n-1)
*/

static int fib(int n)
{
	if(n==0||n==1)
		return 1;
	else
		return fib(n-2)+fib(n-1);
}
/* DO NOT DO THIS , this is O(2^N), gets big REALLY fast */
		
// Fibbonacci on midterm using a loop!!
