public class SimpleSum {

	public static int sumIter(int[] data) {

		int sum = 0;

		for (int i = 0; i < data.length; i++ ) {
			sum += data[i];
		}

		return sum;
	}

	public static double sum (double anArray[],int n) {
		double total = 0;
		if (n==1) total = anArray[0];
		else total =  anArray[n-1] + sum(anArray, n-1);
		return total;
	}


	public static void main(String [] args) {

		//An array: populate any way you like:
		double [] myData = {4.0, 6.1, 7.35, 2};


		System.out.println("The recursive solution: ");
		System.out.print("    The sum is:  " + sum(myData, myData.length));

		System.out.println("\n");

	}


}



