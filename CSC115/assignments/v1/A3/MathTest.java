// NOTE: This program will not compile until method stubs or methods are added!

import java.io.*;
import java.util.*;

public class MathTest {
	public static void main(String[] args) {
		System.out.println("POWER TESTER: base^n :\n");
		Scanner keyboard = new Scanner(System.in);

		// Test Data 1 2^4
		double x = 2.0;
		long n = 4;
		System.out.println("\n"+x+"^"+n+" is " +powerOne(x,n));
		System.out.println(x+"^"+n+" is " +powerTwo(x,n));
		System.out.println(x+"^"+n+" is " +powerThree(x,n));

		// Test Data 2: 2^5
		n = 5;
		System.out.println("\n"+x+"^"+n+" is " +powerOne(x,n));
		System.out.println(x+"^"+n+" is " +powerTwo(x,n));
		System.out.println(x+"^"+n+" is " +powerThree(x,n));

		// Test your own data:
		System.out.println("\n\n YOUR TEST INPUT: ");

		//repeat loop controller
		char repeat = 'n';

		do {
			//get inputs
			System.out.print("base==> ");
			x = keyboard.nextDouble();
			System.out.print("power(n)==>");
			n = keyboard.nextLong();

			//calculate & output results
			System.out.printf("\n%.0f^%d is %.0f", x,n,powerOne(x,n));
			System.out.printf("\n%.0f^%d is %.0f", x,n,powerTwo(x,n));
			System.out.printf("\n%.0f^%d is %.0f\n", x,n,powerThree(x,n));

			//decide if more tests are needed
			System.out.print("\n\tRepeat (y/n)? ");
			repeat = keyboard.next().charAt(0);
		} while (repeat == 'y' || repeat == 'Y');
	}

	// Your powerOne() method goes here

	// Your powerTwo() method goes here

	// Your powerThree() method goes here
}


