public class Ellipse extends Shape
{
	public Ellipse (double a, double b)
	{
		this.a = a;
		this.b = b;
	}

	public String getShapeName()
	{
		return "Ellipse";
	}

	public double getArea()
	{
		return Math.PI*a*b;
	}

	protected double a;
	protected double b;

}
