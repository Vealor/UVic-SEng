/**
 Write a program to process an expression in post fix form from the command line. e.g. java 3 5 +
 Note for multiplication, run it like this: java 3 5 "*"
 Stop the program and send an error message if the number of arguments are less than 3.
*/
public class Lab8
{
	public static void main(String[] args)
	{
		if(args.length<3)
		{
			System.out.println("At least 3 parameters are required, e.g., java Lab8 2 3 +");
			System.exit(1);
		}

		//System.out.println("For multiplication, run the program like this:\nLab8 3 5 \"*\"");
		ExprTree tree=new ExprTree();
		try{
			tree.build(args);
			double result = tree.evaluate();
			System.out.println(tree + "= " + result);
			System.out.println(tree.printTree());
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}
}