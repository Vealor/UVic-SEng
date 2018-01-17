// CSC 115 Lab B12
// Jakob Roberts
// v00484900
// Jan 17th, 2014

public class Lab1{
	private String name;
	private int age;
	private String sNumber;
	private String favColour;
	private String favFood;
	private boolean glasses;
	private boolean hasHat;

	public Lab1(){
		this.name = null;
		this.age = 0;
		this.sNumber = null;
		this.favColour = null;
		this.favFood = null;
	}
	public Lab1(String n, int a, String s, String c, String f, boolean g, boolean h){
		this.name = n;
		this.age = a;
		this.sNumber = s;
		this.favColour = c;
		this.favFood = f;
		this.glasses = g;
		this.hasHat = h;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getSNumber() {
		return this.sNumber;
	}
	public void setSNumber(String sNumber) {
		this.sNumber = sNumber;
	}
	
	public String getFavColour() {
		return this.favColour;
	}
	public void setFavColour(String favColour) {
		this.favColour = favColour;
	}
	
	public String getFavFood() {
		return this.favFood;
	}
	public void setFavFood(String favFood) {
		this.favFood = favFood;
	}
	
	public boolean getGlasses() {
		return this.glasses;
	}
	public void setGlasses(boolean glasses) {
		this.glasses = glasses;
	}
	
	public boolean getHasHat() {
		return this.hasHat;
	}
	public void setHasHat(boolean hasHat) {
		this.hasHat = hasHat;
	}
	
	public String toString(){
		String output = "";
		output += "            Name: " + name + "\n";
		output += "             Age: " + age + "\n";
		output += "  Student Number: " + sNumber + "\n";
		output += "Favourite Colour: " + favColour + "\n";
		output += "  Favourite Food: " + favFood + "\n";
		output += "  Wears Glasses?: " + glasses + "\n";
		output += "    Wears a Hat?: " + hasHat + "\n";
		return output;
	}
	
	public static void main (String[] args){
		Lab1 JR = new Lab1( "Jakob Roberts", 
							24, 
							"v00484900",
							"Green",
							"Sushi",
							true,
							true);
		Lab1 P  = new Lab1( "Pikachu", 
							3, 
							"FFzzzzzt",
							"Yellow",
							"Seeds",
							false,
							false);
							
							
		System.out.println(JR);
		System.out.println(P);
	}
}