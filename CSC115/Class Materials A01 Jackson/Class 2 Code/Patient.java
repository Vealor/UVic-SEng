// To be done in the class on Jan 13:
//  - Add a list of the patient's medications
//  - Check that the birthMonth, birthDay and birthYear are valid

public class Patient {
	//attributes
	private String name;
	private String bloodType;
	private String birthMonth;
	private int birthDay;
	private int birthYear;
	//private String [] currentMedications;

	//Constructors and Instance Methods

	//Default Constructor
	public Patient() {
		this.name = "";
		this.bloodType = "";
		this.birthMonth = "";
		this.birthDay = 0;
		this.birthYear = 0;
	}

	// a non-Default Constructor
	public Patient(String name, String bloodType, String newBirthMonth, int newBirthDay,
			int newBirthYear) {

		this.name = name;
		this.bloodType = bloodType;

		this.birthMonth = newBirthMonth;

		this.birthDay = newBirthDay;
		this.birthYear = newBirthYear;
	}


	// Instance Methods

	//Accessor for BirthYear
	public int getBirthYear() {
		return this.birthYear;
	}

	//Mutator for BirthYear
	public void setBirthYear(int newBirthYear) {
		this.birthYear = newBirthYear;
	}

	// Accessor (getter) for name
	public String getName() {
		return this.name;
	}
	//Mutator (setter) for name
	public void setName(String newName) {
		this.name = newName;
	}

	//Accessor (getter) for birthMonth
	public String getBirthMonth() {
		return this. birthMonth;
	}
	//Mutator (Setter) for birthMonth
	public void setBirthMonth(String newMonth) {
		this.birthMonth = newMonth;
	}

	//converts salient features of the object to a String
	public String toString() {
		String result = name + " ";
		result += bloodType + " " + birthMonth + " " + birthDay + " " + birthYear;
		//Notice that we don't *have to* put all the attributes into the string
		return result;
	}

}