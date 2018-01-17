
public abstract class ClosedShape extends Shape {
	boolean polygon;
	int numPoints;
	int[] xVertices;
	int[] yVertices;
	int x,y,width, height;

	public ClosedShape(boolean isPolygon, int numPoints) {
		super(0,0);
		this.polygon = isPolygon;
		this.numPoints = numPoints;
	}

	public ClosedShape(boolean isPolygon, int numPoints, int[] x, int[] y) {
		super(x[0],y[0]);
		this.polygon = isPolygon;
		if (isPolygon) {
			this.numPoints = numPoints;
			xVertices = new int[numPoints];
			yVertices = new int[numPoints];
		}
		else { // its an oval - define bounding box
			this.numPoints = 4;
			this.x = x[0];
			this.y = y[0];
			width = x[1];
			height = y[1];
		}
	}

	public void setXYCoords(int[] x, int[] y){
		this.xVertices = x;
		this.yVertices = y;
	}

	// Gives access to the width attribute
	public void setWidth(int width){
		//write me
	}

	// Gives access to the height attribute
	public void setHeight(int height) {
		//write me
	}

	public void draw() {
		if (polygon) {
			// provide a way to draw
		}
		else {
			// provide a way to draw
		}

	}

	public abstract double Area();
	public abstract double Perimeter();


}