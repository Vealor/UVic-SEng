/*
 * Calc.java
 * Jakob Roberts
 * v00484900
 */
 
public class Calc{
	private static Boolean isThrown = false;
	
	private static void invalidExp(){
		if (!isThrown){
			System.out.println("Invalid Expression.");
		}
		isThrown = true;
	}
	
	private static Integer someMath(char cmd, Integer two, Integer one){
		Integer temp;
		switch(cmd){
			case '+':
				temp = one + two;
				return temp;
			case '-':
				temp = one - two;
				return temp;
			case 'x':
				temp = one * two;
				return temp;
			case '/':
				if(two == 0){
					invalidExp();
					break;
				}else{
					temp = one/two;
				}
				return temp;
			default:
				invalidExp();
				break;
		}
		return 0;
	}

	public static void main (String[] args){
		LLStack<Integer> stack = new LLStack<Integer>();
		for(int i=0;i<args.length;i++){
			try{
				stack.push(Integer.parseInt(args[i]));
			}catch(NumberFormatException f){	
				try{
					stack.push(someMath(args[i].charAt(0),stack.pop(),stack.pop()));
				}catch(StackEmptyException e){
					invalidExp();
					break;
				}
			}
		}
		try{
			if(stack.size() == 1 && !isThrown){
				System.out.println(Integer.toString(stack.pop()));
			}else{
				invalidExp();
			}			
		}catch(StackEmptyException e){}
	}
}