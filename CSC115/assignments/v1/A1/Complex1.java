// NAME: COMPLEX1
// PURPOSE:  create and manipulate Complex Number objects
// AUTHOR:  Jakob Roberts
// DATE: June 2013
// UPDATED:  January 2014
// CREDITS: LillAnne Jackson

public class Complex1 {
	private int real;
	private int imaginary;

	// Default Constructor
	public Complex1 () {
		this.real = 0;
		this.imaginary = 0;
	}

	// Constructor fora new complex number, with:
	//   - real component = r
	//   - imaginary component = i
	public Complex1 (int r, int i) {
		this.real = r;
		this.imaginary = i;
	}
	
	// Accessor for the Real attribute
	public int getReal() {
		return this.real;
	}

	// Mutator for the Real attribute
	public void setReal(int real) {
		this.real = real;
	}

	// Accessor for the Imaginary attribute
	public int getImaginary() {
		return this.imaginary;
	}

	// Mutator for the Imaginary attribute
	public void setImaginary(int imaginary) {
		this.imaginary = imaginary;
	}

	// Creates a string representation of the object
	public String toString() {
		String result = "";
		result += real + " + " + imaginary + "i";
		return result;
	}
}
