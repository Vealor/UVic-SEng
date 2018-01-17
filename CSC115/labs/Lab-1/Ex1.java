class Ex1
{
	//declare a class constant
	public static final double PI=3.14;
	
	//calculate the area of a circle
	public static double compute_area(double r)
	{
		return (PI * r * r);
	}	
	
	public static void main (String[] args)
	{
		System.out.println("Hello, World!");
		double r=1;
		double area=compute_area(r);
		
		System.out.print("radius = " +r);
		System.out.println(" and area = " + area);
	}
}
