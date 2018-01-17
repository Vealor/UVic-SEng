// NAME: COMPLEX2
// PURPOSE:  create and manipulate Complex Number objects
// AUTHOR:  Jakob Roberts
// DATE: June 2013
// UPDATED:  January 2014
// CREDITS: LillAnne Jackson

/*
 * The difference between static and instanced modifiers on methods are that
 * if it is static then there is only one copy of that function and if it is
 * instanced then with every object or instance of that new class, a new copy
 * is made and the same is with case variables.
 */

public class Complex2 {
	
	private int real;
	private int imaginary;
	
	// Default Constuctor
	public Complex2 () {
		this.real = 0;
		this.imaginary = 0;
	}
	
	// Constructor fora new complex number, with:
	//   - real component = r
	//   - imaginary component = i
	public Complex2 (int r, int i) {
		this.real = r;
		this.imaginary = i;
	}

	// Constructor for a new complex number with no imaginary part, with:
	//   - real component = r
	public Complex2 (int r) {
		this.real = r;
		this.imaginary = 0;
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
	
	// Adds two Complex Numbers together
	public Complex2 add(Complex2 val) {
		Complex2 newNumber = new Complex2();
		newNumber.real = real + val.real;
		newNumber.imaginary = imaginary + val.imaginary;		
		return newNumber;
	}

	// Subtracts the passed complex number from the called complex number
	public Complex2 subtract(Complex2 val) {
		Complex2 newNumber = new Complex2();
		newNumber.real = real - val.real;
		newNumber.imaginary = imaginary - val.imaginary;		
		return newNumber;
	}

	// Multiplies two complex numbers together
	// Reference equation in assignment PDF
	public Complex2 multiply(Complex2 val) {
		Complex2 newNumber = new Complex2();
		newNumber.real = (real * val.real) - (imaginary * val.imaginary);
		newNumber.imaginary = (imaginary * val.real) + (real * val.imaginary);		
		return newNumber;
	}
	
	// Divides the called complex number by the passed complex number
	// Reference equation in assignment PDF
	public Complex2 divide(Complex2 val) {
		Complex2 newNumber = new Complex2();
		newNumber.real = ((real*val.real)+(imaginary*val.imaginary))/((val.real*val.real)+(val.imaginary*val.imaginary));
		newNumber.imaginary = ((imaginary*val.real)-(real*val.imaginary))/((val.real*val.real)+(val.imaginary*val.imaginary));		
		return newNumber;
	}

	// Creates a string representation of the object
	public String toString() {
		String result = "";
		if(real!=0&&imaginary!=0)
		{
			if(imaginary<0)
			{
				imaginary = imaginary*-1;
				result += real + " - " + imaginary + "i";
			}else{
				result += real + " + " + imaginary + "i";
			}
		} else if (real==0&&imaginary!=0)
		{
			if(imaginary<0)
			{
				imaginary = imaginary*-1;
				result += real + " - " + imaginary + "i";
			}else{
				result += real + " + " + imaginary + "i";
			}
		} else if (real!=0&&imaginary==0)
		{
			result += real;
		}else{
			result += "0";
		}
		
		
		return result;
	}

	public static void main(String [] args) {

		System.out.println("Complex Number Tester Output:");

		// Test the new constructor for Real (only) numbers
		Complex2 aReal = new Complex2(423);
		System.out.print("Real Constructor Test: Should Output 423 : ");
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
