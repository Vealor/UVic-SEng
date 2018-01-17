// This is a program that is quite simply designed to test the ArrayList class!
// Written by LillAnne Jackson January 2014

public class DataAnalysis {

	public static void main(String [] args) {

		// create a list to store data accepted from an scanning device
		ArrayList dataScan = new ArrayList();

		// Then add a few items
		dataScan.addFront(27);
		dataScan.addFront(41);
		dataScan.addFront(55);

		// Show the resulting list
		System.out.println(dataScan.toString());

		// Then add a few items
		dataScan.addBack(33);
		dataScan.addBack(25);
		//dataScan.addBack(29);

		// Show the resulting list
		System.out.println(dataScan.toString());

		// Display the first, last and middle data item
		System.out.println("First = " + dataScan.get(0));
		System.out.println("Last = " + dataScan.get(dataScan.size()-1));
		System.out.println("Middle = " + dataScan.get(dataScan.size()/2));
	}
}
