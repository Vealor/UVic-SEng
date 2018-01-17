public class Square implements Area
{
	private	double length;
	private	double width;

	public Square (double l, double w) {
		length = l;
		width = w;
	}

	public double getArea() {
		return length * width;
	}

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	public void setLength (double l) {
		length = l;
	}

	public void setWidth (double w) {
		width = w;
	}

	public String toString() {
		return "Square: length=" 
			+ length + " width=" + width;
	}
}
