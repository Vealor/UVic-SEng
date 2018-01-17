// ComplexExerciser.java
// Author: L. Jackson
// Edit date: Sept 19, 2013
// Purpose: To demonstrate the use of the Complex1 class by instantiating Complex1 objects
// Credits: LillAnne Jackson
 */
import java.util.Scanner;
import java.io.*;

public class ComplexExerciser1{
	public static void main(String [] args) throws FileNotFoundException{

		// Program Intro:
		System.out.println("\nCOMPLEX EXERCISER 1 (L. Jackson)");

		// instantiate 2 complex numbers as specified by assignment #1
		Complex1 first = new Complex1(2,4);
		System.out.println("\nFirst complex number: " + first); //prints first complex number
		Complex1 second = new Complex1(4,-5);
		System.out.println("\nSecond complex number: " + second); //prints second comlex number

		// Open up an input file and input number of items in the file & create an array
		Scanner inFile = new Scanner(new File("ComplexData.txt"));
		int size = inFile.nextInt();
		Complex1 [] theNumbers = new Complex1[size];

		//Input size real,imaginary parts for Complex numbers & instantiate Complex1 object
		//  Note: assumes the input file is correct, does not check for correctness.
		for (int i = 0; i < size; i++) {
			int real = inFile.nextInt();
			int imaginary = inFile.nextInt();
			theNumbers[i] = new Complex1(real, imaginary);
		}

		// Output the complex numbers to the screen
		System.out.println("\nComplex Numbers from File:");
		System.out.println("==========================");
		for (int i = 0; i < size; i++) {
			System.out.println(theNumbers[i]);
		}

	}//main
}//ComplexExerciser1
