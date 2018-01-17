/**
 name: Student.java
 purpose: a class stores and processes student number, mark and letter grade
 author: Victoria Li
 last modified: Jan. 16th, 2014
 list of credits: none
*/
public class Student
{
	private String studentNum;
	private int mark;
	private String letterGrade;
	/**
	 Initializes a newly created Student object whose studentNum is "empty", mark is 0 and the letterGrade is "empty".
	*/
	public Student()
	{
		studentNum="empty";
		mark=0;
		letterGrade="empty";
	}
	
	/**
	 Construct a new Student object whose studentNum is set to id, mark is set to grade and the letterGrade is computed by calling the method calculateLetterGrade().
	*/
	public Student(String id, int grade)
	{
		studentNum=new String(id);
		mark=grade;
		letterGrade=calculateLetterGrade();
	}
	
	/**
	 Copy construct. Construct a new Student object with the same value as "other".
	*/
	public Student(Student other)
	{
		studentNum=new String(other.studentNum);
		mark=other.mark;
		letterGrade=calculateLetterGrade();
	}
	
	/**
	 returns the studentNum;
	*/
	public String getStudentNum()
	{
		return studentNum;
	}
	
	/**
	 set the studentNum to parameter strN 
	*/
	public void setStudentNum(String strN)
	{
		studentNum=new String(strN);
	}
	
	/**
	 returns mark
	*/
	public int getMark()
	{
		return mark;
	}
	
	/**
	 set mark to parameter grade
	*/
	public void setMark(int grade)
	{
		mark=grade;
		letterGrade=calculateLetterGrade();
	}
	
	/**
	 returns the letterGrade
	*/
	public String getLetterGrade()
	{
		return letterGrade; 
	}
	
	/**
	 returns the String representation of the Student object
	*/
	public String toString()
	{
		return studentNum+"\t"+mark+"\t"+letterGrade;
	}
	
	/**
	 converts the mark to a letter grade and returns it
	*/
	private String calculateLetterGrade()
	{
		if (mark >=90) return "A+";
		if (mark >=85) return "A";
		if (mark >=80) return "A-";
		if (mark >=77) return "B+";
		if (mark >=73) return "B";
		if (mark >=70) return "B-";
		if (mark >=65) return "C+";
		if (mark >=60) return "C";
		if (mark >=50) return "D";
		return "F";
	}
}