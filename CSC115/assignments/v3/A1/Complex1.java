// NAME: COMPLEX
// PURPOSE:  create and manipulate Complex Number objects
// AUTHOR:  LillAnne Jackson
// DATE: June 2013
// CREDITS: none - But many others have written complex number classes before me!

public class Complex1 {
	private int real;
	private int imaginary;

	public Complex1 () {
		this.real = 0;
		this.imaginary = 0;
	}

	public Complex1 (int r, int i) {
		this.real = r;
		this.imaginary = i;
	}

	public int getReal() {
		return this.real;
	}

	public void setReal(int real) {
		this.real = real;
	}

	public int getImaginary() {
		return this.imaginary;
	}

	public void setImaginary(int imaginary) {
		this.imaginary = imaginary;
	}

	public String toString() {
		String result = "";
		result += real + " + " + imaginary + "i";
		return result;
	}
}
