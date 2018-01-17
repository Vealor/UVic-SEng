public class BackwardWriter {

	public static void main(String [] args) {

		System.out.println("\n\nThe iterative solution: ");
		System.out.print("    Abcus Backward is:  ");
		backIter("Abcus");

		System.out.println("\n\nThe recursive solution: ");
		System.out.print("    Wusac Backward is:  ");
		backRecur("Wusac");

		System.out.println("\n");

	}

	public static void backIter(String word) {

		for (int i = 0; i < word.length(); i++ ) {
			System.out.print(word.charAt(word.length() - (i+1)));
		}
	}

	public static void backRecur(String word) {

		if (word.length() != 1) {
			System.out.print(word.charAt(word.length()-1));
			backRecur(word.substring(0, word.length() - 1));
		}
		else System.out.print(word);
	}
}


