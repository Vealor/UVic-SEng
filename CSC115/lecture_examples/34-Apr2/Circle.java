public class Circle extends Ellipse
{

	public Circle (double radius)
	{
		super(radius,radius);
	}

	public String getShapeName()
	{
		return "Circle";
	}
}
