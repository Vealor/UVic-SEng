/*
 * Puzzle.java
 * 
 * A 9x9 Sudoku solver.
 * 
 * Based on a C implementation by M. Miller.
 *
 * J. Corless, February, 2014
 */

import java.util.Scanner;
import java.io.File;


public class Puzzle
{
	// This is an inner class
	// useable only by the Puzzle class
	// It could have been declared in it's own 
	// file.
	private class IntHolder
	{
		int value;
		public IntHolder(int v)
		{
			value = v;
		}
	}

	private final static int WIDTH = 9;
	private final static int HEIGHT = 9;

	private int recursiveCount;
	private int[][]	squares;
	private boolean solutionFound;

	public Puzzle()
	{
		squares = new int[HEIGHT][WIDTH];
		recursiveCount = 0;
		solutionFound = false;
	}

	public int getRecursiveCount()
	{
		return recursiveCount;
	}

	public Puzzle (String filename)
	{
		// This invokes the default, no parameter
		// constructor.
		// 
		// You can invoke constructors
		// only on the first line of a different 
		// constructor.
		this();

		try
		{
			File inFile = new File(filename);
			Scanner sc = new Scanner(inFile);

			int r, c;
			for (r=0;r<HEIGHT;r++)
			{
				String line = sc.nextLine();
				for (c=0;c<WIDTH;c++)
				{
					char ch = line.charAt(c);

					if (ch == '.')
						squares[r][c] = 0;
					else
						squares[r][c] = ch - '0';
				}

			}
		}
		catch (Throwable t)
		{
			System.out.println ("Failed to read file: " + filename);
		}
	}

	public boolean solve()
	{
		recursiveCount = 0;
		solutionFound = rsolve(0,0);	
		System.out.println("solved? : " + solutionFound + " recursive calls: " + recursiveCount);
		return solutionFound;
	}

	private boolean findBlank(IntHolder row, IntHolder col)
	{
		while (squares[row.value][col.value] > 0)
		{
			col.value += 1;
			if (col.value == 9)
			{
				col.value = 0;
				row.value += 1;
				if (row.value == 9)
				{
					return false;
				}
			}
		}	
		return true;
	}

	private boolean[] findCandidateValues (int r, int c)
	{
		boolean[] ok = new boolean[10]; // automatically set to false
		int i,j,k;
	
		for (i=1;i<=9;i++)
		{
			ok[i] = true;
			// row and column checks
			for(j=0;j<9;j++)
			{ 
				//row check
				if(squares[r][j]==i)
				{
					ok[i]=false;
					break;
				}
				// column check
				if(squares[j][c]==i)
				{
					ok[i]=false;
					break;
				}
			}

			// block check
			for(j=r/3*3;ok[i]&&j<r/3*3+3;j++)
			{
				for(k=c/3*3;ok[i]&&k<c/3*3+3;k++)
				{
					if(squares[j][k]==i) 
					{
						ok[i]=false;
					}
				}
			}
		}
		return ok;
	}

	private boolean rsolve (int r, int c)
	{
		boolean	ok[];

		IntHolder row = new IntHolder(r); // These are object representations of integers
		IntHolder col = new IntHolder(c); // so I can pass them to functions and change
				 		  // their values and see that change in this 
				 	          // function

	
		if (!findBlank(row,col))	  // no blanks means the
			return true;		  // puzzle is solved
	
		r = row.value;	// assign the changed values to 
		c = col.value;  // the local variables

		ok = findCandidateValues(r,c);

		// try each candidate in turn
		for (int i=1;i<=9;i++)
		{
			if (ok[i])
			{
				recursiveCount++;
				squares[r][c] = i;
				
				if (rsolve(r,c))
					return true;

			}
		}
		squares[r][c] = 0;
		return false;
	}


	public String toString()
	{
		int r,c;
		String s = new String();
	
		for (r=0;r<HEIGHT;r++) 
		{
			for (c=0;c<WIDTH;c++)
			{
				if (squares[r][c] == 0)
				{
					s+= " ";
				}
				else
				{
					s+= squares[r][c];
				}
				if (c == 2 || c == 5)
				{
					s+= "|";
				}
			}
			s+= "\n";
			if (r == 2 || r == 5)
			{
				s+= "-----------\n";
			}
			
		}
		return s;
	}

	public static void main (String[] args)
	{
		Puzzle p;
		String filename = "easy.txt";

		if (args.length > 0)
			filename = args[0];
		
		p = new Puzzle(filename);
		System.out.println(p);

		if (p.solve())
			System.out.println(p);
	}
}
