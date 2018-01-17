// Its a pretty basic Bubble Sort Algorithm . . .
// What is the Big-O Executution time of the sortIt() method?

public class BubbleSort {
	public static void main (String [] args ) {
		int [] stuff = {35, 46, 43, 45, 21, 3, 5} ;

		sortIt(stuff);

		for (int i = 0; i< stuff.length; i++)
			System.out.print(stuff[i]+" ");
	}

	public static void sortIt(int[] data) {

		for (int i = 0; i< data.length-1; i++)
			for (int j = 0; j < data.length-1; j++){
				if (data[j] < data[j+1]) {
					int temp = data[j];
					data[j] = data[j+1];
					data[j+1] = temp;
				}
		}
	}
}