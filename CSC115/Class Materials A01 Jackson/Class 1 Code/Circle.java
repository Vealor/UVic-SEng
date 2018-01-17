public class CircleA {

	public static final double PI = 3.14159265359;

	public static void main (String[] args) {

		//program entry point

		double radius = 17.3;

		double circumference = 2*PI*radius;
		double area = PI * radius*radius;

		System.out.println("The circumference is " + circumference);
		System.out.println("The area is " + area);

	}
}