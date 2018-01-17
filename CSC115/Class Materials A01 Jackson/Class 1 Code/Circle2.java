public class CircleB {

	public static final double PI = 3.14159265359;

	public static void main (String[] args) {

		//program entry point

		double radius = 17.3;

		double area = PI * radius*radius;

		System.out.println("The circumference is " + calculateCircumference(radius));
		System.out.println("The area is " + calculateArea(radius));

	}

	public static double calculateArea (double radius) {
		// what does this do? radius = 10.0;
		double area = PI * radius*radius;
		return area;
	}

	public static double calculateCircumference (double radius) {
		double circumference = 2*PI*radius;
		return circumference;
	}
}