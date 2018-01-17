/**
 name: GradeManager.java
 purpose: Process students' grades
 author: Jakob Roberts
 last modified: Jan. 24th, 2014
 list of credits: none
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GradeManager
{
	/**
	 Open file named "fName", parse the data and construct an 
	 array of Student objects.
	 return the array.
	*/
	public static Student[] getInput(String fName)throws FileNotFoundException
	{
		File inFile = new File(fName);
		Scanner in = new Scanner(inFile);
		int qtyS = in.nextInt();
		Student[] classList=new Student[qtyS];
		for(int i=0;i<qtyS;i++)
		{
			classList[i] = new Student(in.next(), in.nextInt());
		}
		
		return classList;
	}
	
	/**
	 An interactive method to get student number and mark from 
	 keyboard. If user enters the required information, a Student 
	 object is created and returned. "null" is returned otherwise.
	*/
	public static Student addStudent()
	{
		System.out.print("\nAdd a Student? Y/N ");
		Scanner console=new Scanner(System.in);
		String answer=console.nextLine().trim();
		answer=answer.toLowerCase();
		if (answer.charAt(0) == 'y')
		{
			System.out.print("Enter the student number: ");
			String strN=console.nextLine().trim();
			System.out.print("Enter the mark: ");
			int mark=console.nextInt();
			//console.close();
			return new Student(strN, mark);
		}
		console.close();
		return null;
	}
	
	/**
	 Calculate the average mark from the array of Student 
	 objects in "list", the number of students is stored in 
	 "count". The result is a decimal number. Return the average 
	 as a double number.
	*/
	public static double average(Student[] list, int count)
	{
		double average=0.0;
		for(int i=0;i<count;i++)
		{
			average += list[i].getMark();
		}
		return average/count;
	}
	
	/**
	 Print the student information to screen. "list" is an array 
	 of Student objects. "count" is the number of students in the 
	 array. The first line contains some descriptive words, then 
	 the student information is printed, one student per line. At 
	 the end, the class averaged is printed, preceded by some 
	 descriptive words. No return values.
	*/
	public static void report(Student[] list, int count)
	{
		for(int i=0;i<count;i++)
		{
			System.out.println(list[i]);
		}
		System.out.println("\nClass average: " + average(list,count));
	}
	
	/**
	 Double the size of the array "list". "list" is an array 
	 of Student objects. The values in "list" is copied to the 
	 newly created array. The enlarged array is returned.
	*/
	public static Student[] enlargeList(Student[] list)
	{
		int length=list.length;
		Student[] newList = new Student[length*2];
		for(int i=0;i<length;i++)
		{
			newList[i] = list[i];
		}
		return newList;
	}
	
	public static void main(String[] args)
	{
		if (args.length!=1)
		{
			System.out.println("Usage GradeManager inputFileName");
			System.exit(1);
		}
		Scanner inFile=null;
		try{
			//do the whole try block in the lab
			Student[] classList=getInput(args[0]);
			report(classList, classList.length);
			int size = classList.length;
			Student s;
			do
			{
				s = addStudent();
				
				//if(size==classList.length)
				//{
				//	classList = enlargeList(classList);
				//	System.out.println(classList.length);
				//}
				
				if(classList[classList.length-1]!=null)
				{
					classList = enlargeList(classList);
				}
				if(s!=null)
				{
					classList[size++]=s;
				}
			}while(s!=null);
			report(classList, size);
		}
		catch (FileNotFoundException fe)
		{
			fe.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(inFile!=null)
				inFile.close();
		}
	}
}