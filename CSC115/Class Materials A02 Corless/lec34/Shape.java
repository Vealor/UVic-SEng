public abstract class Shape
{
	public abstract double	getArea();

	public void setX (double newX)
	{
		xPos = newX;
	}

	public void setY (double newY)
	{
		yPos = newY;
	}

	public double getX() 
	{
		return xPos;
	}
		
	public double getY()
	{
		return yPos;
	}

	public String toString()
	{
		String s = getShapeName();
		s+= " at position (" + xPos + "," + yPos + ") area: " + getArea();
		return s;
	}

	protected abstract String getShapeName();
	
	protected double	xPos;
	protected double	yPos;
}
