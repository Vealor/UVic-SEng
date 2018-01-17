// NAME: COMPLEX
// PURPOSE:  create and manipulate Complex Number objects
// AUTHOR:  LillAnne Jackson
// DATE: June 2013
// CREDITS: none - But many others have written complex number classes before me!

public class Complex1 {
	private int real;
	private int imaginary;

	// Default Constructor
	public Complex1 () {
		this.real = 0;
		this.imaginary = 0;
	}

	// Constructor for a new complex number, with:
	//   - real component = r
	//   - imaginary component = i
	public Complex1 (int r, int i) {
		this.real = r;
		this.imaginary = i;
	}

	// Accessor for the real attribute
	// INPUT: nothing
	// OUTPUT: an integer representing the real component of the complex number
	public int getReal() {
		return this.real;
	}

	// Mutator for the real attribute
	// INPUT: nothing
	// OUTPUT: nothing (but the real attribute is changed!)
	public void setReal(int real) {
		this.real = real;
	}

	// Accessor for the imaginary attribute
	// INPUT: nothing
	// OUTPUT: an integer representing the imaginary component of the complex number
	public int getImaginary() {
		return this.imaginary;
	}

	// Mutator for the imaginary attribute
	// INPUT: nothing
	// OUTPUT:  nothing (but the imaginary attribute is changed!)
	public void setImaginary(int imaginary) {
		this.imaginary = imaginary;
	}

	// Accessor for the real and imaginary attributes
	// INPUT: nothing
	// OUTPUT: returns a string formatted as a complex number
	public String toString() {
		String result = " ";
		result += real + " + " + imaginary + "i";
		return result;
	}
}