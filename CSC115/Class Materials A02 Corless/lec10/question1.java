public class question1
{
	public static void main (String[] args)
	{
		Student s = new Student ("Jeff Johnson", 55212);
		// If you write a literal 75.8, the java compiler
		// treats this as type double
		// So here, you need to explictly say you mean
		// a floating point by writing 75.8f
		//
		// You wouldn't need to worry about those details on
		// an exam, but I wanted this code to actually compile
		// and run, so I've done that here.
		s.setMidterm(75.8f);
		s.setFinal(82.5f);

		System.out.println(s);
		System.out.println("Exam average is:" + s.getExamAverage());

	}
}
