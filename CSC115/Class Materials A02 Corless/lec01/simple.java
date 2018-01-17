class simple
{

	public static int doWhat (int x)
	{
		x = x + 10;
		return x;
	}

	public static void doSomething(Student s)
	{
		s.name = "joe";
	}

	public static void main (String[] args)
	{
		int x;
		int y;

		x = 10;
		
		y = doWhat(x);

		Student s;
		s = new Student ("bob");

		doSomething(s);

		System.out.println(s.name);
		System.out.println("hello world");
		System.out.println(x);

	}
}
