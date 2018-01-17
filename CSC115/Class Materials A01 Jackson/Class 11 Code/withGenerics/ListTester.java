// This is a program that is quite simply designed to test the LinkedList class!
// Written by LillAnne Jackson January 31, 2013

public class ListTester {

	public static void main(String [] args) {

		// create a list to store some items

		LinkedList<Integer> classSize = new LinkedList<Integer>();
		// Then add a few items
		classSize.addFront(133);
		// Then add a few items
		classSize.addFront(76);

		// Then add a few items
		classSize.addFront(237);

		// Show the resulting list
		System.out.println(classSize.toString());

		LinkedList<Double> grades = new LinkedList<Double>();
		// Then add a few items
		grades.addFront(133.0);
		// Then add a few items
		grades.addFront(76.0);

		// Then add a few items
		grades.addFront(237.0);

		// Show the resulting list
		System.out.println(grades.toString());

		LinkedList<String> names = new LinkedList<String>();
		// Then add a few items
		names.addFront("asdf");
		// Then add a few items
		names.addFront("ahdleyr");

		// Then add a few items
		names.addFront("sleihs");

		// Show the resulting list
		System.out.println(names.toString());



	}
}
