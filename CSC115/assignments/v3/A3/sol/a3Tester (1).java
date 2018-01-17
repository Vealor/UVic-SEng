// NOTE: This program will not compile until method stubs or methods are added!


// NAME: a3Tester
// PURPOSE:  Solve Mathematical functions, like x^n and e^x iteratively and then recursively
//     and then more efficiently using recursion.
// AUTHOR:  LillAnne Jackson
// DATE: January 2014
// CREDITS: The ideas for the problems: from the Carrano and Prichard textbook


import java.util.*;
import java.io.*;

public class a3Tester {
	// compute x^n iteratively
	static double powerOne(double x, long n){
		double number = 1.00000;
		for(int i=0;i<n;i++){
			number = number * x;
		}
		return number;		
	}

	// compute x^n recursively
	static double powerTwo(double x, long n){
		if(n==0||x==1){
			return 1;
		}
		return x * powerTwo(x,n-1);
	}
	
	// compute x^n recursively by cutting in half
	static double powerThree(double x, long n){
		double backNum = 1.0;
		if(n==0||x==1){
			return 1;
		}
		else{
			backNum = powerThree(x,n/2);
			backNum *= backNum;
			if(n%2 == 1){
				backNum = backNum*x;
			}
			return backNum;
		}
	}
	// compute x! iteratively
	static double factOne(double x){
		double number = 1.0;
		if(x==0){
			return 1.0;
		}else{
			for(int i=1;i<=x;i++){
				number *= i;
			}
		}
		return number;
	}

	// compute x! recursively
	static double factTwo(double x){
		if (x==0){
			return 1.0;
		}else{
			return x * factTwo(x-1);
		}	
	}

	// MacLaurin series to compute e^x iteratively
	static double eOne(double x, long n){
		double number = 0.0;
		for(int i=0;i<=n;i++){
			number += powerThree(x,(long) i)/factTwo((double) i);
		}
		return number;
	}
	// MacLaurin series to compute e^x recursively
	static double eTwo(double x, long n){
		if(n==0){
			return 1.0;
		}else{
			return eTwo(x,n-1) + (powerThree(x,n)/factTwo(n));
		}		
	}
	// MacLaurin series to compute e^x recursively using a special factoring formula
	static double eThree(double x, long n){
		return eThreeMath(x,n,1);
	}
	// does the recursive calculation for eThree
	private static double eThreeMath(double x, long n, long count){
		if(n+1==count){
			return 1.0;
		}else{
			return 1+(x/count)*eThreeMath(x,n,count+1);
		}
	}

	public static boolean test(double result, Scanner testData) {
			return (Math.abs(result - testData.nextDouble()) < 0.00000000001);
	}

	public static void main (String [] args) throws FileNotFoundException{

		// File input
		Scanner in = new Scanner(new File("TestData.txt"));
		Scanner testIn = new Scanner(new File("TestResult.txt"));

		System.out.println(" M A T H   T E S T E R \n\n" );
		System.out.println(" Methods Written by: Jakob Roberts");
		System.out.println("   ID:  v00484900  February 2014");

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


