// PROGRAM: VictoriaHospitalSystem
// PURPOSE:   This program tests the methods and attributes in the Patient.java class.
// AUTHOR:  LillAnne Jackson
// DATE:    January 2014

// Will be updated in class Jan 13

public class VictoriaHospitalSystem {
	public static void main(String[] args) {

		//test the developing Patient!

		// a single Patient: using the default constructor
		Patient fred = new Patient();

		// an array of Patient's using the default constructor
		Patient [] admissionsJan13 = new Patient[54];

		for (int i = 0; i < admissionsJan13.length; i++) {
			// could also get patient info from a file then use non-default constructors
			admissionsJan13[i]= new Patient();

			// At this point, I could read data from a file and put it into each Patient object.
		}

		// Using the non-default constructor
		Patient lillAnne = new Patient("LillAnne Jackson", "AB", "January", 12, 1995);

		System.out.println("Made Some Patients");

		//Now output the info from the Patients
		//  Uses the toString() method we defined in Patient
		//  the . between 'fredLester' and 'toString' is called the dot operator
		for (int i = 0; i < admissionsJan13.length; i++) {
			System.out.println(admissionsJan13[i].toString());
		}
		System.out.println(lillAnne.toString());

		System.out.println("The full name is" + lillAnne.birthYear);


	}
}