public abstract class OpenShape extends Shape {
	double length;

	public OpenShape(double length) {
		this.length = length;
	}
	public OpenShape(double length, int x, int y) {
		super(x,y);
		this.length = length;
	}
	public double Area(){
		return 0.0;
	}
	public double Perimeter() {
		return length;
	}
}
