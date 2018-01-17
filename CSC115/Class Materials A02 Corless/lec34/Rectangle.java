public class Rectangle extends Shape
{
	public Rectangle(double l, double w)
	{
		length = l;
		width = w;
	}

	public double getArea()
	{
		return length*width;
	}

	public	String getShapeName()
	{
		return "Rectangle";
	}

	protected	double length;
	protected	double width;
}
