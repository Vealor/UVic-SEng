public class ObjectDemo{
	public static void change(Student studentA, int i){
		studentA.setMark(i);
		System.out.print("\nIn change(), studentA = ");
		System.out.println(studentA);
	}
	
	public static void changeOther(Student studentB, int i){
		studentB=new Student(studentB);
		studentB.setMark(i);
		System.out.print("\nIn changeOther(), studentB = ");
		System.out.println(studentB);	
	}
	
	public static void main(String[] args){
		Student student1=new Student("V0011111",11);
		System.out.println("In Main, student1 = "+student1);
		change(student1, 88);
		System.out.print("\nIn main, after change(), student1 = ");
		System.out.println(student1);
		changeOther(student1, 75);
		System.out.print("\nIn main, after changeOther(), student1 = ");
		System.out.println(student1);
	}
}
