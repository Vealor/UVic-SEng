// NAME: COMPLEXEXERCISER1
// PURPOSE:  create and manipulate Complex Number objects
// AUTHOR:  Jakob Roberts
// DATE: June 2013
// UPDATED:  January 2014
// CREDITS: LillAnne Jackson

import java.util.*;
import java.io.*;

public class ComplexExerciser1
{
	public static void main (String[] args) throws FileNotFoundException
	{
		int firstR = 2;
		int firstI = 4;
		int secondR = 4;
		int secondI = -5;
		Complex1 first = new Complex1(firstR,firstI);
		Complex1 second = new Complex1(secondR,secondI);
		
		System.out.println("First complex number:" + first);
		System.out.println("Second complex number:" + second);
		
		//
		
		File inComplex = new File("ComplexData.txt");
		Scanner ic = new Scanner(inComplex);
		
		int listLength = ic.nextInt();
		
		System.out.println("From File:");
		
		Complex1 comp;
		Complex1[] list = new Complex1[listLength];	
		int nextReal;
		int nextImaginary;
		
		for (int i=0;i<listLength;i++)
		{
			nextReal = ic.nextInt();
			nextImaginary = ic.nextInt();
			comp = new Complex1(nextReal,nextImaginary);
			list[i] = comp;
			System.out.println(list[i]);
		}
	}
}
	
