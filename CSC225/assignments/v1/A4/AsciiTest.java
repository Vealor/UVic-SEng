public class AsciiTest{

	public static void main(String[] args){
		String s = "a";
		int value = 0;
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			value += (int)c;
		}
		System.out.println(value);
	}
}
