public class Student {
	private String 	name;
	private long 	id;
	private float 	midterm;
	private float 	finalExam;
	
	public Student(String name, long id) {
		this.name = name;
		this.id = id;
	}
	public void setMidterm (float midterm) {
		this.midterm = midterm;
	}
	public void setFinal (float finalExam) {
		this.finalExam = finalExam;
	}
	public double getExamAverage() {
		return (this.midterm + this.finalExam) / 2.0;
	}
	public String toString(){
		return name + "," + id + "," + midterm + "," + finalExam;
	}
}

