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
		
	}
	
	/**
	  Get a line from the Scanner object and return the String object
	*/
	private static String getURL(Scanner input)
	{
		return null;
	}
	
	/**
	  if bStack is not empty, push URL to fStack and pop a string from bStack, return the string
	  else print a message to screen and return null
	*/
	private static String backward(LabStack<String> bStack,LabStack<String> fStack,String URL)
	{
		return null;
	}
	
	/**
	  if fStack is not empty, push URL to bStack and pop a string from fStack, return the string
	  else print a message to screen and return null
	*/
	private static String forward(LabStack<String> bStack,LabStack<String> fStack,String URL)
	{
		return null;//optional	
	}
	
	//an interactive program to simulate the "<-" button of the Firefox browser.
	public static void main(String[] args)
	{
	}
}