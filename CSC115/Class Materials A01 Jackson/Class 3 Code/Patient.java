// To be done in the class on Jan 13:
//  - Add a list of the patient's medications
//  - Check that the birthMonth, birthDay and birthYear are valid

// Note:  In many or all places in this code, the 'this.' can be removed:
//                Experiment to discover where it is necessary.  LJ

public class Patient {
	//Constant
	public static final int CURRENT_YEAR = 2014;

	//attributes
	private String name;
	private String bloodType;
	private int birthMonth;
	private int birthDay;
	private int birthYear;
	private String [] currentMedications;

	//Constructors and Instance Methods

	//Default Constructor
	public Patient() {
		this.name = "";
		this.bloodType = "";
		this.birthMonth = 0;
		this.birthDay = 0;
		this.birthYear = 0;
		this.currentMedications = new String[1];
	}

	// a non-Default Constructor
	public Patient(String newName, String newBloodType, int newBirthMonth, int newBirthDay,
			int newBirthYear, String[] medications) {

		this.name = newName;
		this.bloodType = newBloodType;

		birthMonth  = newBirthMonth;

		birthDay = newBirthDay;

		if (newBirthYear <=CURRENT_YEAR)  birthYear = newBirthYear;
		else birthYear = 0;

		this.currentMedications = new String[medications.length];
		for (int i = 0; i < medications.length; i++) {
			this.currentMedications[i] = medications[i];
		}
	}


	// Instance Methods

	//Accessor for BirthYear
	public int getBirthYear() {
		return this.birthYear;
	}

	//Mutator for BirthYear
	public void setBirthYear(int newBirthYear) {
		if (newBirthYear <= CURRENT_YEAR) {
			this.birthYear = newBirthYear;
		}
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
	public int getBirthMonth() {
		return this. birthMonth;
	}
	//Mutator (Setter) for birthMonth
	public void setBirthMonth(int newMonth) {
		if (checkBirthMonth(newMonth))
			this.birthMonth = newMonth;
		else System.err.println("ERROR: Month must be a value from 1 to 12.");
	}


	//helper method to check for accurate birthMonth
	private boolean checkBirthMonth(int newMonth) {
		if (newMonth >= 1 && newMonth <= 12)
			return true;
		else return false;
	}


	//Accessor (getter) for currentMedications:  Converts to a single string
	public String getCurrentMedications() {
		String result = "";

		for (int i = 0; i < this.currentMedications.length; i++) {
			result += this.currentMedications[i] +  ": ";
		}
		return result;
	}

	//converts salient features of the object to a String
	public String toString() {
		String result = name + " ";
		result += bloodType + " " + birthMonth + " " + birthDay + " " + birthYear;
		//Notice that we don't *have to* put all the attributes into the string
		return result;
	}

}