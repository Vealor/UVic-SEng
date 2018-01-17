public class Rectangle extends ClosedShape{

	// (x,y) is upper left corner
	public Rectangle(int x, int y, int width, int height) {
		super(true, 4);
		int [] xValues = {x, x, x+width, x+width};
		int [] yValues = {y, y+height, y+height, y};
		super.setXYCoords(xValues, yValues);
		super.setInitX(x);
		super.setInitY(y);
		super.setWidth(width);
		super.setHeight(height);
	}

	public double Area(){
		if (polygon) {
			return 0.0;//fix
		}
		else {
			return 0.0; //fix
		}
	}
	public double Perimeter() {
		if (polygon) {
			return 0.0;//fix
		}
		else {
			return 0.0; //fix
		}
	}

}