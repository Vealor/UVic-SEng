// NOTE: This program will not compile until method stubs or methods are added!


// NAME: a3Tester
// PURPOSE:  Solve Mathematical functions, like x^n and e^x iteratively and then recursively
//     and then more efficiently using recursion.
// AUTHOR:  LillAnne Jackson
// DATE: Januayr 2014
// CREDITS: The ideas for the problems: from the Carrano and Prichard textbook


import java.util.*;
import java.io.*;

public class a3Tester {

	// Include your powerOne() method here

	// Include your powerTwo() method here

	// Include your powerThree() method here

	// Include your factOne() method here

	// Include your factTwo() method here

	// Include your eOne() method here

	// Include your eTwo() method here

	// Include your eThree() method here


	public static boolean test(double result, Scanner testData) {
			return (Math.abs(result - testData.nextDouble()) < 0.00000000001);
	}

	public static void main (String [] args) throws FileNotFoundException{

		// File input
		Scanner in = new Scanner(new File("TestData.txt"));
		Scanner testIn = new Scanner(new File("TestResult.txt"));

		System.out.println(" M A T H   T E S T E R \n\n" );
		System.out.println(" Methods Written by: ??");
		System.out.println("   ID:  ??  February 2014");

		// First line of file is number of data points:
		int numPoints = in.nextInt();

	    for (int i=0; i < numPoints; i++ ) {

			//skip title line in file
			in.next();

			// Data for testing x^n methods
			double base = in.nextDouble();
			int exponent = in.nextInt();

			// x^n test calls
			System.out.println(" *********************************");
			System.out.println(" Iterative x^n with x= " + base + " and n= " + exponent + ": ");
			double data = powerOne(base, exponent);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			System.out.println(" *********************************");
			System.out.println(" Recursive x^n (#1) with x= " + base + " and n= " + exponent + ": ");
			data = powerTwo(base, exponent);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			System.out.println(" *********************************");
			System.out.println(" Recursive x^n (#2) with x= " + base + " and n= " + exponent + ": ");
			data = powerThree(base, exponent);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			// Data for testing n! methods
			//skip title line in file
			in.next();
			int n = in.nextInt();

			// x^n test calls

			System.out.println(" *********************************");
			System.out.println(" Iterative n! with n= " + n +  ": ");
			data = factOne((double)n);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			System.out.println(" *********************************");
			System.out.println(" Recursive n! with n= " + n + ": ");
			data = factTwo((double)n);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			// Data for testing e^x methods
			//skip title line in file
			in.next();
			double x = in.nextDouble();
			n = in.nextInt();

			// x^n test calls

			System.out.println(" *********************************");
			System.out.println(" Iterative e^x with x= " + x + "("+ n + " terms): ");
			data = eOne(x, n);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			System.out.println(" *********************************");
			System.out.println(" Recursive e^x (#1) with x= " + x + "("+ n + " terms): ");
			data = eTwo(x, n);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			System.out.println(" *********************************");
			System.out.println(" Recursive e^x (#2) with x= " + x + "("+ n + " terms): ");
			data = eThree(x, n);
			System.out.println("    " + data);
			System.out.println("  \t\t\t\t\tTest: " + test(data, testIn));

			//in.nextLine();
		}
	}


}


