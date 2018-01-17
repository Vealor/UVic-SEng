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
	// runtime is O(N)
	public static double powerOne(double x, long n){
		double number = 1.00000;
		for(int i=0;i<n;i++){
			number = number * x;
		}
		return number;		
	}

	// Your powerTwo() method goes here
	// runtime is O(N)
	public static double powerTwo(double x, long n){
		double number=1.00000;
		if (n>=1){
			number = x * powerTwo(x,n-1);
		}
		return number;
	}

	// Your powerThree() method goes here
	// runtime is O(N!)
	public static double powerThree(double x, long n){
		/*System.out.print("\nADD ONE");
		double number = 1.00000;
		double backNum;
		if(n>1){
			backNum = powerThree(x,n/2);
			number = backNum*backNum;
			if(n%2 == 1){
				number = number*x;
			}
			return number;
		}
		if(n==1){
			return x;
		}
		return 1;
	}*/
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
}


