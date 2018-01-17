/**
  A very simple interactive text browser to simulate the "<-" and "->" buttons of the web browser Firefox. When a user enters a URL, it is pushed onto the back stack. If the user chooses "<", the URL visited previously is shown on screen while the current URL is pushed onto the forward stack.
*/
import java.util.Scanner;
public class Browser
{
	/**
	  provide a text menu to users. The menu options are:
	  n - go to a new website
	  < - backward: go to the previous website (Don't show it if the bstack is empty)
	  > - forward: go to the next website (Don't show it if the fstack is empty)
	  q - quit the program
	*/
	private static void menu(LabStack<String> bStack,LabStack<String> fStack)
	{
		
		System.out.println("back:" + bStack);
		System.out.println("forward:" + fStack);
		
	}
	
	/**
	  Get a line from the Scanner object and return the String object
	*/
	private static String getURL(Scanner input)
	{
		return input.nextLine();
	}
	
	/**
	  if bStack is not empty, push URL to fStack and pop a string from bStack, return the string
	  else print a message to screen and return null
	*/
	private static String backward(LabStack<String> bStack,LabStack<String> fStack,String URL)
	{
		if(!bStack.empty()){
			if(URL!=null){
				fStack.push(URL);
			}
			return bStack.pop();
		}
		if(bStack.empty()&&URL!=null){
			fStack.push(URL);
		}
		return null;
	}
	
	/**
	  if fStack is not empty, push URL to bStack and pop a string from fStack, return the string
	  else print a message to screen and return null
	*/
	private static String forward(LabStack<String> bStack,LabStack<String> fStack,String URL)
	{
		if(!fStack.empty()){
			if(URL!=null){
				bStack.push(URL);
			}
			return fStack.pop();
		}
		if(fStack.empty()&&URL!=null){
			bStack.push(URL);
		}
		return null;	
	}
	
	//an interactive program to simulate the "<-" button of the Firefox browser.
	public static void main(String[] args)
	{
		LabStack<String> bStack = new LabStack<String>();
		LabStack<String> fStack = new LabStack<String>();
		char choice = 'a';
		String currentURL = null;
		Scanner s = new Scanner (System.in);
		
		while(choice != 'q'){
			System.out.print("            Welcome to the Simple Text Browser              \n" +
						 "************************************************************\n" +
						 "* Press one of the four characters: n < > or q             *\n" +
						 "* n -Go to a new website                                   *\n" +
						 "* q -Quit                                                  *\n" +
						 "************************************************************\n" +
						 "Enter a character: ");
			choice = s.nextLine().trim().charAt(0);
			switch(choice){
				case 'n':
					if(currentURL!=null){
						bStack.push(currentURL);
					}
					System.out.print("Enter the URL: ");
					currentURL = getURL(s);
					fStack.makeEmpty();
					menu(bStack,fStack);
					break;
				case '<':
					if(!bStack.empty()){
						currentURL = backward(bStack,fStack,currentURL);
					}
					System.out.println("Current URL: " + currentURL);
					menu(bStack,fStack);
					break;
				case '>':
					if(!fStack.empty()){
						currentURL = forward(bStack,fStack,currentURL);
					}
					System.out.println("Current URL: " + currentURL);
					menu(bStack,fStack);
					break;
				case 'q':
					break;
				default:
					System.out.println("Invalid choice. Try again.");
			}
		}
	}
}