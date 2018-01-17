
public abstract class Shape {
	int initX, initY;

	public Shape() {
		initX = 0;
		initY = 0;
	}

	public Shape(int x, int y) {
		initX = x;
		initY = y;
	}

	public void setInitX (int x) {
		initX = x;
	}

	public void setInitY (int y) {
		initY = y;
	}

	public abstract void draw();
	public abstract double Area();
	public abstract double Perimeter();

	public void Move(int deltaX, int deltaY){
		//future work
	}
}
