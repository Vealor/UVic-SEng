// NAME: COMPLEX
// PURPOSE:  create and manipulate Complex Number objects
// AUTHOR:  LillAnne Jackson
// DATE: June 2013
// UPDATED:  January 2014
// CREDITS: none - But many others have written complex number classes before me!

public class Complex2 {
	private int real;
	private int imaginary;

	// Default Constructor
	public Complex2 () {
		this.real = 0;
		this.imaginary = 0;
	}

	// Constructor for a new complex number, with:
	//   - real component = r
	//   - imaginary component = i
	public Complex2 (int r, int i) {
		this.real = r;
		this.imaginary = i;
	}

	// Constructor for a new complex number, with:
	//   - real component = r
	//   - imaginary component = 0
	public Complex2 (int r) {
		this.real = r;
		this.imaginary = 0;
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

	// Instance method to add 2 complex numbers
	// INPUT: a complex number
	// OUTPUT: the sum of this + the input number
	public Complex2 add(Complex2 other) {
		Complex2 result = new Complex2();
		result.real = real + other.real;
		result.imaginary = imaginary + other.imaginary;

		return result;
	}

	// Instance method to subtract 2 complex numbers
	// INPUT: a complex number
	// OUTPUT: the difference: this - the input number
	public Complex2 subtract(Complex2 other) {
		Complex2 result = new Complex2();
		result.real = real - other.real;
		result.imaginary = imaginary - other.imaginary;

		return result;
	}

	// Instance method to multiply 2 complex numbers
	// INPUT: a complex number
	// OUTPUT: the product: this * the input number
	public Complex2 multiply(Complex2 other) {
		Complex2 result = new Complex2();
		result.real = real * other.real - imaginary * other.imaginary;
		result.imaginary = imaginary * other.real + real * other.imaginary;

		return result;
	}


	// Instance method to divide 2 complex numbers
	// INPUT: a complex number
	// OUTPUT: the product: this / the input number
	// 			(results are truncated to integers)
	public Complex2 divide(Complex2 other) {
		Complex2 result = new Complex2();
		result.real = (int) ((real * other.real + imaginary * other.imaginary)
						/ (other.real * other.real + other.imaginary * other.imaginary));
		result.imaginary = (int) ((imaginary * other.real - real * other.imaginary)
						/ (other.real * other.real + other.imaginary * other.imaginary));

		return result;
	}


	// Instance method that converts the object's attributes to a String
	// INPUT: nothing
	// OUTPUT: returns a string formatted as a complex number
	public String toString() {
		String result = " ";
		result += this.real;
		if (this.imaginary < 0) result += " - " + -1 * this.imaginary + "i";
		else if (this.imaginary > 0) result += " + " + this.imaginary + "i";
		// Notice that there is nothing added when imaginary == 0

		return result;
	}

	public static void main(String [] args) {

		System.out.println("\nComplex Number Tester Output: \n");

		// Test the new constructor for Real (only) numbers
		Complex2 aReal = new Complex2(12);
		System.out.print("Real Constructor Test: Should Output 12 : ");
		System.out.println(aReal.toString());

		// Instantiate some more complex numbers
		Complex2 oneValue = new Complex2(-3,4);
		Complex2 anotherValue = new Complex2(2,-1);

		// Test add:  oneValue + anotherValue
		System.out.print("Add Tester: Should Output -1 + 3i : ");
		System.out.println(oneValue.add(anotherValue));

		// Test subtract:  oneValue - anotherValue
		System.out.print("Subtract Tester: Should Output -5 + 5i : ");
		System.out.println(oneValue.subtract(anotherValue));

		// Test multiply:  oneValue * anotherValue
		System.out.print("Multiply Tester: Should Output -2 + 11i : ");
		System.out.println(oneValue.multiply(anotherValue));

		// Test divide:  oneValue / anotherValue
		System.out.print("Divide Tester: Should Output -2 + 1i : ");
		System.out.println(oneValue.divide(anotherValue));

	}
}