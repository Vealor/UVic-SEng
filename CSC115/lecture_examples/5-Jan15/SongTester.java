import java.util.*;
import java.io.*;

public class SongTester
{
	public static void main (String[] args) throws FileNotFoundException
	{
		File inFile = new File ("songs.txt");
		Scanner sc = new Scanner(inFile);
		Song q;
		
		Song[] storage = new Song[4];
		int songCount = 0;
		
		while (sc.hasNext())
		{
			q = new Song(sc);
			storage[songCount++] = q;
			//System.out.println(q);
		}
		System.out.println("There are " + songCount + " songs in the Library");
		for (int i=0;i<songCount;i++)
		{
			System.out.println(storage[i]);
		}
		//
	}
}
